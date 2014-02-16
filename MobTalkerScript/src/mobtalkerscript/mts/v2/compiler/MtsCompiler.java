package mobtalkerscript.mts.v2.compiler;

import static mobtalkerscript.mts.v2.value.MtsValue.*;

import java.util.*;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.compiler.antlr.*;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.AssignmentTargetContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.BinaryOpExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ChunkContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ConditionalBlockExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ExprListContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.LiteralBooleanContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.LiteralNullContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.LiteralNumberContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.LiteralStringContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.LocalVariableDeclrContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.LogicalOpExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.NameAccessExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.PrefixOpExprContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ReturnStmtContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.SimpleAssignmentStmtContext;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.SimpleAssignmentTargetContext;
import mobtalkerscript.mts.v2.instruction.*;
import mobtalkerscript.mts.v2.value.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import com.google.common.collect.*;

public class MtsCompiler extends MtsBaseVisitor<Void>
{
    public static final String G = "_G";
    public static final String ENV = "_ENV";
    
    // ========================================
    // Cached Instructions
    
    private static final Map<String, MtsInstruction> _prefixOps;
    private static final Map<String, MtsInstruction> _binaryOps;
    private static final Map<String, MtsInstruction> _logicalOps;
    private static final Map<String, MtsInstruction> _assignOps;
    
    static
    {
        _prefixOps = Maps.newHashMap();
        _prefixOps.put( "not", new InstrNot() );
        _prefixOps.put( "-", new InstrNeg() );
        _prefixOps.put( "#", new InstrSize() );
        
        _binaryOps = Maps.newHashMap();
        _binaryOps.put( "+", new InstrAdd() );
        _binaryOps.put( "-", new InstrSub() );
        _binaryOps.put( "*", new InstrMul() );
        _binaryOps.put( "/", new InstrDiv() );
        _binaryOps.put( "%", new InstrMod() );
        _binaryOps.put( "^", new InstrPow() );
        
        _logicalOps = Maps.newHashMap();
        _logicalOps.put( "==", new InstrEQ() );
        _logicalOps.put( "<", new InstrLT() );
        _logicalOps.put( "<=", new InstrLTE() );
        
        _assignOps = Maps.newHashMap();
        _assignOps.put( "+=", _binaryOps.get( "+" ) );
        _assignOps.put( "-=", _binaryOps.get( "-" ) );
        _assignOps.put( "*=", _binaryOps.get( "*" ) );
        _assignOps.put( "/=", _binaryOps.get( "/" ) );
        _assignOps.put( "%=", _binaryOps.get( "%" ) );
        _assignOps.put( "^=", _binaryOps.get( "^" ) );
        _assignOps.put( ".=", _binaryOps.get( ".." ) );
    }
    
    private static final MtsInstruction _not = new InstrNot();
    
    private static final MtsInstruction _loadT = new InstrLoadT();
    private static final MtsInstruction _storeT = new InstrStoreT();
    
    // ========================================
    
    private final FunctionScope _mainFunction;
    private FunctionScope _currentFunction;
    
    private final String _sourceFile;
    private int _curLine;
    private int _curColoum;
    
    public MtsCompiler( ChunkContext ctx )
    {
        String sourceName = ctx.getStart().getTokenSource().getSourceName();
        int sourceLineStart = ctx.getStart().getLine();
        int sourceLineEnd = ctx.getStop().getLine();
        
        _mainFunction = new FunctionScope( null, "main", sourceName, sourceLineStart, sourceLineEnd );
        _currentFunction = _mainFunction;
        _sourceFile = sourceName;
        
        visit( ctx );
    }
    
    public MtsCompiler( String sourceName, int sourceLineStart, int sourceLineEnd )
    {
        _mainFunction = new FunctionScope( null, "main", sourceName, sourceLineStart, sourceLineEnd );
        _currentFunction = _mainFunction;
        _sourceFile = sourceName;
    }
    
