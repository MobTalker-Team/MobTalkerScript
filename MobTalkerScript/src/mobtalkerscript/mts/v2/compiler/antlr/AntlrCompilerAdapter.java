package mobtalkerscript.mts.v2.compiler.antlr;

import static mobtalkerscript.mts.v2.compiler.MtsCompiler.*;
import static mobtalkerscript.mts.v2.value.MtsValue.*;

import java.util.*;

import mobtalkerscript.mts.v2.compiler.*;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.AssignExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.BinaryOpExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.BooleanLiteralContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.CallArgsContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.CallContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.CallExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ChunkContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.CommandHideContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.CommandMenuContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.CommandSayContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.CommandSceneContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.CommandShowContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ConditionalOpExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ElseBodyContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ElseIfBodyContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.FieldDefContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.FuncBodyContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.FuncDeclrExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.FuncDeclrStmtContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.FuncNameContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.GenericForLoopContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.GotoStmtContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.IfThenElseContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.LabelStmtContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ListFieldContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.LocalFuncDeclrStmtContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.LocalVarDeclrStmtContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.LogicalOpExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.NameFieldAccessContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.NameKeyFieldContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.NameListContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.NestedBlockContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.NullLiteralContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.NumberLiteralContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.NumericForLoopContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.RepeatLoopContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ReturnStmtContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.SimpleAssignmentStmtContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.StmtContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.StringLiteralContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.TableCtorContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.UnaryOpExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.VarExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.VarExprListContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.VarSuffixContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.WhileLoopContext;
import mobtalkerscript.mts.v2.lib.*;
import mobtalkerscript.mts.v2.value.*;

import org.antlr.v4.runtime.*;

import com.google.common.collect.*;

@SuppressWarnings( "unchecked" )
public class AntlrCompilerAdapter extends MtsBaseVisitor<Void>
{
    private static final ITreePattern TableAssignPattern;
    
    static
    {
        TableAssignPattern = new TreeMultiPattern( new TreePattern( AssignExprContext.class ),
                                                   new TreePattern( VarExprListContext.class,
                                                                    AssignExprContext.class ) );
    }
    
    // ========================================
    
    private MtsCompiler _c;
    
    // ========================================
    
    public void compile( MtsCompiler compiler, ChunkContext chunk )
    {
        _c = compiler;
        visit( chunk );
        _c = null;
    }
    
    // ========================================
    // Chunk
    
    @Override
    public Void visitChunk( ChunkContext ctx )
    {
        System.out.println( "Enter Chunk" );
        
        visitChildren( ctx );
        _c.returnFunction( 0 );
        
        System.out.println( "Exit Chunk" );
        return null;
    }
    
    // ========================================
    // Commands
    
    private void visitSingleOrCreateTable( List<ExprContext> ctxs )
    {
        visit( ctxs );
        
        int exprs = ctxs.size();
        if ( exprs > 1 )
        {
            _c.createTable( exprs, 0 );
        }
    }
    
    @Override
    public Void visitCommandSay( CommandSayContext ctx )
    {
        _c.loadVariable( MtsCommandLib.FNAME_SAY );
        
        if ( ctx.Character == null )
            _c.loadNil();
        else
            visit( ctx.Character );
        
        visitSingleOrCreateTable( ctx.Text.Exprs );
        
        _c.loadConstant( valueOf( ctx.IsLast != null ) );
        
        _c.callFunction( 3, 0 );
        
        return null;
    }
    
    @Override
    public Void visitCommandShow( CommandShowContext ctx )
    {
        _c.loadVariable( MtsCommandLib.FNAME_SHOW );
        
        visitSingleOrCreateTable( ctx.Path );
        
        if ( ctx.Position == null )
            _c.loadNil();
        else
            visit( ctx.Position );
        
        if ( ctx.Offset == null )
        {
            _c.loadConstant( ZERO );
            _c.loadConstant( ZERO );
        }
        else
        {
            visit( ctx.Offset.Exprs.get( 0 ) );
            if ( ctx.Offset.Exprs.size() < 2 )
            {
                _c.loadConstant( ZERO );
            }
            else
            {
                visit( ctx.Offset.Exprs.get( 1 ) );
            }
        }
        
        _c.callFunction( 4, 0 );
        
        return null;
    }
    
