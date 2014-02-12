package mobtalkerscript.mts.v2.compiler;

import static mobtalkerscript.mts.v2.value.MtsValue.*;
import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.instruction.*;
import mobtalkerscript.mts.v2.value.*;

public class MtsCompiler
{
    public static final String G = "_G";
    public static final String ENV = "_ENV";
    
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
    
    protected void addInstr( MtsInstruction instr, int stackChange )
    {
        _currentFunction.addInstruction( instr, _curLine, _curColoum, stackChange );
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
            addInstr( new InstrLoadL( index ), 1 );
        }
        else if ( _currentFunction.isExternal( name ) )
        { // External
            int index = _currentFunction.getExternalIndex( name );
            addInstr( new InstrLoadE( index ), 1 );
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
            addInstr( new InstrLoadE( index ), 1 );
        }
        else if ( _currentFunction.isLocal( ENV ) )
        {
            int index = _currentFunction.getLocalIndex( ENV );
            addInstr( new InstrLoadL( index ), 1 );
        }
        else
        {
            throw new IllegalStateException( ENV + " is neither a local nor an external!" );
        }
    }
    
    public void loadConstant( MtsValue value )
    {
        int index = _currentFunction.getConstantIndex( value );
        addInstr( new InstrLoadC( index ), 1 );
    }
    
    public void store( String name )
    {
        if ( _currentFunction.isLocal( name ) )
        { // Local
            int index = _currentFunction.getLocalIndex( name );
            addInstr( new InstrStoreL( index ), -1 );
        }
        else if ( _currentFunction.isExternal( name ) )
        { // External
            int index = _currentFunction.getExternalIndex( name );
            addInstr( new InstrStoreE( index ), -1 );
        }
        else
        { // Global
            loadEnvironment();
            loadConstant( valueOf( name ) );
            storeInTable();
        }
    }
    
    // ========================================
    
    public void loadFromTable()
    {
        addInstr( new InstrLoadT(), -1 );
    }
    
    public void storeInTable()
    {
        addInstr( new InstrStoreT(), -3 );
    }
    
    // ========================================
    
//    public abstract void prefixOperation( String op );
    
//    public abstract void postfixOperation( String op );
    
//    public abstract void binaryOperation( String op );
    
//    public abstract void logicOperation( String op );
    
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
