package mobtalkerscript.mts.v1.parser;

import java.util.*;
import java.util.regex.*;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.instruction.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.mts.v1.parser.MtsParser.BinaryExprContext;
import mobtalkerscript.mts.v1.parser.MtsParser.BlockContext;
import mobtalkerscript.mts.v1.parser.MtsParser.BooleanLiteralContext;
import mobtalkerscript.mts.v1.parser.MtsParser.BreakStmtContext;
import mobtalkerscript.mts.v1.parser.MtsParser.CallContext;
import mobtalkerscript.mts.v1.parser.MtsParser.ChunkContext;
import mobtalkerscript.mts.v1.parser.MtsParser.ExprContext;
import mobtalkerscript.mts.v1.parser.MtsParser.ExpressionKeyedAccessContext;
import mobtalkerscript.mts.v1.parser.MtsParser.ExpressionKeyedFieldDefContext;
import mobtalkerscript.mts.v1.parser.MtsParser.FieldDefExprContext;
import mobtalkerscript.mts.v1.parser.MtsParser.FuncDeclContext;
import mobtalkerscript.mts.v1.parser.MtsParser.FunctionCallContext;
import mobtalkerscript.mts.v1.parser.MtsParser.GenericForContext;
import mobtalkerscript.mts.v1.parser.MtsParser.IdentifierKeyedAccessContext;
import mobtalkerscript.mts.v1.parser.MtsParser.IdentifierKeyedFieldDefContext;
import mobtalkerscript.mts.v1.parser.MtsParser.IfElseBlockContext;
import mobtalkerscript.mts.v1.parser.MtsParser.JumpContext;
import mobtalkerscript.mts.v1.parser.MtsParser.LabelDeclContext;
import mobtalkerscript.mts.v1.parser.MtsParser.ListFieldDefContext;
import mobtalkerscript.mts.v1.parser.MtsParser.LocalFieldDefinitionContext;
import mobtalkerscript.mts.v1.parser.MtsParser.LogicalExprContext;
import mobtalkerscript.mts.v1.parser.MtsParser.LoopBlockContext;
import mobtalkerscript.mts.v1.parser.MtsParser.NullLiteralContext;
import mobtalkerscript.mts.v1.parser.MtsParser.NumberLiteralContext;
import mobtalkerscript.mts.v1.parser.MtsParser.NumericForContext;
import mobtalkerscript.mts.v1.parser.MtsParser.RepeatBlockContext;
import mobtalkerscript.mts.v1.parser.MtsParser.ReturnStmtContext;
import mobtalkerscript.mts.v1.parser.MtsParser.StringLiteralContext;
import mobtalkerscript.mts.v1.parser.MtsParser.TableAccessContext;
import mobtalkerscript.mts.v1.parser.MtsParser.TableAssignmentContext;
import mobtalkerscript.mts.v1.parser.MtsParser.TableCallContext;
import mobtalkerscript.mts.v1.parser.MtsParser.TableCtorExprContext;
import mobtalkerscript.mts.v1.parser.MtsParser.TableExprContext;
import mobtalkerscript.mts.v1.parser.MtsParser.TableFieldAccessContext;
import mobtalkerscript.mts.v1.parser.MtsParser.UnaryExprContext;
import mobtalkerscript.mts.v1.parser.MtsParser.VariableAccessContext;
import mobtalkerscript.mts.v1.parser.MtsParser.VariableAssignmentContext;
import mobtalkerscript.mts.v1.parser.MtsParser.WhileBlockContext;
import mobtalkerscript.util.logging.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import com.google.common.collect.*;

public class MtsToMislCompiler extends AbstractMtsToMislCompiler
{
    private final IBindings _local;
    
    private InstrLabel _curBreakTarget;
    
    // ========================================
    
    {
        _local = new SimpleBindings();
    }
    
    // ========================================
    
    public IBindings getBindings()
    {
        return _local;
    }
    