    // ========================================
    
    private void addInstr( MtsInstruction instr )
    {
        _currentFunction.addInstruction( instr, _curLine, _curColoum );
    }
    
    private void setJumpTarget()
    {
        _currentFunction.setJumpTarget();
    }
    
    public void setLineNumber( int line, int coloum )
    {
        _curLine = line;
        _curColoum = coloum;
    }
    
    // ========================================
    
    public void declareFunction( String name, int sourceLineStart, int sourceLineEnd, String... params )
    {
        FunctionScope child = new FunctionScope( _currentFunction,
                                                 name,
                                                 _sourceFile,
                                                 sourceLineStart,
                                                 sourceLineEnd );
        _currentFunction.addChild( child );
        
        _currentFunction = child;
        
        for ( String param : params )
        {
            declareLocal( param );
        }
    }
    
    public void leaveFunction()
    {
        addInstr( new InstrReturn( 0 ) );
        _currentFunction = _currentFunction.getParent();
    }
    
    // ========================================
    
    public void enterBlock()
    {
        _currentFunction.enterBlock();
    }
    
    public void leaveBlock()
    {
        _currentFunction.leaveBlock();
    }
    
    // ========================================
    
    public void beginWhileCondition()
    {
        _currentFunction.enterLoopCondition();
    }
    
    public void beginWhileBody()
    {
        addInstr( new InstrTest() );
        _currentFunction.enterLoopBody();
        _currentFunction.enterBlock();
    }
    
    public void endWhile()
    {
        addInstr( new InstrJump() );
        _currentFunction.leaveLoopBlock();
    }
    
    public void breakLoop()
    {
        addInstr( new InstrJump() );
        _currentFunction.addPendingBreak();
    }
    
    // ========================================
    
//    public abstract void declareLabel( String name );
    
//    public abstract void gotoLabel( String name );
    
//    public abstract void gotoFunction( String name );
    
    // ========================================
    
    public void declareLocal( String name )
    {
        _currentFunction.declareLocal( name );
    }
    
    public void load( String name )
    {
        if ( _currentFunction.isLocal( name ) )
        { // Local
            int index = _currentFunction.getLocalIndex( name );
            addInstr( new InstrLoadL( index ) );
        }
        else if ( _currentFunction.isExternal( name ) )
        { // External
            int index = _currentFunction.getExternalIndex( name );
            addInstr( new InstrLoadE( index ) );
        }
        else
        { // Global
            loadEnvironment();
            loadConstant( valueOf( name ) );
            loadFromTable();
        }
    }
    
    public void loadEnvironment()
    {
        // If-Else inverted since environment will most likely be an external.
        if ( _currentFunction.isExternal( ENV ) )
        {
            int index = _currentFunction.getExternalIndex( ENV );
            addInstr( new InstrLoadE( index ) );
        }
        else if ( _currentFunction.isLocal( ENV ) )
        {
            int index = _currentFunction.getLocalIndex( ENV );
            addInstr( new InstrLoadL( index ) );
        }
        else
        {
            throw new IllegalStateException( ENV + " is neither a local nor an external!" );
        }
    }
    
    public void loadConstant( MtsValue value )
    {
        int index = _currentFunction.getConstantIndex( value );
        addInstr( new InstrLoadC( index ) );
    }
    
    public void store( String name )
    {
        if ( _currentFunction.isLocal( name ) )
        { // Local
            int index = _currentFunction.getLocalIndex( name );
            addInstr( new InstrStoreL( index ) );
        }
        else if ( _currentFunction.isExternal( name ) )
        { // External
            int index = _currentFunction.getExternalIndex( name );
            addInstr( new InstrStoreE( index ) );
        }
        else
        { // Global
            loadConstant( valueOf( name ) );
            loadEnvironment();
            storeInTable();
        }
    }
    
    // ========================================
    
    public void loadFromTable()
    {
        addInstr( _loadT );
    }
    