    @Override
    public Void visitCommandScene( CommandSceneContext ctx )
    {
        _c.loadVariable( MtsCommandLib.FNAME_SCENE );
        
        visitSingleOrCreateTable( ctx.Path );
        
        if ( ctx.Mode == null )
            _c.loadNil();
        else
            visit( ctx.Mode );
        
        _c.callFunction( 2, 0 );
        
        return null;
    }
    
    @Override
    public Void visitCommandHide( CommandHideContext ctx )
    {
        _c.loadVariable( MtsCommandLib.FNAME_HIDE );
        
        visit( ctx.Group );
        
        _c.callFunction( 1, 0 );
        
        return null;
    }
    
    @Override
    public Void visitCommandMenu( CommandMenuContext ctx )
    {
        _c.loadVariable( MtsCommandLib.FNAME_MENU );
        
        if ( ctx.Caption == null )
            _c.loadNil();
        else
            visit( ctx.Caption );
        
        int choiceIndex = _c.declareAnonymousLocal( "choice" ).getIndex();
        
        int nOptions = ctx.Options.size();
        for ( int i = 0; i < nOptions; i++ )
        {
            visit( ctx.Options.get( i ).Caption );
        }
        
        _c.callFunction( 1 + nOptions, 1 );
        _c.storeLocal( choiceIndex );
        
        _c.enterIfThenElseBlock();
        _c.enterBlock();
        
        for ( int i = 0; i < nOptions; i++ )
        {
            if ( i > 0 )
            {
                _c.enterIfCondition();
            }
            
            _c.loadLocal( choiceIndex );
            _c.loadConstant( valueOf( i + 1 ) );
            _c.logicOperation( "==" );
            _c.endIfCondition();
            visit( ctx.Options.get( i ).Block );
            _c.endThenBlock();
        }
        
        _c.exitBlock();
        _c.exitIfThenElse();
        
        return null;
    }
    
    // ========================================
    // Functions
    
    private static boolean isMethodBody( FuncBodyContext ctx )
    {
        return ( ctx.getParent() instanceof FuncDeclrStmtContext )
               && ( ( (FuncDeclrStmtContext) ctx.getParent() ).Name.MethodName != null );
    }
    
    private static List<String> getParameterNames( FuncBodyContext ctx )
    {
        List<String> paramNames = Lists.newArrayList();
        
        if ( isMethodBody( ctx ) )
        {
            paramNames.add( "self" );
        }
        
        if ( ctx.Params != null )
        {
            for ( Token t : ctx.Params.NameList.Names )
            {
                paramNames.add( t.getText() );
            }
        }
        
        return paramNames;
    }
    
    @Override
    public Void visitFuncName( FuncNameContext ctx )
    {
        if ( ctx.TableKeys.isEmpty() && ( ctx.MethodName == null ) )
        {
            _c.storeVariable( ctx.RootName.getText() );
        }
        else
        {
            _c.loadVariable( ctx.RootName.getText() );
            
            if ( !ctx.TableKeys.isEmpty() )
            {
                _c.loadConstant( ctx.TableKeys.get( 0 ).getText() );
                for ( int i = 0; i < ctx.TableKeys.size(); i++ )
                {
                    _c.loadFromTable();
                    _c.loadConstant( ctx.TableKeys.get( i ).getText() );
                }
            }
            
            if ( ctx.MethodName != null )
            {
                _c.loadFromTable();
                _c.loadConstant( ctx.MethodName.getText() );
            }
            
            _c.storeInTable();
        }
        
        return null;
    }
    
    @Override
    public Void visitFuncDeclrStmt( FuncDeclrStmtContext ctx )
    {
        visit( ctx.Body );
        visit( ctx.Name );
        
        return null;
    }
    
    @Override
    public Void visitLocalFuncDeclrStmt( LocalFuncDeclrStmtContext ctx )
    {
        String name = ctx.Name.getText();
        
        visit( ctx.Body );
        
        _c.declareLocal( name );
        _c.storeVariable( name );
        
        return null;
    }
    
    @Override
    public Void visitFuncBody( FuncBodyContext ctx )
    {
        String funcName = getFunctionName( ctx );
        int lineStart = ctx.getStart().getLine();
        int lineEnd = ctx.getStop().getLine();
        
        List<String> paramNames = getParameterNames( ctx );
        
        _c.enterFunction( funcName, lineStart, lineEnd, paramNames );
        
        visit( ctx.Body );
        
        _c.exitFunction();
        _c.createClosure();
        
        return null;
    }
    
    // ========================================
    // Assignments
    