    // ========================================
    
    private void checkSourcePosition( ParserRuleContext ctx )
    {
        checkSourcePosition( ctx.start );
    }
    
    private void checkSourcePosition( Token token )
    {
        String sourceName = token.getTokenSource().getSourceName();
        int sourceLine = token.getLine();
        
        if ( setSourcePosition( sourceName, sourceLine ) )
        {
            addInstr( new InstrLine( sourceName, sourceLine ) );
        }
    }
    
    // ========================================
    
    @Override
    public Void visit( ParseTree tree )
    {
        if ( tree == null )
        {
            return null;
        }
        
        if ( ( tree instanceof ParserRuleContext ) && !( tree instanceof ChunkContext ) )
        {
            checkSourcePosition( (ParserRuleContext) tree );
        }
        
        super.visit( tree );
        
        return null;
    }
    
    public Void visit( List<? extends ParseTree> ctxs )
    {
        for ( ParseTree ctx : ctxs )
        {
            visit( ctx );
        }
        
        return null;
    }
    
    // ========================================
    // chunk
    
    @Override
    public Void visitChunk( ChunkContext ctx )
    {
        MTSLog.fine( "[Compiler] Begin chunk" );
        
        visit( ctx.labelDecl() );
        visit( ctx.funcDecl() );
        
        MTSLog.fine( "[Compiler] End chunk" );
        
        return null;
    }
    
    // ========================================
    // Label
    
    @Override
    public Void visitLabelDecl( LabelDeclContext ctx )
    {
        String funcName = ctx.LabelName.getText();
        
        InstrLabel label = new InstrLabel( funcName );
        addInstr( label );
        
        _local.set( funcName, new MislFunction( label, 0 ) );
        
        visitChildren( ctx.Block );
        
        addInstr( new InstrReturn( 0 ) );
        
        return null;
    }
    
    @Override
    public Void visitFuncDecl( FuncDeclContext ctx )
    {
        String funcName = ctx.FunctionName.getText();
        int argCount = ctx.Parameters.size();
        
        InstrLabel label = new InstrLabel( funcName );
        addInstr( label );
        
        for ( Token paramName : ctx.Parameters )
        {
            String argName = paramName.getText();
            addInstr( new InstrStoreL( argName ) );
        }
        
        visit( ctx.Block );
        
        addInstr( new InstrReturn( 0 ) );
        
        _local.set( funcName, new MislFunction( label, argCount ) );
        
        return null;
    }
    
    @Override
    public Void visitReturnStmt( ReturnStmtContext ctx )
    {
        visit( ctx.ReturnExpr );
        
        int returnCount = ctx.ReturnExpr != null ? 1 : 0;
        
        addInstr( new InstrReturn( returnCount ) );
        
        return null;
    }
    
    @Override
    public Void visitJump( JumpContext ctx )
    {
        String labelName = ctx.LabelName.getText();
        
        addInstr( new InstrLoad( labelName ) );
        addInstr( new InstrJumpF() );
        
        return null;
    }
    
    @Override
    public Void visitCall( CallContext ctx )
    {
        String funcName = ctx.FunctionName.getText();
        
        addInstr( new InstrLoad( funcName ) );
        addInstr( new InstrCall( 0, 0 ) );
        
        return null;
    }
    
    // ========================================
    // Literal
    
    @Override
    public Void visitStringLiteral( StringLiteralContext ctx )
    {
        String literal = ctx.Literal.getText();
        literal = literal.substring( 1, literal.length() - 1 );
        
        visitInterpolatedString( literal );
        
        return null;
    }
    
    private static final Pattern _ipStrVarPattern = Pattern.compile( "\\$([_a-zA-Z]+[_a-zA-Z0-9]*)" );
    
