package mobtalkerscript.mts.v2.compiler;

import static com.google.common.base.Preconditions.*;
import static mobtalkerscript.mts.v2.compiler.CompilerConstants.*;
import static mobtalkerscript.mts.v2.instruction.InstructionCache.*;
import static mobtalkerscript.mts.v2.value.MtsValue.*;

import java.util.*;
import java.util.regex.*;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.instruction.*;
import mobtalkerscript.mts.v2.value.*;

public class MtsCompiler
{
    
    private final FunctionState _mainFunction;
    private FunctionState _currentFunction;
    
    private final String _sourceFile;
    private SourcePosition _curPosition;
    
    // ========================================
    
    {
        _curPosition = new SourcePosition( 0, 0 );
    }
    
    public MtsCompiler( String sourceName, int sourceLineStart, int sourceLineEnd )
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
        System.out.println( "  Instruction: " + instr );
        _currentFunction.addInstruction( instr, _curPosition );
    }
    
    public void discardValue()
    {
        addInstr( InstrPop() );
    }
    
    // ========================================
    
    public void enterFunction( String name, int sourceLineStart, int sourceLineEnd, String... params )
    {
        System.out.println( "Enter Function " );
        
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
        System.out.println( "Exit Function " );
        
        addInstr( InstrReturn( 0 ) );
        _currentFunction = _currentFunction.getParent();
    }
    
    // ========================================
    
    public void enterBlock()
    {
        System.out.println( "Enter Block" );
        
        _currentFunction.enterBlock();
    }
    
    public void exitBlock()
    {
        System.out.println( "Exit Block" );
        
        _currentFunction.exitBlock();
    }
    
    // ========================================
    
    public void enterWhileLoop()
    {
        System.out.println( "Enter WhileLoop" );
        
        _currentFunction.enterLoop();
    }
    
    public void enterWhileBody()
    {
        System.out.println( "Enter WhileBody" );
        
        addInstr( InstrTest() );
        _currentFunction.markBreak();
        _currentFunction.enterBlock();
    }
    
    public void exitWhileLoop()
    {
        System.out.println( "Exit WhileLoop" );
        
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
    
    public void enterNumericForLoop( String varName )
    {
        _currentFunction.enterBlock();
        _currentFunction.enterNumericForLoop( varName );
    }
    
    public void enterGenericForLoop( String... vars )
    {
        _currentFunction.enterBlock();
        _currentFunction.enterGenericForLoop( vars );
    }
    
    public void exitForLoop()
    {
        addInstr( InstrJump() );
        _currentFunction.exitLoop();
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
    
    public LocalDescription declareLocal( String name )
    {
        System.out.println( "Declare local: " + name );
        
        return _currentFunction.declareLocal( name );
    }
    
    public LocalDescription declareAnonymousLocal( String name )
    {
        System.out.println( "Declare internal: " + name );
        
        return _currentFunction.declareAnonymousLocal( name );
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
        System.out.println( "Load Variable: " + name );
        
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
    
    public void loadLocal( int index )
    {
        addInstr( InstrLoadL( index ) );
    }
    
    public void loadConstant( MtsValue value )
    {
        checkNotNull( value != null, "value cannot be null" );
        checkArgument( !value.isNil(), "value cannot be nil" );
        
        System.out.println( "Load constant: " + value );
        
        int index = _currentFunction.getConstantIndex( value );
        addInstr( InstrLoadC( index ) );
    }
    
    public void loadConstant( String s )
    {
        loadConstant( parseString( s ) );
    }
    
    public void loadNil()
    {
        System.out.println( "Load nil" );
        
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
        System.out.println( "Store Variable: " + name );
        
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
    
    public void storeLocal( int index )
    {
        addInstr( InstrStoreL( index ) );
    }
    
    // ========================================
    
    public void createTable( int listElements, int hashPairs )
    {
        System.out.println( "Create Table" );
        
        addInstr( InstrNewTable( listElements, hashPairs ) );
    }
    
    public void loadFromTable()
    {
        System.out.println( "Load from Table" );
        
        addInstr( InstrLoadT() );
    }
    
    public void storeInTable()
    {
        System.out.println( "Store in Table" );
        
        addInstr( InstrStoreT() );
    }
    
    public void loadMethod( String name )
    {
        System.out.println( "Load Method: " + name );
        
        addInstr( InstrDup() );
        loadConstant( valueOf( name ) );
        loadFromTable();
    }
    
    // ========================================
    
    public void assignmentOperation( String op )
    {
        System.out.println( "Operator: " + op );
        
        throw new UnsupportedOperationException();
    }
    
    public void unaryOperation( String op )
    {
        System.out.println( "Operator: " + op );
        
        addInstr( InstrUnaryOp( op ) );
    }
    
    public void binaryOperation( String op )
    {
        System.out.println( "Operator: " + op );
        
        addInstr( InstrBinaryOp( op ) );
    }
    
    public void logicOperation( String op )
    {
        System.out.println( "Operator: " + op );
        
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
        System.out.println( "Operator: " + op );
        
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
     * <p>
     * CLOSURE index
     */
    public void createClosure()
    {
        System.out.println( "Create Closure" );
        
        List<FunctionState> childs = _currentFunction.getChilds();
        int index = childs.size() - 1;
        
        addInstr( new InstrClosure( index ) );
    }
    
    /**
     * CALL nArgs nReturn
     */
    public void callFunction( int nArgs, int nReturn )
    {
        System.out.println( "Call Function" );
        
        addInstr( new InstrCallFunc( nArgs, nReturn ) );
    }
    
    public void returnFunction( int nValues )
    {
        System.out.println( "Return Function" );
        
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
    
    public static MtsString parseString( String s )
    {
        return MtsValue.valueOf( stripHyphen( s ) );
    }
    
    public static MtsBoolean parseBoolean( String s )
    {
        return MtsValue.valueOf( Boolean.parseBoolean( s ) );
    }
    
    public static MtsNumber parseNumber( String s )
    {
        return MtsValue.valueOf( Double.parseDouble( s ) );
    }
    
    // ========================================
    
    private static final Pattern _interpolationPattern = Pattern.compile( "(?<!\\\\)\\{([_a-zA-Z][_a-zA-Z0-9]*)\\}" );
    
    public void interpolateString( String s )
    {
        s = stripHyphen( s );
        Matcher matcher = _interpolationPattern.matcher( s );
        
        int start = 0;
        int end = 0;
        if ( matcher.find() )
        {
            int count = 0;
            do
            {
                end = matcher.start( 0 );
                
                if ( ( end - start ) > 0 )
                {
                    loadConstant( s.substring( start, end ) );
                    count++;
                }
                
                String var = matcher.group( 1 );
                loadVariable( var );
                count++;
                
                start = matcher.end( 0 );
            }
            while ( matcher.find() );
            
            if ( ( s.length() - start ) > 0 )
            {
                loadConstant( s.substring( start ) );
                count++;
            }
            
            addInstr( InstrConcat( count ) );
        }
        else
        {
            loadConstant( s );
        }
    }
    
    // ========================================
    
    public MtsFunctionPrototype compile()
    {
        if ( _currentFunction != _mainFunction )
            throw new IllegalStateException();
        
        return _mainFunction.createPrototype();
    }
}
