package mobtalkerscript.mts.v2.compiler;

import static com.google.common.base.Preconditions.*;
import static mobtalkerscript.mts.v2.compiler.CompilerConstants.*;
import static mobtalkerscript.mts.v2.instruction.InstructionCache.*;
import static mobtalkerscript.mts.v2.value.MtsValue.*;

import java.util.*;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.compiler.antlr.*;
import mobtalkerscript.mts.v2.instruction.*;
import mobtalkerscript.mts.v2.value.*;

public abstract class MtsCompilerBase extends MtsBaseVisitor<Void> implements IMtsCompiler
{
    private final FunctionState _mainFunction;
    private FunctionState _currentFunction;
    
    private final String _sourceFile;
    private SourcePosition _curPosition;
    
    // ========================================
    
    {
        _curPosition = new SourcePosition( 0, 0 );
    }
    
    public MtsCompilerBase( String sourceName, int sourceLineStart, int sourceLineEnd )
    {
        _mainFunction = new FunctionState( null, "main", sourceName, sourceLineStart, sourceLineEnd );
        _mainFunction.addExternal( new ExternalDescription( ENV, 0, 0, true ) );
        
        _currentFunction = _mainFunction;
        
        _sourceFile = sourceName;
    }
    
    // ========================================
    
    public void setSourcePosition( int line, int coloum )
    {
        if ( _curPosition.equals( line, coloum ) )
            return;
        
        _curPosition = new SourcePosition( line, coloum );
    }
    
    public SourcePosition getSourcePosition()
    {
        return _curPosition;
    }
    
    // ========================================
    
    public void addInstr( MtsInstruction instr )
    {
        _currentFunction.addInstruction( instr, _curPosition );
    }
    
    // ========================================
    
    public void enterFunction( String name, int sourceLineStart, int sourceLineEnd, String... params )
    {
        enterFunction( name, sourceLineStart, sourceLineEnd, params );
    }
    