    private void visitInterpolatedString( String str )
    {
        Matcher matcher = _ipStrVarPattern.matcher( str );
        int start = 0;
        int parts = 0;
        
        while ( matcher.find() )
        {
            if ( matcher.start() > 0 )
            {
                // Split string
                String subStr = str.substring( start, matcher.start() );
                addInstr( new InstrPush( subStr ) );
                
                parts++;
            }
            
            // Load variable
            String varName = matcher.group( 1 );
            addInstr( new InstrLoad( varName ) );
            
            parts++;
            
            start = matcher.end();
        }
        
        if ( start < str.length() )
        {
            String subStr = str.substring( start );
            addInstr( new InstrPush( subStr ) );
        }
        
        for ( int i = 1; i < parts; i++ )
        {
            addInstr( new InstrConcat() );
        }
    }
    
    @Override
    public Void visitNumberLiteral( NumberLiteralContext ctx )
    {
        int literal = Integer.parseInt( ctx.Literal.getText() );
        addInstr( new InstrPush( literal ) );
        
        return null;
    }
    
    @Override
    public Void visitBooleanLiteral( BooleanLiteralContext ctx )
    {
        boolean literal = Boolean.parseBoolean( ctx.Literal.getText() );
        addInstr( new InstrPush( literal ) );
        
        return null;
    }
    
    @Override
    public Void visitNullLiteral( NullLiteralContext ctx )
    {
        addInstr( new InstrPush( MislValue.NIL ) );
        
        return null;
    }
    
    // ========================================
    // Operators
    
    @Override
    public Void visitBinaryExpr( BinaryExprContext ctx )
    {
        String op = ctx.Operator.getText();
        
        if ( "+".equals( op ) )
        {
            visit( ctx.Left );
            visit( ctx.Right );
            
            addInstr( new InstrAdd() );
            
        }
        else if ( "-".equals( op ) )
        {
            visit( ctx.Left );
            visit( ctx.Right );
            
            addInstr( new InstrSub() );
        }
        else if ( "*".equals( op ) )
        {
            visit( ctx.Left );
            visit( ctx.Right );
            
            addInstr( new InstrMul() );
        }
        else if ( "/".equals( op ) )
        {
            visit( ctx.Left );
            visit( ctx.Right );
            
            addInstr( new InstrDiv() );
        }
        else if ( "%".equals( op ) )
        {
            visit( ctx.Left );
            visit( ctx.Right );
            
            addInstr( new InstrMod() );
        }
        else if ( "<".equals( op ) )
        {
            visit( ctx.Left );
            visit( ctx.Right );
            
            addInstr( new InstrLessThen() );
        }
        else if ( "<=".equals( op ) )
        {
            visit( ctx.Left );
            visit( ctx.Right );
            
            addInstr( new InstrLessThenEqual() );
        }
        else if ( ">".equals( op ) )
        {
            visit( ctx.Right );
            visit( ctx.Left );
            
            addInstr( new InstrLessThen() );
        }
        else if ( ">=".equals( op ) )
        {
            visit( ctx.Right );
            visit( ctx.Left );
            
            addInstr( new InstrLessThenEqual() );
        }
        else if ( "==".equals( op ) )
        {
            visit( ctx.Left );
            visit( ctx.Right );
            
            addInstr( new InstrCompare() );
        }
        else if ( "!=".equals( op ) )
        {
            visit( ctx.Left );
            visit( ctx.Right );
            
            addInstr( new InstrCompare() );
            addInstr( new InstrNot() );
        }
        else if ( "..".equals( op ) )
        {
            visit( ctx.Left );
            visit( ctx.Right );
            
            addInstr( new InstrConcat() );
        }
        else
        {
            throw new ScriptParserException( "Unknown binary operator: %s", op );
        }
        
        return null;
    }
    
    @Override
    public Void visitUnaryExpr( UnaryExprContext ctx )
    {
        visit( ctx.Right );
        
        String op = ctx.Operator.getText();
        
        if ( "not".equals( op ) )
        {
            addInstr( new InstrNot() );
        }
        else if ( "-".equals( op ) )
        {
            addInstr( new InstrNeg() );
        }
        else
        {
            throw new ScriptParserException( "Unknown binary operator: %s", op );
        }
        
        return null;
    }
    
