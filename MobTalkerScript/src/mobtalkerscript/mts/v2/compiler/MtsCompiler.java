package mobtalkerscript.mts.v2.compiler;

import static mobtalkerscript.mts.v2.value.MtsValue.*;

import java.util.*;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.instruction.*;
import mobtalkerscript.mts.v2.value.*;

import com.google.common.collect.*;

public class MtsCompiler
{
    public static final String G = "_G";
    public static final String ENV = "_ENV";
    
    // ========================================
    // Cached Instructions
    
    private static final Map<String, MtsInstruction> _binaryOps;
    private static final Map<String, MtsInstruction> _logicalOps;
    
    static
    {
        _binaryOps = Maps.newHashMap();
        _binaryOps.put( "+", new InstrAdd() );
        _binaryOps.put( "-", new InstrSub() );
        _binaryOps.put( "*", new InstrMul() );
        _binaryOps.put( "/", new InstrDiv() );
        _binaryOps.put( "%", new InstrMod() );
        
        _logicalOps = Maps.newHashMap();
        _logicalOps.put( "==", new InstrEQ() );
        _logicalOps.put( "<", new InstrLT() );
        _logicalOps.put( "<=", new InstrLTE() );
    }
    
    private static final MtsInstruction _not = new InstrNot();
    
    private static final MtsInstruction _loadT = new InstrLoadT();
    private static final MtsInstruction _storeT = new InstrStoreT();
    
    // ========================================
    
    private final FunctionScope _mainFunction;
    private FunctionScope _currentFunction;
    
    private int _curLine;
    private int _curColoum;
    
    public MtsCompiler( String sourceName )
    {
        _mainFunction = new FunctionScope( null, "main", sourceName );
        _currentFunction = _mainFunction;
    }
    
    // ========================================
    
    public void addInstr( MtsInstruction instr )
    {
        _currentFunction.addInstruction( instr, _curLine, _curColoum );
    }
    
    public void markJumpOrigin()
    {
        _currentFunction.markJumpOrigin();
    }
    
    public void setJumpTarget()
    {
        _currentFunction.setJumpTarget();
    }
    
    public void setLineNumber( int line, int coloum )
    {
        _curLine = line;
        _curColoum = coloum;
    }
    
    // ========================================
    
//    public abstract void declareFunction( String name, String... params );
    
//    public abstract void leaveFunction();
    
    // ========================================
    
//    public abstract void enterBlock();
    
//    public abstract void leaveBlock();
    
//    public abstract void enterLoopBlock();
    
//    public abstract void breakLoop();
    
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
    
//    public abstract void prefixOperation( String op );
    
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
        else if ( "and".equals( op ) )
        {
            addInstr( new InstrAnd() );
            markJumpOrigin();
        }
        else if ( "or".equals( op ) )
        {
            addInstr( new InstrOr() );
            markJumpOrigin();
        }
        else
        {
            throw new IllegalArgumentException( op );
        }
    }
    
    // ========================================
    
//    public abstract void createClosure( int prototype );
    
//    public abstract void callFunction( int nArgs );
    
//    public abstract void callMethod( int nArgs );
    
//    public abstract void returnFunction( int nValues );
    
    // ========================================
    
    public MtsFunctionPrototype compile()
    {
        if ( _currentFunction != _mainFunction )
            throw new IllegalStateException();
        
        return _mainFunction.createPrototype();
    }
    
}
