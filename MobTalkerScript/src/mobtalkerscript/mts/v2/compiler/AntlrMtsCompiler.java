package mobtalkerscript.mts.v2.compiler;

import static mobtalkerscript.mts.v2.value.MtsValue.*;

import java.util.*;

import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.AssignExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.BinaryOpExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.BooleanLiteralContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.CallArgsContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.CallContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.CallExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ChunkContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.CommandHideContext;
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
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.IfThenElseContext;
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
public class AntlrMtsCompiler extends MtsCompilerBase
{
    private static final ITreePattern TableAssignPattern;
    
    static
    {
        TableAssignPattern = new TreeMultiPattern( new TreePattern( AssignExprContext.class ),
                                                   new TreePattern( VarExprListContext.class,
                                                                    AssignExprContext.class ) );
    }
    
    // ========================================
    
    public AntlrMtsCompiler( ChunkContext ctx )
    {
        super( ctx.getStart().getTokenSource().getSourceName(), //
               ctx.getStart().getLine(),
               ctx.getStop().getLine() );
        
        visit( ctx );
    }
    
    // ========================================
    // Chunk
    
    @Override
    public Void visitChunk( ChunkContext ctx )
    {
        System.out.println( "Enter Chunk" );
        
        visitChildren( ctx );
        returnFunction( 0 );
        
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
            createTable( exprs, 0 );
        }
    }
    
    @Override
    public Void visitCommandSay( CommandSayContext ctx )
    {
        loadVariable( MtsCommandLib.FNAME_SAY );
        
        if ( ctx.Character == null )
            loadNil();
        else
            visit( ctx.Character );
        
        visitSingleOrCreateTable( ctx.Text.Exprs );
        
        loadConstant( valueOf( ctx.IsLast != null ) );
        
        callFunction( 3, 0 );
        
        return null;
    }
    
    @Override
    public Void visitCommandShow( CommandShowContext ctx )
    {
        loadVariable( MtsCommandLib.FNAME_SHOW );
        
        visitSingleOrCreateTable( ctx.Path );
        
        if ( ctx.Position == null )
            loadNil();
        else
            visit( ctx.Position );
        
        if ( ctx.Offset == null )
        {
            loadConstant( ZERO );
            loadConstant( ZERO );
        }
        else
        {
            visit( ctx.Offset.Exprs.get( 0 ) );
            if ( ctx.Offset.Exprs.size() < 2 )
                loadConstant( ZERO );
            else
                visit( ctx.Offset.Exprs.get( 1 ) );
        }
        
        callFunction( 4, 0 );
        
        return null;
    }
    
    @Override
    public Void visitCommandScene( CommandSceneContext ctx )
    {
        loadVariable( MtsCommandLib.FNAME_SCENE );
        
        visitSingleOrCreateTable( ctx.Path );
        
        if ( ctx.Mode == null )
            loadNil();
        else
            visit( ctx.Mode );
        
        callFunction( 2, 0 );
        
        return null;
    }
    
    @Override
    public Void visitCommandHide( CommandHideContext ctx )
    {
        loadVariable( MtsCommandLib.FNAME_HIDE );
        
        visit( ctx.Group );
        
        callFunction( 1, 0 );
        
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
            storeVariable( ctx.RootName.getText() );
        }
        else
        {
            loadVariable( ctx.RootName.getText() );
            
            if ( !ctx.TableKeys.isEmpty() )
            {
                loadConstant( ctx.TableKeys.get( 0 ).getText() );
                for ( int i = 0; i < ctx.TableKeys.size(); i++ )
                {
                    loadFromTable();
                    loadConstant( ctx.TableKeys.get( i ).getText() );
                }
            }
            
            if ( ctx.MethodName != null )
            {
                loadFromTable();
                loadConstant( ctx.MethodName.getText() );
            }
            
            storeInTable();
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
        
        declareLocal( name );
        storeVariable( name );
        
        return null;
    }
    
    @Override
    public Void visitFuncBody( FuncBodyContext ctx )
    {
        String funcName = getFunctionName( ctx );
        int lineStart = ctx.getStart().getLine();
        int lineEnd = ctx.getStop().getLine();
        
        List<String> paramNames = getParameterNames( ctx );
        
        System.out.println( "EnterFunction " + funcName + ", " + paramNames );
        
        enterFunction( funcName, lineStart, lineEnd, paramNames );
        
        visit( ctx.Body );
        
        System.out.println( "ExitFunction" );
        exitFunction();
        createClosure();
        
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
            System.out.println( "Declaring local " + identifier.getText() );
            declareLocal( identifier.getText() );
        }
        
        if ( ctx.ExprList == null )
            return null;
        
        int nTargets = ctx.NameList.Names.size();
        adjustExprListResults( ctx.ExprList.Exprs, nTargets );
        
        // Exprs are evaluated left to right, so they must be assigned right to left.
        for ( Token identifier : Lists.reverse( ctx.NameList.Names ) )
        {
            storeVariable( identifier.getText() );
        }
        
        return null;
    }
    
    // ========================================
    // Calls
    
    @Override
    public Void visitCallArgs( CallArgsContext ctx )
    {
        if ( ctx.Method != null )
        {
            String name = ctx.Method.getText();
            loadMethod( name );
        }
        
        int nArgs;
        if ( ctx.Arg != null )
        {
            nArgs = 1;
            loadConstant( ctx.Arg.getText() );
        }
        else if ( ctx.Args != null )
        {
            nArgs = ctx.Args.Exprs.size();
            visit( ctx.Args );
        }
        else
        {
            nArgs = 0;
        }
        
        callFunction( nArgs, ctx.nReturn );
        
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
        
        System.out.println( "Return values: " + ctx.nReturn );
        
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
        
        returnFunction( nReturn );
        
        System.out.println( "Return " + nReturn );
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
                storeVariable( name );
            }
            else
            {
                loadVariable( ctx.Root.getText() );
            }
            
            return null;
        }
        
        // Visit root
        if ( ctx.Root != null )
        {
            loadVariable( ctx.Root.getText() );
        }
        else
        {
            visit( ctx.RootExpr );
        }
        
        // Visit suffixes
        visit( ctx.Suffixes.get( 0 ) );
        for ( int i = 1; i < ctx.Suffixes.size(); i++ )
        {
            loadFromTable();
            
            VarSuffixContext suffix = ctx.Suffixes.get( i );
            visit( suffix );
        }
        
        // Determine if we assigning or accessing
        if ( TableAssignPattern.matches( ctx.getParent() ) )
        {
            storeInTable();
        }
        else
        {
            loadFromTable();
        }
        
        return null;
    }
    
    @Override
    public Void visitNameFieldAccess( NameFieldAccessContext ctx )
    {
        String fieldName = ctx.Field.getText();
        loadConstant( fieldName );
        
        System.out.println( "Table Key: " + fieldName );
        
        return null;
    }
    
    @Override
    public Void visitTableCtor( TableCtorContext ctx )
    {
        if ( ctx.FieldDefs.isEmpty() )
        {
            createTable( 0, 0 );
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
        
        createTable( listFields.size(), hashPairs );
        
        return null;
    }
    
    @Override
    public Void visitNameKeyField( NameKeyFieldContext ctx )
    {
        loadConstant( ctx.Key.getText() );
        visit( ctx.Value );
        
        return null;
    }
    
    // ========================================
    // Blocks
    
    @Override
    public Void visitNestedBlock( NestedBlockContext ctx )
    {
        enterBlock();
        visit( ctx.Block );
        leaveBlock();
        
        return null;
    }
    
    // ========================================
    // If-Then-Else
    
    @Override
    public Void visitIfThenElse( IfThenElseContext ctx )
    {
        enterIfThenElseBlock();
        
        visit( ctx.Condition );
        endIfCondition();
        
        visit( ctx.Block );
        endThenBlock();
        
        visit( ctx.ElseIfs );
        
        if ( ctx.Else != null )
        {
            visit( ctx.Else );
        }
        
        exitIfThenElse();
        
        return null;
    }
    
    @Override
    public Void visitElseIfBody( ElseIfBodyContext ctx )
    {
        enterIfCondition();
        visit( ctx.Condition );
        endIfCondition();
        visit( ctx.Block );
        endThenBlock();
        
        return null;
    }
    
    @Override
    public Void visitElseBody( ElseBodyContext ctx )
    {
        enterElseBlock();
        visit( ctx.Block );
        
        return null;
    }
    
    // ========================================
    // Loops
    
    @Override
    public Void visitWhileLoop( WhileLoopContext ctx )
    {
        enterWhileLoop();
        visit( ctx.Condition );
        enterWhileBody();
        visit( ctx.Block );
        exitWhileLoop();
        
        return null;
    }
    
    @Override
    public Void visitRepeatLoop( RepeatLoopContext ctx )
    {
        enterRepeatLoop();
        visit( ctx.Block );
        enterUntilConditon();
        visit( ctx.Condition );
        exitRepeatLoop();
        
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
            loadConstant( ONE );
        }
        
        enterNumericForLoop( ctx.Control.Var.getText() );
        
        visit( ctx.Block );
        exitForLoop();
        
        return null;
    }
    
    @Override
    public Void visitGenericForLoop( GenericForLoopContext ctx )
    {
        adjustExprListResults( ctx.Control.Exprs.Exprs, 3 );
        
        enterGenericForLoop( getNames( ctx.Control.Vars ) );
        
        visit( ctx.Block );
        exitForLoop();
        
        return null;
    }
    
    // ========================================
    // Operators
    
    @Override
    public Void visitUnaryOpExpr( UnaryOpExprContext ctx )
    {
        visit( ctx.Expr );
        unaryOperation( ctx.Operator.getText() );
        
        System.out.println( "UnaryOp: " + ctx.Operator.getText() );
        return null;
    }
    
    @Override
    public Void visitBinaryOpExpr( BinaryOpExprContext ctx )
    {
        visit( ctx.Left );
        visit( ctx.Right );
        binaryOperation( ctx.Operator.getText() );
        
        System.out.println( "BinaryOp: " + ctx.Operator.getText() );
        return null;
    }
    
    @Override
    public Void visitLogicalOpExpr( LogicalOpExprContext ctx )
    {
        visit( ctx.Left );
        visit( ctx.Right );
        logicOperation( ctx.Operator.getText() );
        
        System.out.println( "LogicalOp: " + ctx.Operator.getText() );
        return null;
    }
    
    @Override
    public Void visitConditionalOpExpr( ConditionalOpExprContext ctx )
    {
        visit( ctx.Left );
        enterConditionalBlock( ctx.Operator.getText() );
        visit( ctx.Right );
        exitConditionalBlock();
        
        System.out.println( "ConditionalOp: " + ctx.Operator.getText() );
        return null;
    }
    
    // ========================================
    // Literals
    
    @Override
    public Void visitNullLiteral( NullLiteralContext ctx )
    {
        loadNil();
        
        System.out.println( "Literal: nil" );
        return null;
    }
    
    @Override
    public Void visitNumberLiteral( NumberLiteralContext ctx )
    {
        MtsNumber value = parseNumber( ctx.getText() );
        loadConstant( value );
        
        System.out.println( "Literal: " + value );
        return null;
    }
    
    @Override
    public Void visitBooleanLiteral( BooleanLiteralContext ctx )
    {
        MtsBoolean value = parseBoolean( ctx.getText() );
        loadConstant( value );
        
        System.out.println( "Literal: " + value );
        return null;
    }
    
    @Override
    public Void visitStringLiteral( StringLiteralContext ctx )
    {
//        MtsString value = parseString( ctx.getText() );
//        loadConstant( value );
//        
//        System.out.println( "Literal: " + value );
        
        System.out.println( "Interpolating string: " + ctx.getText() );
        
        interpolateString( ctx.getText() );
        
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
                    discardValue();
                }
            }
        }
        
        // Fill any leftover targets with nils
        loadNil( nResults - nExprs );
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