    @Override
    public Void visitLogicalExpr( LogicalExprContext ctx )
    {
        InstrLabel cont = new InstrLabel( "continue" );
        
        String op = ctx.Operator.getText();
        
        visit( ctx.Left );
        
        if ( "and".equals( op ) )
        {
            addInstr( new InstrAnd( cont ) );
        }
        else if ( "or".equals( op ) )
        {
            addInstr( new InstrOr( cont ) );
        }
        
        visit( ctx.Right );
        
        addInstr( cont );
        
        return null;
    }
    
    // ========================================
    // Variables
    
//    private MtsSyntaxError generateSyntaxError(Token token, String msg)
//    {
//        String source = token.getTokenSource().getSourceName();
//        int line = token.getLine();
//        int col = token.getStartIndex();
//        
//        return new MtsSyntaxError(source, line, col, msg);
//    }
    
    @Override
    public Void visitVariableAssignment( VariableAssignmentContext ctx )
    {
        visit( ctx.ValueExpr );
        
        String varName = ctx.VariableName.getText();
        addInstr( new InstrStore( varName ) );
        
        return null;
    }
    
    @Override
    public Void visitLocalFieldDefinition( LocalFieldDefinitionContext ctx )
    {
        visit( ctx.ValueExpr );
        
        String varName = ctx.VariableName.getText();
        addInstr( new InstrStoreL( varName ) );
        
        return null;
    }
    
    @Override
    public Void visitVariableAccess( VariableAccessContext ctx )
    {
        String varName = ctx.VariableName.getText();
        addInstr( new InstrLoad( varName ) );
        
        return null;
    }
    
    // ========================================
    // Calls
    
    @Override
    public Void visitFunctionCall( FunctionCallContext ctx )
    {
        boolean shouldReturnValue = ctx.getParent() instanceof ExprContext;
        
        String funcName = ctx.FunctionName.getText();
        int argCount = ctx.ArgumentExprs.size();
        int retCount = shouldReturnValue ? 1 : 0;
        
        visit( ctx.ArgumentExprs );
        
        addInstr( new InstrLoad( funcName ) );
        addInstr( new InstrCall( argCount, retCount ) );
        
        return null;
    }
    
    @Override
    public Void visitTableCall( TableCallContext ctx )
    {
        boolean shouldReturnValue = ctx.getParent() instanceof ExprContext;
        
        int argCount = ctx.ArgumentExprs.size();
        int retCount = shouldReturnValue ? 1 : 0;
        
        visit( ctx.ArgumentExprs );
        visit( ctx.FunctionExpr );
        
        addInstr( new InstrLoadT() );
        addInstr( new InstrCall( argCount, retCount ) );
        
        return null;
    }
    
    // ========================================
    // Tables
    
    @Override
    public Void visitTableCtorExpr( TableCtorExprContext ctx )
    {
        int tSize = ctx.FieldExprs.size();
        addInstr( new InstrCreateT( tSize ) );
        
        List<ListFieldDefContext> listExprs = Lists.newArrayList();
        
        // Sort field definitions
        for ( FieldDefExprContext fieldDef : ctx.FieldExprs )
        {
            if ( fieldDef instanceof ListFieldDefContext )
            {
                listExprs.add( (ListFieldDefContext) fieldDef );
            }
            else
            {
                visit( fieldDef );
            }
        }
        
        // Store list values
        if ( !listExprs.isEmpty() )
        {
            for ( ListFieldDefContext fieldDef : listExprs )
            {
                addInstr( new InstrDup() );
                visit( fieldDef.ValueExpr );
                addInstr( new InstrStoreTL() );
            }
        }
        
        return null;
    }
    
