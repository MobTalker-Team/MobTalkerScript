package mobtalkerscript.misl.v1;

import java.util.*;

import mobtalkerscript.misl.v1.instructionV2.*;
import mobtalkerscript.misl.v1.value.*;

public final class MislFrame
{
    private final MislClosure _closure;
    private int _ip;
    
    private final MislValue[] _stack;
    private int _top;
    
    private final MislValue[] _locals;
    private final List<MislValue> _constants;
    private final List<External> _externals;
    
    // ========================================
    
    public MislFrame( MislClosure closure, MislValue[] args, List<External> externals )
    {
        _closure = closure;
        
        int nStack = closure.getPrototype().getMaxStackSize();
        int nLocals = closure.getPrototype().getLocalCount();
        
        _stack = new MislValue[nStack];
        _top = 0;
        
        MislValue[] locals = new MislValue[nLocals];
        for ( int i = args.length; i < nLocals; i++ )
        {
            locals[i] = MislValue.NIL;
        }
        
        System.arraycopy( args, 0, locals, 0, args.length );
        _locals = locals;
        
        _constants = closure.getPrototype().getConstants();
        _externals = externals;
    }
    
    // ========================================
    
    public MislClosure getClosure()
    {
        return _closure;
    }
    
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
        List<MislInstruction> instructions = _closure.getPrototype().getInstructions();
        _ip = -1;
        
        while ( ++_ip > 0 )
        {
            instructions.get( _ip ).execute( this );
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
    
    public External getExternal( int i )
    {
        return _externals.get( i );
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
