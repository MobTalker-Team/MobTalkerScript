package mobtalkerscript.mts.v2.compiler;

import static com.google.common.base.Preconditions.*;

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
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.LocalFuncDeclrStmtContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.LogicalOpExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.NameFieldAccessContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.NullLiteralContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.NumberLiteralContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ReturnStmtContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.SimpleAssignmentStmtContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.StmtContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.StringLiteralContext;
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
        CallAssignExprPattern = new TreePattern( CallContext.class,
                                                 ExprContext.class,
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
    
    @Override
    public Void visitSimpleAssignmentStmt( SimpleAssignmentStmtContext ctx )
    {
        int nTargets = ctx.VarExprList.ExprList.size();
        int nExprs = ctx.ExprList.Exprs.size();
        
        for ( int i = 0; i < nExprs; i++ )
        {
            ExprContext exprCtx = ctx.ExprList.Exprs.get( i );
            
            if ( exprCtx instanceof CallExprContext )
            {
                CallExprContext callCtx = (CallExprContext) exprCtx;
                
                int nCallReturn;
                
                if ( i == ( nExprs - 1 ) )
                { // A call at the end fills every remaining target
                    nCallReturn = ( 1 + nTargets ) - nExprs;
                    nExprs = nTargets;
                }
                
                else if ( i < nTargets )
                { // Calls with a target and not at the end return one value
                    nCallReturn = 1;
                }
                else
                { // Calls with no target return no value
                    nCallReturn = 0;
                }
                
                visitCallExpr( callCtx, nCallReturn );
            }
            else
            {
                visit( exprCtx );
            }
        }
        
        int nils = nTargets - nExprs;
        for ( int i = 0; i < nils; i++ )
        {
            loadNil();
        }
        
        visit( ctx.VarExprList );
        
        return null;
    }
    
    // ========================================
    // Calls
    
    @Override
    public Void visitCallExpr( CallExprContext ctx )
    {
        throw new AssertionError();
    }
    
    public void visitCallExpr( CallExprContext ctx, int nReturn )
    {
        checkArgument( nReturn >= nReturn, "Cannot have negative amount of return values!" );
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
        boolean isAssignment = TableAssignPattern.matches( ctx.getParent() );
        
        if ( ctx.Suffixes.isEmpty() && isAssignment )
        {
            storeVariable( ctx.Root.getText() );
            return null;
        }
        
        if ( ctx.Root != null )
        {
            loadVariable( ctx.Root.getText() );
        }
        else
        {
            visit( ctx.RootExpr );
        }
        
        for ( int i = 1; i < ctx.Suffixes.size(); i++ )
        {
            VarSuffixContext suffix = ctx.Suffixes.get( i - 1 );
            visit( suffix );
            loadFromTable();
        }
        
        if ( isAssignment )
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
    
    // ========================================
    // Operators
    
    @Override
    public Void visitUnaryOpExpr( UnaryOpExprContext ctx )
    {
        visit( ctx.Expr );
        unaryOperation( ctx.Operator.getText() );
        
        System.out.println( ctx.Operator.getText() );
        return null;
    }
    
    @Override
    public Void visitBinaryOpExpr( BinaryOpExprContext ctx )
    {
        visit( ctx.Left );
        visit( ctx.Right );
        binaryOperation( ctx.Operator.getText() );
        
        System.out.println( ctx.Operator.getText() );
        return null;
    }
    
    @Override
    public Void visitLogicalOpExpr( LogicalOpExprContext ctx )
    {
        visit( ctx.Left );
        visit( ctx.Right );
        logicOperation( ctx.Operator.getText() );
        
        System.out.println( ctx.Operator.getText() );
        return null;
    }
    
    @Override
    public Void visitConditionalOpExpr( ConditionalOpExprContext ctx )
    {
        visit( ctx.Left );
        enterConditionalBlock( ctx.Operator.getText() );
        visit( ctx.Right );
        exitConditionalBlock();
        
        System.out.println( ctx.Operator.getText() );
        return null;
    }
    
    // ========================================
    // Literals
    
    @Override
    public Void visitNullLiteral( NullLiteralContext ctx )
    {
        loadNil();
        return null;
    }
    
    @Override
    public Void visitNumberLiteral( NumberLiteralContext ctx )
    {
        MtsNumber value = parseNumber( ctx.getText() );
        loadConstant( value );
        
        System.out.println( value );
        return null;
    }
    
    @Override
    public Void visitBooleanLiteral( BooleanLiteralContext ctx )
    {
        MtsBoolean value = parseBoolean( ctx.getText() );
        loadConstant( value );
        
        System.out.println( value );
        return null;
    }
    
    @Override
    public Void visitStringLiteral( StringLiteralContext ctx )
    {
        MtsString value = parseString( ctx.getText() );
        loadConstant( value );
        
        System.out.println( value );
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