    @Override
    public Void visitIdentifierKeyedFieldDef( IdentifierKeyedFieldDefContext ctx )
    {
        String key = ctx.Key.getText();
        
        addInstr( new InstrDup() );
        addInstr( new InstrLoad( key ) );
        visit( ctx.ValueExpr );
        addInstr( new InstrStoreT() );
        
        return null;
    }
    
    @Override
    public Void visitExpressionKeyedFieldDef( ExpressionKeyedFieldDefContext ctx )
    {
        addInstr( new InstrDup() );
        visit( ctx.KeyExpr );
        visit( ctx.ValueExpr );
        addInstr( new InstrStoreT() );
        
        return null;
    }
    
    @Override
    public Void visitTableExpr( TableExprContext ctx )
    {
        String parentVar = ctx.ParentVariable.getText();
        addInstr( new InstrLoad( parentVar ) );
        
        for ( TableFieldAccessContext fieldAccessCtx : ctx.FieldAccesses )
        {
            visit( fieldAccessCtx );
            addInstr( new InstrLoadT() );
        }
        
        visit( ctx.LastFieldAccess );
        
        return null;
    }
    
    @Override
    public Void visitTableAccess( TableAccessContext ctx )
    {
        visit( ctx.TableFieldExpr );
        addInstr( new InstrLoadT() );
        
        return null;
    }
    
    @Override
    public Void visitTableAssignment( TableAssignmentContext ctx )
    {
        visit( ctx.TableFieldExpr );
        visit( ctx.ValueExpr );
        addInstr( new InstrStoreT() );
        
        return null;
    }
    
    @Override
    public Void visitIdentifierKeyedAccess( IdentifierKeyedAccessContext ctx )
    {
        String fieldName = ctx.Key.getText();
        addInstr( new InstrPush( fieldName ) );
        
        return null;
    }
    
    @Override
    public Void visitExpressionKeyedAccess( ExpressionKeyedAccessContext ctx )
    {
        visit( ctx.KeyExpr );
        
        return null;
    }
    
    // ========================================
    // Blocks
    
    @Override
    public Void visitBlock( BlockContext ctx )
    {
        return visitBlock( ctx, true );
    }
    
    public Void visitBlock( BlockContext ctx, boolean newScope )
    {
        if ( newScope )
        {
            addInstr( new InstrPushScope() );
        }
        
        super.visitBlock( ctx );
        
        if ( newScope )
        {
            addInstr( new InstrPopScope() );
        }
        
        return null;
    }
    
    @Override
    public Void visitLoopBlock( LoopBlockContext ctx )
    {
        super.visitLoopBlock( ctx );
        
        return null;
    }
    
    @Override
    public Void visitIfElseBlock( IfElseBlockContext ctx )
    {
        InstrLabel cont = new InstrLabel( "continue" );
        
        singleIfElse( ctx, cont );
        
        if ( ctx.Condition.size() > 1 )
        {
            multiIfElse( ctx, cont );
        }
        
        if ( ctx.ElseBlock != null )
        {
            visit( ctx.ElseBlock );
        }
        
        addInstr( cont );
        
        return null;
    }
    
    private void singleIfElse( IfElseBlockContext ctx, InstrLabel cont )
    {
        InstrLabel elze = new InstrLabel( "else" );
        
        ExprContext ifCond = ctx.Condition.get( 0 );
        visit( ifCond );
        
        addInstr( new InstrJumpIfNot( elze ) );
        
        BlockContext thenBlock = ctx.ThenBlock.get( 0 );
        visit( thenBlock );
        
        addInstr( new InstrJump( cont, false, false ) );
        addInstr( elze );
    }
    
    private void multiIfElse( IfElseBlockContext ctx, InstrLabel cont )
    {
        for ( int i = 1; i < ctx.Condition.size(); i++ )
        {
            InstrLabel elze = new InstrLabel( "elseif" );
            
            ExprContext ifCond = ctx.Condition.get( i );
            visit( ifCond );
            
            addInstr( new InstrJumpIfNot( elze ) );
            
            BlockContext thenBlock = ctx.ThenBlock.get( i );
            visit( thenBlock );
            
            addInstr( new InstrJump( cont, false, false ) );
            addInstr( elze );
        }
    }
    
