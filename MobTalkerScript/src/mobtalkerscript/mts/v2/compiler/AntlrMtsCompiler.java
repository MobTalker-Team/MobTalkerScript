package mobtalkerscript.mts.v2.compiler;

import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.AssignExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.BinaryOpExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.BooleanLiteralContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.CallContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ChunkContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ConditionalOpExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ExprListContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.FuncBodyContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.LogicalOpExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.NullLiteralContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.NumberLiteralContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ReturnStmtContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.StmtContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.StringLiteralContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.UnaryOpExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.VarSuffixContext;
import mobtalkerscript.mts.v2.value.*;

@SuppressWarnings( "unchecked" )
public class AntlrMtsCompiler extends MtsCompilerBase
{
    
    private static final CallPattern CallExprPattern;
    private static final CallPattern CallStmtPattern;
    private static final CallPattern CallSuffixExprPattern;
    private static final CallPattern CallAssignExprPattern;
    
    static
    {
        CallStmtPattern = new CallPattern( CallContext.class, StmtContext.class );
        CallExprPattern = new CallPattern( CallContext.class, ExprContext.class );
        CallSuffixExprPattern = new CallPattern( VarSuffixContext.class );
        CallAssignExprPattern = new CallPattern( CallContext.class,
                                                 ExprContext.class,
                                                 ExprListContext.class,
                                                 AssignExprContext.class );
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
    
    @Override
    public Void visitFuncBody( FuncBodyContext ctx )
    {
        System.out.println( "Enter FuncBody" );
        
        visitChildren( ctx );
        returnFunction( 0 );
        
        System.out.println( "Exit FuncBody" );
        return null;
    }
    
    // ========================================
    // Calls
    
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
    // Operator
    
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
}