    @Override
    public Void visitSimpleAssignmentStmt( SimpleAssignmentStmtContext ctx )
    {
        int nTargets = ctx.Targets.ExprList.size();
        adjustExprListResults( ctx.Exprs.Exprs, nTargets );
        
        visit( ctx.Targets );
        
        return null;
    }
    
    @Override
    public Void visitVarExprList( VarExprListContext ctx )
    {
        // Exprs are evaluated left to right, so they must be assigned right to left.
        visit( Lists.reverse( ctx.ExprList ) );
        return null;
    }
    
    @Override
    public Void visitLocalVarDeclrStmt( LocalVarDeclrStmtContext ctx )
    {
        for ( Token identifier : ctx.NameList.Names )
        {
            _c.declareLocal( identifier.getText() );
        }
        
        if ( ctx.ExprList == null )
            return null;
        
        int nTargets = ctx.NameList.Names.size();
        adjustExprListResults( ctx.ExprList.Exprs, nTargets );
        
        // Exprs are evaluated left to right, so they must be assigned right to left.
        for ( Token identifier : Lists.reverse( ctx.NameList.Names ) )
        {
            _c.storeVariable( identifier.getText() );
        }
        
        return null;
    }
    
    // ========================================
    // Calls
    
    @Override
    public Void visitCallArgs( CallArgsContext ctx )
    {
        int nArgs = 0;
        
        if ( ctx.Method != null )
        {
            String name = ctx.Method.getText();
            _c.loadMethod( name );
            nArgs++;
        }
        
        if ( ctx.Arg != null )
        {
            _c.loadConstant( ctx.Arg.getText() );
            nArgs++;
        }
        else if ( ctx.Args != null )
        {
            visit( ctx.Args );
            nArgs += ctx.Args.Exprs.size();
        }
        
        _c.callFunction( nArgs, ctx.nReturn );
        
        return null;
    }
    
    @Override
    public Void visitCall( CallContext ctx )
    {
        if ( ctx.getParent() instanceof StmtContext )
        {
            ctx.nReturn = 0;
        }
        
        ctx.Args.get( ctx.Args.size() - 1 ).nReturn = ctx.nReturn;
        return visitChildren( ctx );
    }
    
    @Override
    public Void visitReturnStmt( ReturnStmtContext ctx )
    {
        int nReturn = 0;
        
        if ( ctx.ExprList != null )
        {
            nReturn = ctx.ExprList.getChildCount();
            visit( ctx.ExprList );
        }
        
        _c.returnFunction( nReturn );
        return null;
    }
    
    // ========================================
    // Tables
    
    @Override
    public Void visitVarExpr( VarExprContext ctx )
    {
        // Simple Identifier?
        if ( ctx.Suffixes.isEmpty() )
        {
            if ( TableAssignPattern.matches( ctx.getParent() ) )
            {
                String name = ctx.Root.getText();
                _c.storeVariable( name );
            }
            else
            {
                _c.loadVariable( ctx.Root.getText() );
            }
            
            return null;
        }
        
        // Visit root
        if ( ctx.Root != null )
        {
            _c.loadVariable( ctx.Root.getText() );
        }
        else
        {
            visit( ctx.RootExpr );
        }
        
        // Visit suffixes
        visit( ctx.Suffixes.get( 0 ) );
        for ( int i = 1; i < ctx.Suffixes.size(); i++ )
        {
            _c.loadFromTable();
            
            VarSuffixContext suffix = ctx.Suffixes.get( i );
            visit( suffix );
        }
        
        // Determine if we assigning or accessing
        if ( TableAssignPattern.matches( ctx.getParent() ) )
        {
            _c.storeInTable();
        }
        else
        {
            _c.loadFromTable();
        }
        
        return null;
    }
    
    @Override
    public Void visitNameFieldAccess( NameFieldAccessContext ctx )
    {
        String fieldName = ctx.Field.getText();
        _c.loadConstant( fieldName );
        return null;
    }
    
    @Override
    public Void visitTableCtor( TableCtorContext ctx )
    {
        if ( ctx.FieldDefs.isEmpty() )
        {
            _c.createTable( 0, 0 );
            return null;
        }
        
        List<ListFieldContext> listFields = Lists.newArrayListWithExpectedSize( ( ctx.FieldDefs.size() + 1 ) / 2 );
        int hashPairs = 0;
        
        for ( FieldDefContext fieldDef : ctx.FieldDefs )
        {
            if ( fieldDef instanceof ListFieldContext )
            {
                listFields.add( (ListFieldContext) fieldDef );
            }
            else
            {
                visit( fieldDef );
                hashPairs++;
            }
        }
        
        visit( listFields );
        
        _c.createTable( listFields.size(), hashPairs );
        
        return null;
    }
    
