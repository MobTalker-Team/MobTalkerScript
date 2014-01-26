package mobtalkerscript.mts.v2.instruction;

import java.util.*;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;

public final class MtsFrame
{
    private final MtsClosure _closure;
    private int _ip;
    
    private final MtsValue[] _stack;
    private int _top;
    
    private final MtsValue[] _locals;
    private final List<MtsValue> _constants;
    private final List<External> _externals;
    
    // ========================================
    
    public MtsFrame( MtsClosure closure, MtsValue[] args, List<External> externals )
    {
        _closure = closure;
        _ip = 0;
        
        int nStack = closure.getPrototype().getMaxStackSize();
        int nLocals = closure.getPrototype().getLocalCount();
        
        _stack = new MtsValue[nStack];
        _top = -1;
        
        MtsValue[] locals = new MtsValue[nLocals];
        for ( int i = args.length; i < nLocals; i++ )
        {
            locals[i] = MtsValue.NIL;
        }
        
        System.arraycopy( args, 0, locals, 0, args.length );
        _locals = locals;
        
        _constants = closure.getPrototype().getConstants();
        _externals = externals;
    }
    
    // ========================================
    
    public MtsClosure getClosure()
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
    
    // ========================================
    
    /**
     * Executes the instructions given until an instruction signals a frame exit and returns the top of the stack.
     */
    public MtsValue run()
    {
        List<MtsInstruction> instructions = _closure.getPrototype().getInstructions();
        
        for ( ;; )
        {
            MtsInstruction instr = instructions.get( _ip++ );
            
            System.out.println( "Executing " + instr.toString() );
            
            instr.execute( this );
            
            if ( instr.exits() )
                break;
        }
        
        return pop();
    }
    
    // ========================================
    
    public MtsValue getLocal( int i )
    {
        return _locals[i];
    }
    
    public void setLocal( int i, MtsValue value )
    {
        _locals[i] = value;
    }
    
    // ========================================
    
    public External getExternal( int i )
    {
        return _externals.get( i );
    }
    
    // ========================================
    
    public void push( MtsValue o )
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
    
    public MtsValue pop()
    {
        if ( stackIsEmpty() )
            throw new ScriptEngineException( "stack underflow" );
        
        return _stack[_top--];
    }
    
    public MtsValue[] pop( int count )
    {
        if ( count == 0 )
            return new MtsValue[0];
        
        if ( count > stackCount() )
            throw new ScriptEngineException( "stack underflow" );
        
        MtsValue[] values = new MtsValue[count];
        System.arraycopy( _stack, ( _top - count ) + 1, values, 0, count );
        
        return values;
    }
    
    public void storeLocal( int i )
    {
        _locals[i] = pop();
    }
    
    public void storeExternal( int i )
    {
        _externals.get( i ).set( pop() );
    }
    
    public MtsValue peek()
    {
        if ( stackIsEmpty() )
            throw new ScriptEngineException( "stack is empty" );
        
        return _stack[_top];
    }
    
    public void duplicateTop()
    {
        if ( _top++ == _stack.length )
        {
            _top--;
            throw new ScriptEngineException( "stack overflow" );
        }
        
        _stack[_top] = _stack[_top - 1];
    }
    
    // ========================================
    
    public int stackCount()
    {
        return _top + 1;
    }
    
    public boolean stackIsEmpty()
    {
        return _top < 0;
    }
    
}