    public void storeInTable()
    {
        addInstr( _storeT );
    }
    
    // ========================================
    
    public void assignmentOperation( String op )
    {
        if ( !_assignOps.containsKey( op ) )
            throw new IllegalArgumentException( op );
        
        addInstr( _assignOps.get( op ) );
    }
    
    public void prefixOperation( String op )
    {
        if ( !_prefixOps.containsKey( op ) )
            throw new IllegalArgumentException( op );
        
        addInstr( _prefixOps.get( op ) );
    }
    
//    public abstract void postfixOperation( String op );
    
    public void binaryOperation( String op )
    {
        if ( !_binaryOps.containsKey( op ) )
            throw new IllegalArgumentException( op );
        
        addInstr( _binaryOps.get( op ) );
    }
    
    public void logicOperation( String op )
    {
        if ( _logicalOps.containsKey( op ) )
        {
            addInstr( _logicalOps.get( op ) );
        }
        else if ( ">".equals( op ) )
        {
            addInstr( _logicalOps.get( "<=" ) );
            addInstr( _not );
        }
        else if ( ">=".equals( op ) )
        {
            addInstr( _logicalOps.get( "<" ) );
            addInstr( _not );
        }
        else if ( "!=".equals( op ) )
        {
            addInstr( _logicalOps.get( "==" ) );
            addInstr( _not );
        }
        else
        {
            throw new IllegalArgumentException( op );
        }
    }
    
    public void beginConditionalBlock( String op )
    {
        if ( "and".equals( op ) )
        {
            addInstr( new InstrAnd() );
        }
        else if ( "or".equals( op ) )
        {
            addInstr( new InstrOr() );
        }
        else
        {
            throw new IllegalArgumentException( op );
        }
        
        _currentFunction.markPendingJump();
    }
    
    public void endConditionalBlock()
    {
        setJumpTarget();
    }
    
    // ========================================
    
//    public abstract void createClosure( int prototype );
    
    public void callFunction( int nArgs, int nReturn )
    {
        addInstr( new InstrCallFunc( nArgs, nReturn ) );
    }
    
    public void callMethod( String name, int nArgs, int nReturn )
    {
        addInstr( new InstrDup() );
        loadConstant( valueOf( name ) );
        callFunction( nArgs + 1, nReturn );
    }
    
    public void returnFunction( int nValues )
    {
        addInstr( new InstrReturn( nValues ) );
    }
    
    // ========================================
    
    public MtsFunctionPrototype compile()
    {
        if ( _currentFunction != _mainFunction )
            throw new IllegalStateException();
        
        return _mainFunction.createPrototype();
    }
    
    // ========================================
    
    @Override
    public Void visit( ParseTree tree )
    {
        if ( tree instanceof ParserRuleContext )
        {
            ParserRuleContext ctx = (ParserRuleContext) tree;
            int line = ctx.getStart().getLine();
            int col = ctx.getStart().getCharPositionInLine();
            setLineNumber( line, col );
        }
        
        return super.visit( tree );
    }
    
    public void visit( List<? extends ParseTree> trees )
    {
        for ( ParseTree tree : trees )
        {
            visit( tree );
        }
    }
    
    @Override
    protected boolean shouldVisitNextChild( RuleNode node, Void currentResult )
    {
        if ( node instanceof ParserRuleContext )
        {
            ParserRuleContext ctx = (ParserRuleContext) node;
            int line = ctx.getStart().getLine();
            int col = ctx.getStart().getCharPositionInLine();
            setLineNumber( line, col );
        }
        
        return true;
    }
    
    // ========================================
    
    @Override
    public Void visitReturnStmt( ReturnStmtContext ctx )
    {
        ExprListContext exprs = ctx.exprList();
        
        int nValues = exprs == null ? 0 : exprs.expr().size();
        visit( exprs );
        
        returnFunction( nValues );
        
        return null;
    }
    
    // ========================================
    
