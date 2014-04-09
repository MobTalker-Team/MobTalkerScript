package mobtalkerscript.mts.v2.compiler;

import static mobtalkerscript.mts.v2.instruction.InstructionCache.*;
import static mobtalkerscript.mts.v2.value.MtsValue.*;

import java.util.*;

import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.AssignExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.BinaryOpExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.BooleanLiteralContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.CallContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.CallExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ChunkContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ConditionalOpExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ExprListContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.FuncBodyContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.FuncDeclrExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.FuncDeclrStmtContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.FuncNameContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.FunctionCallContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.LocalFuncDeclrStmtContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.LocalVarDeclrStmtContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.LogicalOpExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.MethodCallContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.NameFieldAccessContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.NullLiteralContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.NumberLiteralContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ReturnStmtContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.SimpleAssignmentStmtContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.StmtContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.StringLiteralContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.TableCtorContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.UnaryOpExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.VarExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.VarExprListContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.VarSuffixContext;
import mobtalkerscript.mts.v2.value.*;

import org.antlr.v4.runtime.*;

import com.google.common.collect.*;

@SuppressWarnings( "unchecked" )
public class AntlrMtsCompiler extends MtsCompilerBase
{
    
    private static final ITreePattern CallExprPattern;
    private static final ITreePattern CallStmtPattern;
    private static final ITreePattern CallSuffixExprPattern;
    private static final ITreePattern CallAssignExprPattern;
    
    private static final ITreePattern TableAssignPattern;
    
    static
    {
        CallStmtPattern = new TreePattern( CallContext.class, StmtContext.class );
        CallExprPattern = new TreePattern( CallContext.class, ExprContext.class );
        CallSuffixExprPattern = new TreePattern( VarSuffixContext.class );
        CallAssignExprPattern = new TreePattern( ExprContext.class,
                                                 ExprListContext.class,
                                                 AssignExprContext.class );
        
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
    // Functions
    
    private static List<String> getParameterNames( FuncBodyContext ctx )
    {
        List<String> paramNames = Lists.newArrayList();
        
        if ( ctx.getParent() instanceof FuncDeclrStmtContext )
        {
            if ( ( (FuncDeclrStmtContext) ctx.getParent() ).Name.MethodName != null )
            {
                paramNames.add( "self" );
            }
        }
        
        for ( Token t : ctx.Params.NameList.Names )
        {
            paramNames.add( t.getText() );
        }
        
        return paramNames;
    }
    
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
    public Void visitFuncBody( FuncBodyContext ctx )
    {
        String funcName = getFunctionName( ctx );
        int lineStart = ctx.getStart().getLine();
        int lineEnd = ctx.getStop().getLine();
        
        List<String> paramNames = getParameterNames( ctx );
        
        System.out.println( "EnterFunction " + funcName + ", " + paramNames );
        
        enterFunction( funcName, lineStart, lineEnd, paramNames );
        
        visit( ctx.Body );
        returnFunction( 0 );
        
        System.out.println( "ExitFunction" );
        exitFunction();
        createClosure();
        
        return null;
    }
    
    // ========================================
    // Assignments
    
    /**
     * Adjusts a given expression list so that all expressions are evaluated while adding exactly <code>nResults</code> values
     * to the stack. Values may be discarded or nils may be pushed to match the desired amount.
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
                    addInstr( InstrPop() );
                }
            }
        }
        
        // Fill any leftover targets with nils
        loadNil( nResults - nExprs );
    }
    
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
    public Void visitFunctionCall( FunctionCallContext ctx )
    {
        int nArgs = ctx.Args.Exprs.size();
        
        visit( ctx.Args );
        callFunction( nArgs, ctx.nReturn );
        
        return null;
    }
    
    @Override
    public Void visitMethodCall( MethodCallContext ctx )
    {
        int nArgs = ctx.Args.Exprs.size();
        String name = ctx.Method.getText();
        
        // self
        addInstr( InstrDup() );
        loadConstant( valueOf( name ) );
        loadFromTable();
        
        visit( ctx.Args );
        callFunction( nArgs + 1, ctx.nReturn );
        
        return null;
    }
    
    @Override
    public Void visitCall( CallContext ctx )
    {
        Lists.reverse( ctx.Args ).get( 0 ).nReturn = ctx.nReturn;
        
        System.out.println( "Return values: " + ctx.nReturn );
        
        return visitChildren( ctx );
    }
    
    @Override
    public Void visitReturnStmt( ReturnStmtContext ctx )
    {
        int nReturn = ctx.ExprList.getChildCount();
        
        visit( ctx.ExprList );
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
                storeVariable( ctx.Root.getText() );
            else
                loadVariable( ctx.Root.getText() );
            
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
    public Void visitTableCtor( TableCtorContext ctx )
    {
        // TODO Fields
        
        createTable();
        
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
        MtsString value = parseString( ctx.getText() );
        loadConstant( value );
        
        System.out.println( "Literal: " + value );
        return null;
    }
    
    // ========================================
    // Utilities
    
    public void visit( Iterable<? extends ParserRuleContext> ctxs )
    {
        for ( ParserRuleContext ctx : ctxs )
        {
            visit( ctx );
        }
    }
    
}