    @Override
    public Void visitNameKeyField( NameKeyFieldContext ctx )
    {
        _c.loadConstant( ctx.Key.getText() );
        visit( ctx.Value );
        
        return null;
    }
    
    // ========================================
    // Blocks
    
    @Override
    public Void visitNestedBlock( NestedBlockContext ctx )
    {
        _c.enterBlock();
        visit( ctx.Block );
        _c.exitBlock();
        
        return null;
    }
    
    // ========================================
    // Labels
    
    @Override
    public Void visitLabelStmt( LabelStmtContext ctx )
    {
        _c.declareLabel( ctx.Name.getText() );
        return null;
    }
    
    @Override
    public Void visitGotoStmt( GotoStmtContext ctx )
    {
        _c.gotoLabel( ctx.Target.getText() );
        return null;
    }
    
    // ========================================
    // If-Then-Else
    
    @Override
    public Void visitIfThenElse( IfThenElseContext ctx )
    {
        _c.enterIfThenElseBlock();
        _c.enterBlock();
        
        visit( ctx.Condition );
        _c.endIfCondition();
        
        visit( ctx.Block );
        _c.endThenBlock();
        
        visit( ctx.ElseIfs );
        
        if ( ctx.Else != null )
        {
            visit( ctx.Else );
        }
        
        _c.exitBlock();
        _c.exitIfThenElse();
        
        return null;
    }
    
    @Override
    public Void visitElseIfBody( ElseIfBodyContext ctx )
    {
        _c.enterIfCondition();
        visit( ctx.Condition );
        _c.endIfCondition();
        visit( ctx.Block );
        _c.endThenBlock();
        
        return null;
    }
    
    @Override
    public Void visitElseBody( ElseBodyContext ctx )
    {
        _c.enterElseBlock();
        visit( ctx.Block );
        
        return null;
    }
    
    // ========================================
    // Loops
    
    @Override
    public Void visitWhileLoop( WhileLoopContext ctx )
    {
        _c.enterWhileLoop();
        visit( ctx.Condition );
        _c.enterWhileBody();
        visit( ctx.Block );
        _c.exitWhileLoop();
        
        return null;
    }
    
    @Override
    public Void visitRepeatLoop( RepeatLoopContext ctx )
    {
        _c.enterRepeatLoop();
        visit( ctx.Block );
        _c.enterUntilConditon();
        visit( ctx.Condition );
        _c.exitRepeatLoop();
        
        return null;
    }
    
    @Override
    public Void visitNumericForLoop( NumericForLoopContext ctx )
    {
        visit( ctx.Control.Start );
        visit( ctx.Control.Limit );
        
        if ( ctx.Control.Step != null )
        {
            visit( ctx.Control.Step );
        }
        else
        {
            _c.loadConstant( ONE );
        }
        
        _c.enterNumericForLoop( ctx.Control.Var.getText() );
        
        visit( ctx.Block );
        _c.exitForLoop();
        
        return null;
    }
    
    @Override
    public Void visitGenericForLoop( GenericForLoopContext ctx )
    {
        adjustExprListResults( ctx.Control.Exprs.Exprs, 3 );
        
        _c.enterGenericForLoop( getNames( ctx.Control.Vars ) );
        
        visit( ctx.Block );
        _c.exitForLoop();
        
        return null;
    }
    
    // ========================================
    // Operators
    
    @Override
    public Void visitUnaryOpExpr( UnaryOpExprContext ctx )
    {
        if ( "-".equals( ctx.Operator.getText() ) && ( ctx.Expr instanceof NumberLiteralContext ) )
        {
            NumberLiteralContext nlCtx = new NumberLiteralContext( ctx );
            nlCtx.addChild( new CommonToken( MtsParser.FLOAT, "-" + ctx.Expr.getText() ) );
            
            visit( nlCtx );
        }
        else
        {
            visit( ctx.Expr );
            _c.unaryOperation( ctx.Operator.getText() );
        }
        
        return null;
    }
    
    @Override
    public Void visitBinaryOpExpr( BinaryOpExprContext ctx )
    {
        visit( ctx.Left );
        visit( ctx.Right );
        _c.binaryOperation( ctx.Operator.getText() );
        return null;
    }
    