    public void enterFunction( String name, int sourceLineStart, int sourceLineEnd, Iterable<String> params )
    {
        FunctionState child = new FunctionState( _currentFunction,
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
    
    public void exitFunction()
    {
        addInstr( InstrReturn( 0 ) );
        _currentFunction = _currentFunction.getParent();
    }
    
    // ========================================
    
    public void enterBlock()
    {
        _currentFunction.enterBlock();
    }
    
    public void leaveBlock()
    {
        _currentFunction.exitBlock();
    }
    
    // ========================================
    
    public void enterWhileLoop()
    {
        _currentFunction.enterLoop();
    }
    
    public void enterWhileBody()
    {
        addInstr( InstrTest() );
        _currentFunction.markBreak();
        _currentFunction.enterBlock();
    }
    
    public void exitWhileLoop()
    {
        addInstr( InstrJump() );
        _currentFunction.exitLoop();
        _currentFunction.exitBlock();
    }
    
    // ========================================
    
    public void enterRepeatLoop()
    {
        _currentFunction.enterLoop();
        _currentFunction.enterBlock();
    }
    
    public void enterUntilConditon()
    {
        _currentFunction.exitBlock();
    }
    
    public void exitRepeatLoop()
    {
        addInstr( InstrTest() );
        _currentFunction.exitLoop();
    }
    
    // ========================================
    
    public void enterForLoop()
    {
        _currentFunction.enterLoop();
        _currentFunction.enterBlock();
    }
    
    public void enterForBody()
    {
        addInstr( InstrTest() );
        _currentFunction.markBreak();
        _currentFunction.enterBlock();
    }
    
    public void exitForLoop()
    {
        addInstr( InstrJump() );
        _currentFunction.exitLoop();
        _currentFunction.exitBlock();
        _currentFunction.exitBlock();
    }
    
    // ========================================
    
    public void breakLoop()
    {
        addInstr( InstrJump() );
        _currentFunction.markBreak();
    }
    
    // ========================================
    
    public void enterIfThenElseBlock()
    {
        _currentFunction.enterIfThenElse();
    }
    
    public void enterIfCondition()
    {
        _currentFunction.enterIfCondition();
    }
    
    public void endIfCondition()
    {
        _currentFunction.endIfCondition();
    }
    
    public void endThenBlock()
    {
        _currentFunction.endThenBlock();
    }
    
    public void enterElseBlock()
    {
        _currentFunction.enterElseBlock();
    }
    
    public void exitIfThenElse()
    {
        _currentFunction.exitIfThenElse();
    }
    
    // ========================================
    
    public void declareLabel( String name )
    {
        _currentFunction.addLabel( name );
    }
    
    public void gotoLabel( String name )
    {
        _currentFunction.gotoLabel( name );
    }
    
//    public abstract void gotoFunction( String name );
    
    // ========================================
    
    public void declareLocal( String name )
    {
        _currentFunction.declareLocal( name );
    }
    
    private void loadEnvironment()
    {
        if ( _currentFunction.isLocal( ENV ) )
        { // Local environment
            int index = _currentFunction.getLocalIndex( ENV );
            addInstr( InstrLoadL( index ) );
        }
        else
        { // Parent environment
            int index = _currentFunction.getExternalIndex( ENV );
            addInstr( InstrLoadE( index ) );
        }
    }
    
    public void loadVariable( String name )
    {
        if ( _currentFunction.isLocal( name ) )
        { // Local
            int index = _currentFunction.getLocalIndex( name );
            addInstr( InstrLoadL( index ) );
        }
        else if ( _currentFunction.isExternal( name ) )
        { // External
            int index = _currentFunction.getExternalIndex( name );
            addInstr( InstrLoadE( index ) );
        }
        else
        { // Global
            loadEnvironment();
            
            int constant = _currentFunction.getConstantIndex( valueOf( name ) );
            addInstr( InstrLoadC( constant ) );
            addInstr( InstrLoadT() );
        }
    }
    
    public void loadConstant( MtsValue value )
    {
        checkNotNull( value != null, "value cannot be null" );
        checkArgument( !value.isNil(), "value cannot be nil" );
        
        int index = _currentFunction.getConstantIndex( value );
        addInstr( InstrLoadC( index ) );
    }
    
    public void loadConstant( String s )
    {
        loadConstant( valueOf( s ) );
    }
    
    public void loadNil()
    {
        addInstr( InstrLoadNil() );
    }
    
    public void loadNil( int count )
    {
        for ( int i = 0; i < count; i++ )
        {
            loadNil();
        }
    }
    
    public void storeVariable( String name )
    {
        if ( _currentFunction.isLocal( name ) )
        { // Local
            int index = _currentFunction.getLocalIndex( name );
            addInstr( InstrStoreL( index ) );
        }
        else if ( _currentFunction.isExternal( name ) )
        { // External
            int index = _currentFunction.getExternalIndex( name );
            addInstr( InstrStoreE( index ) );
        }
        else
        { // Global
            loadEnvironment();
            
            int constant = _currentFunction.getConstantIndex( valueOf( name ) );
            addInstr( InstrLoadC( constant ) );
            addInstr( InstrStoreT() );
        }
    }
    
    // ========================================
    
    public void createTable( int listElements, int hashPairs )
    {
        addInstr( InstrNewTable( listElements, hashPairs ) );
    }
    
    public void loadFromTable()
    {
        addInstr( InstrLoadT() );
    }
    
    public void storeInTable()
    {
        addInstr( InstrStoreT() );
    }
    
    // ========================================
    
    public void assignmentOperation( String op )
    {
        throw new UnsupportedOperationException();
    }
    
    public void unaryOperation( String op )
    {
        addInstr( InstrUnaryOp( op ) );
    }
    
    public void binaryOperation( String op )
    {
        addInstr( InstrBinaryOp( op ) );
    }
    
    public void logicOperation( String op )
    {
        if ( ">".equals( op ) )
        {
            addInstr( InstrLte() );
            addInstr( InstrNot() );
        }
        else if ( ">=".equals( op ) )
        {
            addInstr( InstrLt() );
            addInstr( InstrNot() );
        }
        else if ( "!=".equals( op ) )
        {
            addInstr( InstrEq() );
            addInstr( InstrNot() );
        }
        else
        {
            addInstr( InstrLogicalOp( op ) );
        }
    }
    
    // ========================================
    
    /**
     * Mark the beginning of the second expression of an <code>and</code> or <code>or</code> operator.
     */
    public void enterConditionalBlock( String op )
    {
        if ( "and".equals( op ) )
        {
            addInstr( InstrAnd() );
            _currentFunction.markPendingJump();
        }
        else if ( "or".equals( op ) )
        {
            addInstr( InstrOr() );
            _currentFunction.markPendingJump();
        }
        else
        {
            throw new IllegalArgumentException( op + " is not a valid conditional operator" );
        }
    }
    
    /**
     * Signal the completion of the second expression of an <code>and</code> or <code>or</code> operator.
     */
    public void exitConditionalBlock()
    {
        _currentFunction.setPendingJump();
    }
    
    // ========================================
    
    /**
     * Creates a closure off the latest child of the current function (the function compiled last).
     * 
     * CLOSURE index
     */
    public void createClosure()
    {
        List<FunctionState> childs = _currentFunction.getChilds();
        int index = childs.size() - 1;
        
        addInstr( new InstrClosure( index ) );
    }
    
    /**
     * CALL nArgs nReturn
     */
    public void callFunction( int nArgs, int nReturn )
    {
        addInstr( new InstrCallFunc( nArgs, nReturn ) );
    }
    
    public void returnFunction( int nValues )
    {
        addInstr( InstrReturn( nValues ) );
    }
    
    // ========================================
    
    public static String stripHyphen( String s )
    {
        if ( ( s.charAt( 0 ) == '"' ) && ( s.charAt( s.length() - 1 ) == '"' ) )
        {
            return s.substring( 1, s.length() - 1 );
        }
        else
        {
            return s;
        }
    }
    
    public MtsString parseString( String s )
    {
        return MtsValue.valueOf( stripHyphen( s ) );
    }
    
    public MtsBoolean parseBoolean( String s )
    {
        return MtsValue.valueOf( Boolean.parseBoolean( s ) );
    }
    
    public MtsNumber parseNumber( String s )
    {
        return MtsValue.valueOf( Double.parseDouble( s ) );
    }
    
    // ========================================
    
    public MtsFunctionPrototype compile()
    {
        if ( _currentFunction != _mainFunction )
            throw new IllegalStateException();
        
        return _mainFunction.createPrototype();
    }
}