    @Override
    public Void visitLocalVariableDeclr( LocalVariableDeclrContext ctx )
    {
        List<TerminalNode> targets = ctx.Names.Name();
        List<ExprContext> exprs = ctx.Exprs.expr();
        
        if ( exprs != null )
        {
            for ( int i = 0; i < targets.size(); i++ )
            {
                if ( i < exprs.size() )
                {
                    visit( exprs.get( i ) );
                }
                else
                {
                    break;
                }
            }
        }
        
        for ( int i = targets.size() - 1; i >= 0; i-- )
        {
            String target = targets.get( i ).getText();
            
            declareLocal( target );
            if ( i < exprs.size() )
            {
                store( target );
            }
        }
        
        // TODO
        // For now only expressions that have matching targets are evaluated.
        // Might want to change this since Lua does evaluate leftovers.
        
        return null;
    }
    
    @Override
    public Void visitSimpleAssignmentStmt( SimpleAssignmentStmtContext ctx )
    {
        List<AssignmentTargetContext> targets = ctx.Targets.assignmentTarget();
        List<ExprContext> exprs = ctx.Exprs.expr();
        
        // Expressions are evaluated left to right and assigned right to left
        // So this works: a, b = b, a;
        
        for ( int i = 0; i < targets.size(); i++ )
        {
            if ( i < exprs.size() )
            {
                visit( exprs.get( i ) );
            }
            else
            {
                loadConstant( NIL );
            }
        }
        
        for ( int i = targets.size() - 1; i >= 0; i-- )
        {
            visit( targets.get( i ) );
        }
        
        // TODO
        // For now only expressions that have matching targets are evaluated.
        // Might want to change this since Lua does evaluate leftovers.
        
        return null;
    }
    
    @Override
    public Void visitSimpleAssignmentTarget( SimpleAssignmentTargetContext ctx )
    {
        String target = ctx.getText();
        store( target );
        
        return null;
    }
    
    // ========================================
    
    @Override
    public Void visitNameAccessExpr( NameAccessExprContext ctx )
    {
        String var = ctx.Name().getText();
        load( var );
        
        // TODO Handle Tables
        
        return null;
    }
    
    // ========================================
    
    @Override
    public Void visitPrefixOpExpr( PrefixOpExprContext ctx )
    {
        visit( ctx.Right );
        prefixOperation( ctx.Operator.getText() );
        
        return null;
    }
    
    @Override
    public Void visitBinaryOpExpr( BinaryOpExprContext ctx )
    {
        visit( ctx.Left );
        visit( ctx.Right );
        binaryOperation( ctx.Operator.getText() );
        
        return null;
    }
    
    @Override
    public Void visitLogicalOpExpr( LogicalOpExprContext ctx )
    {
        visit( ctx.Left );
        visit( ctx.Right );
        logicOperation( ctx.Operator.getText() );
        
        return null;
    }
    
    @Override
    public Void visitConditionalBlockExpr( ConditionalBlockExprContext ctx )
    {
        visit( ctx.Left );
        beginConditionalBlock( ctx.Operator.getText() );
        visit( ctx.Right );
        endConditionalBlock();
        
        return null;
    }
    
    // ========================================
    
    @Override
    public Void visitLiteralNull( LiteralNullContext ctx )
    {
        loadConstant( NIL );
        
        return null;
    }
    
    @Override
    public Void visitLiteralBoolean( LiteralBooleanContext ctx )
    {
        boolean b = Boolean.parseBoolean( ctx.getText() );
        loadConstant( valueOf( b ) );
        
        return null;
    }
    
    @Override
    public Void visitLiteralNumber( LiteralNumberContext ctx )
    {
        double n = Double.parseDouble( ctx.getText() );
        loadConstant( valueOf( n ) );
        
        return null;
    }
    
    @Override
    public Void visitLiteralString( LiteralStringContext ctx )
    {
        String s = ctx.getText();
        loadConstant( valueOf( s ) );
        
        return null;
    }
}