    @Override
    public Void visitLogicalOpExpr( LogicalOpExprContext ctx )
    {
        visit( ctx.Left );
        visit( ctx.Right );
        _c.logicOperation( ctx.Operator.getText() );
        return null;
    }
    
    @Override
    public Void visitConditionalOpExpr( ConditionalOpExprContext ctx )
    {
        visit( ctx.Left );
        _c.enterConditionalBlock( ctx.Operator.getText() );
        visit( ctx.Right );
        _c.exitConditionalBlock();
        return null;
    }
    
    // ========================================
    // Literals
    
    @Override
    public Void visitNullLiteral( NullLiteralContext ctx )
    {
        _c.loadNil();
        return null;
    }
    
    @Override
    public Void visitNumberLiteral( NumberLiteralContext ctx )
    {
        MtsNumber value = parseNumber( ctx.getText() );
        _c.loadConstant( value );
        return null;
    }
    
    @Override
    public Void visitBooleanLiteral( BooleanLiteralContext ctx )
    {
        MtsBoolean value = parseBoolean( ctx.getText() );
        _c.loadConstant( value );
        return null;
    }
    
    @Override
    public Void visitStringLiteral( StringLiteralContext ctx )
    {
//        MtsString value = parseString( ctx.getText() );
//        loadConstant( value );
//        
//        System.out.println( "Literal: " + value );
        
        _c.interpolateString( ctx.getText() );
        return null;
    }
    
    // ========================================
    // Utilities
    
    /**
     * Extension to the other visit methods.
     */
    public void visit( Iterable<? extends ParserRuleContext> ctxs )
    {
        for ( ParserRuleContext ctx : ctxs )
        {
            visit( ctx );
        }
    }
    
    /**
     * Returns an array of the given variable identifiers as strings.
     */
    private String[] getNames( NameListContext ctx )
    {
        String[] names = new String[ctx.Names.size()];
        for ( int i = 0; i < ctx.Names.size(); i++ )
        {
            names[i] = ctx.Names.get( i ).getText();
        }
        return names;
    }
    
    /**
     * Adjusts a given expression list so that all expressions are evaluated while adding exactly <code>nResults</code> values
     * to the stack. Values may be discarded or nils may be added to match the desired amount.
     */
    private void adjustExprListResults( List<ExprContext> exprs, int nResults )
    {
        int nExprs = exprs.size();
        
        for ( int i = 0; i < nExprs; i++ )
        {
            ExprContext exprCtx = exprs.get( i );
            
            if ( exprCtx instanceof CallExprContext )
            {
                CallContext call = ( (CallExprContext) exprCtx ).Call;
                
                if ( i == ( nExprs - 1 ) )
                { // A call at the end fills every remaining targets
                    call.nReturn = ( 1 + nResults ) - nExprs;
                    nExprs = nResults;
                }
                else if ( i < nResults )
                { // Calls with a target and not at the end return one value
                    call.nReturn = 1;
                }
                else
                { // Calls with no target return no value
                    call.nReturn = 0;
                }
                
                visit( call );
            }
            else
            {
                // Every expression *must* be evaluated because meta-methods could be involved.
                visit( exprCtx );
                
                if ( i >= nResults )
                {
                    _c.discardValue();
                }
            }
        }
        
        // Fill any leftover targets with nils
        _c.loadNil( nResults - nExprs );
    }
    
    /**
     * Extracts the name of a function.
     * <p>
     * This can be either
     * <ul>
     * <li>a method name</li>
     * <li>a table key of the function</li>
     * <li>a local variable name</li>
     * <li>or <code>Anonymous Function</code></li>
     * </ul>
     */
    private static String getFunctionName( FuncBodyContext ctx )
    {
        ParserRuleContext parent = ctx.getParent();
        if ( parent instanceof FuncDeclrStmtContext )
        {
            FuncNameContext nameCtx = ( (FuncDeclrStmtContext) parent ).Name;
            if ( nameCtx.MethodName != null )
                return nameCtx.MethodName.getText();
            if ( !nameCtx.TableKeys.isEmpty() )
                return nameCtx.TableKeys.get( nameCtx.TableKeys.size() - 1 ).getText();
            
            return nameCtx.RootName.getText();
        }
        else if ( parent instanceof LocalFuncDeclrStmtContext )
        {
            return ( (LocalFuncDeclrStmtContext) parent ).Name.getText();
        }
        else if ( parent instanceof FuncDeclrExprContext )
        {
            return "Anonymous Function";
        }
        else
        {
            throw new AssertionError();
        }
    }
    
}
