package mobtalkerscript.misl.v1;

import java.util.*;

import mobtalkerscript.misl.v1.instructionV2.*;
import mobtalkerscript.misl.v1.value.*;

public final class MislFrame
{
    private final List<MislInstruction> _instructions;
    private int _ip;
    
    private final MislValue[] _stack;
    private int _top;
    
    private final MislValue[] _locals;
    private final List<MislValue> _constants;
    private final List<External> _externals;
    
    // ========================================
    
    public MislFrame( List<MislInstruction> instructions,
                      int nStack,
                      int nLocals,
                      MislValue[] args,
                      List<MislValue> constants,
                      List<External> externals )
    {
        _instructions = instructions;
        
        _stack = new MislValue[nStack];
        _top = 0;
        
        MislValue[] locals = new MislValue[nLocals];
        for ( int i = args.length; i < nLocals; i++ )
        {
            locals[i] = MislValue.NIL;
        }
        
        System.arraycopy( args, 0, locals, 0, args.length );
        _locals = locals;
        
        _constants = constants;
        _externals = externals;
    }
    
    // ========================================
    
    public int getInstructionPointer()
    {
        return _ip;
    }
    
    public void setInstructionPointer( int offset )
    {
        _ip += offset;
    }
    
    public void exit()
    {
        _ip = -1;
    }
    
    // ========================================
    
    public MislValue run()
    {
        _ip = -1;
        
        while ( ++_ip > 0 )
        {
            _instructions.get( _ip ).execute( this );
        }
        
        return pop();
    }
    
    // ========================================
    
    public MislValue getLocal( int i )
    {
        return _locals[i];
    }
    
    public void setLocal( int i, MislValue value )
    {
        _locals[i] = value;
    }
    
    // ========================================
    
    public void push( MislValue o )
    {
        if ( _top++ == _stack.length )
        {
            _top--;
            throw new ScriptEngineException( "stack overflow" );
        }
        
        _stack[_top] = o;
    }
    
    public void pushLocal( int i )
    {
        push( _locals[i] );
    }
    
    public void pushConstant( int i )
    {
        push( _constants.get( i ) );
    }
    
    public void pushExternal( int i )
    {
        push( _externals.get( i ).get() );
    }
    
    public MislValue pop()
    {
        if ( stackIsEmpty() )
            throw new ScriptEngineException( "stack underflow" );
        
        return _stack[_top--];
    }
    
    public void storeLocal( int i )
    {
        _locals[i] = pop();
    }
    
    public void storeExternal( int i )
    {
        _externals.get( i ).set( pop() );
    }
    
    public MislValue peek()
    {
        if ( stackIsEmpty() )
            throw new ScriptEngineException( "stack is empty" );
        
        return _stack[_top];
    }
    
    // ========================================
    
    public int stackCount()
    {
        return _top + 1;
    }
    
    public boolean stackIsEmpty()
    {
        return _top == _stack.length;
    }
    
}