    @Override
    public Void visitWhileBlock( WhileBlockContext ctx )
    {
        InstrLabel loop = new InstrLabel( "while" );
        addInstr( loop );
        
        visit( ctx.Condition );
        
        InstrLabel cont = new InstrLabel( "continue" );
        addInstr( new InstrJumpIfNot( cont ) );
        
        addInstr( new InstrPushScope() );
        _curBreakTarget = cont;
        visit( ctx.LoopBlock );
        _curBreakTarget = null;
        addInstr( new InstrPopScope() );
        
        addInstr( new InstrJump( loop, false, false ) );
        addInstr( cont );
        
        return null;
    }
    
    @Override
    public Void visitRepeatBlock( RepeatBlockContext ctx )
    {
        InstrLabel loop = new InstrLabel( "repeat" );
        addInstr( loop );
        
        InstrLabel cont = new InstrLabel( "continue" );
        
        addInstr( new InstrPushScope() );
        _curBreakTarget = cont;
        visit( ctx.LoopBlock );
        _curBreakTarget = null;
        addInstr( new InstrPopScope() );
        
        visit( ctx.Condition );
        
        addInstr( new InstrNot() );
        addInstr( new InstrJumpIfNot( loop ) );
        addInstr( cont );
        
        return null;
    }
    
    @Override
    public Void visitNumericFor( NumericForContext ctx )
    {
        String loopVarName = ctx.Control.LoopVariable.getText();
        
        visit( ctx.Control.ValueExpr );
        
        InstrLabel loop = new InstrLabel( "nfor" );
        addInstr( loop );
        
        addInstr( new InstrPushScope() );
        addInstr( new InstrStoreL( loopVarName ) );
        
        visit( ctx.Control.Condition );
        
        InstrLabel cont = new InstrLabel( "continue" );
        addInstr( new InstrJumpIfNot( cont ) );
        
        _curBreakTarget = cont;
        visit( ctx.LoopBlock );
        _curBreakTarget = null;
        
        if ( ctx.Control.UpdateExpr == null )
        {
            addInstr( new InstrLoad( loopVarName ) );
            addInstr( new InstrPush( 1 ) );
            addInstr( new InstrAdd() );
        }
        else
        {
            visit( ctx.Control.UpdateExpr );
        }
        
        addInstr( new InstrPopScope() );
        
        addInstr( new InstrJump( loop, false, false ) );
        addInstr( cont );
        
        return null;
    }
    
    @Override
    public Void visitGenericFor( GenericForContext ctx )
    {
        String keyVarName = ctx.Control.KeyVariable.getText();
        String valVarName = ctx.Control.ValueVariable.getText();
        
        visit( ctx.Control.TableExpr );
        addInstr( new InstrPush( MislValue.NIL ) );
        
        InstrLabel loop = new InstrLabel( "gfor" );
        addInstr( loop );
        
        InstrLabel cont = new InstrLabel( "continue" );
        addInstr( new InstrNext( cont ) );
        
        addInstr( new InstrPushScope() );
        addInstr( new InstrStoreL( valVarName ) );
        addInstr( new InstrStoreL( keyVarName ) );
        _curBreakTarget = cont;
        visit( ctx.LoopBlock );
        _curBreakTarget = null;
        addInstr( new InstrPopScope() );
        
        addInstr( new InstrJump( loop, false, false ) );
        addInstr( cont );
        addInstr( new InstrPop() );
        
        return null;
    }
    
    @Override
    public Void visitBreakStmt( BreakStmtContext ctx )
    {
        addInstr( new InstrJump( _curBreakTarget, false, true ) );
        
        return null;
    }
}
