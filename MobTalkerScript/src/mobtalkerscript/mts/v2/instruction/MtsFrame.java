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
    
    private final FrameValue[] _locals;
    private final FrameValue[] _externals;
    
    private int _lastLocal;
    private int _lastExternal;
    
    // ========================================
    
    public MtsFrame( MtsClosure closure, MtsValue[] args, FrameValue[] externals )
    {
        _closure = closure;
        _ip = 0;
        
        int nStack = closure.getPrototype().getMaxStackSize();
        int nLocals = closure.getPrototype().getLocalCount();
        
        _stack = new MtsValue[nStack];
        _top = 0;
        
        FrameValue[] locals = new FrameValue[nLocals];
        for ( int i = 0; i < args.length; i++ )
        {
            locals[i] = new FrameValue( args[i] );
        }
        for ( int i = args.length; i < nLocals; i++ )
        {
            locals[i] = new FrameValue();
        }
        _locals = locals;
        
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
    
    public void setInstructionPointer( int target )
    {
        _ip = target;
    }
    
    public int getLastLocal()
    {
        return _lastLocal;
    }
    
    public int getLastExternal()
    {
        return _lastExternal;
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
            MtsInstruction instr = instructions.get( _ip );
            
            System.out.println( "Executing " + instr.toString( this.getClosure().getPrototype() ) );
            
            instr.execute( this );
            
            if ( instr.exits() )
                break;
            
            _ip++;
        }
        
        return pop();
    }
    
    // ========================================
    
    public MtsValue getConstant( int i )
    {
        return _closure.getPrototype().getConstants().get( i );
    }
    
    public FrameValue getLocal( int i )
    {
        _lastLocal = i;
        return _locals[i];
    }
    
    public FrameValue getExternal( int i )
    {
        _lastExternal = i;
        return _externals[i];
    }
    
    // ========================================
    
    public void push( MtsValue o )
    {
        if ( _top == _stack.length )
            throw new ScriptEngineException( "stack overflow" );
        
        _stack[_top++] = o;
    }
    
    public MtsValue pop()
    {
        if ( stackIsEmpty() )
            throw new ScriptEngineException( "stack underflow" );
        
        return _stack[--_top];
    }
    
    public List<MtsValue> pop( int count )
    {
        if ( count == 0 )
            return Collections.emptyList();
        
        if ( count > stackCount() )
            throw new ScriptEngineException( "stack underflow" );
        
        MtsValue[] values = new MtsValue[count];
        System.arraycopy( _stack, ( _top - count ), values, 0, count );
        
        _top -= count;
        
        return Arrays.asList( values );
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
    
    public void pushConstant( int i )
    {
        push( getConstant( i ) );
    }
    
    public void pushLocal( int i )
    {
        push( getLocal( i ).get() );
    }
    
    public void pushExternal( int i )
    {
        push( getExternal( i ).get() );
    }
    
    public void storeLocal( int i )
    {
        getLocal( i ).set( pop() );
    }
    
    public void storeExternal( int i )
    {
        getExternal( i ).set( pop() );
    }
    
    // ========================================
    
    public int stackCount()
    {
        return _top;
    }
    
    public boolean stackIsEmpty()
    {
        return _top <= 0;
    }
    
}
