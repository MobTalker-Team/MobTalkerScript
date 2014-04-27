package mobtalkerscript.mts.v2.instruction;

import java.util.*;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;
import mobtalkerscript.util.logging.*;

import com.google.common.base.*;
import com.google.common.collect.*;

public final class MtsFrame
{
    private static boolean DEBUG = true;
    
    public static void enableDebug( boolean flag )
    {
        DEBUG = flag;
    }
    
    // ========================================
    
    private final MtsClosure _closure;
    private int _ip;
    
    private final MtsValue[] _stack;
    private int _top;
    
    private final List<FrameValue> _locals;
    private final List<FrameValue> _externals;
    
    private String _lastVarOrConst;
    
    // ========================================
    
    public MtsFrame( MtsClosure closure, MtsVarArgs arguments, List<FrameValue> externals )
    {
        _closure = closure;
        _ip = 0;
        
        int nStack = closure.getPrototype().getMaxStackSize();
        int nLocals = closure.getPrototype().getLocalCount();
        int nArgs = arguments.count();
        
        _stack = new MtsValue[nStack];
        _top = 0;
        
        ArrayList<FrameValue> locals = Lists.newArrayListWithCapacity( nLocals );
        int i = 0;
        for ( ; i < nArgs; i++ )
            locals.add( new FrameValue( arguments.get( i ) ) );
        for ( ; i < nLocals; i++ )
            locals.add( new FrameValue() );
        
        _locals = locals;
        _externals = externals;
        
        _lastVarOrConst = "?";
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
    
    public String getLastVariableOrConstant()
    {
        return _lastVarOrConst;
    }
    
    // ========================================
    
    /**
     * Executes the instructions given until an instruction signals a frame exit and returns the top of the stack.
     */
    public MtsValue run()
    {
        List<MtsInstruction> instructions = _closure.getPrototype().getInstructions();
        
        if ( MTSLog.isFinestEnabled() )
        {
            MTSLog.finest( "Stack: " + formatStack() );
        }
        
        for ( ;; )
        {
            MtsInstruction instr = instructions.get( _ip );
            
            if ( MTSLog.isFinerEnabled() )
            {
                MTSLog.finer( "Executing [%s] %s",
                              formatInstructionPointer( instructions.size() ),
                              instr.toString( getClosure().getPrototype() ) );
            }
            
            instr.execute( this );
            
            if ( MTSLog.isFinestEnabled() )
            {
                MTSLog.finest( "Stack: " + formatStack() );
            }
            
            if ( instr.exits() )
                break;
            
            _ip++;
        }
        
        return pop();
    }
    
    private String formatStack()
    {
        if ( _top == 0 )
            return "[]";
        
        StringBuilder s = new StringBuilder( "[" );
        int i = 0;
        for ( ; i < ( _top - 1 ); i++ )
        {
            s.append( _stack[i] ).append( ", " );
        }
        s.append( _stack[i] ).append( ']' );
        
        return s.toString();
    }
    
    private String formatInstructionPointer( int count )
    {
        int l = Integer.toString( count ).length();
        return Strings.padStart( Integer.toString( _ip ), l, '0' );
    }
    
    // ========================================
    
    public MtsValue getConstant( int i )
    {
        MtsValue result = _closure.getPrototype().getConstant( i );
        
        if ( DEBUG )
            _lastVarOrConst = result.isString() ? result.toString() : "?";
        else
            _lastVarOrConst = "?";
        
        return result;
    }
    
    public FrameValue getLocal( int i )
    {
        if ( DEBUG )
            _lastVarOrConst = _closure.getPrototype().getLocalDescription( i ).getName();
        else
            _lastVarOrConst = "?";
        
        return _locals.get( i );
    }
    
    public FrameValue getExternal( int i )
    {
        if ( DEBUG )
            _lastVarOrConst = _closure.getPrototype().getExternalDescription( i ).getName();
        else
            _lastVarOrConst = "?";
        
        return _externals.get( i );
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
        
        return _stack[_top - 1];
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
    
    // ========================================
    
    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder( "Frame" );
        
        s.append( " [IP: " ).append( _ip );
        s.append( ", Last used Variable or Constant: " ).append( _lastVarOrConst );
        s.append( "]\n" );
        
        s.append( " Locals    " ).append( _locals.toString() ).append( "\n" );
        s.append( " Externals " ).append( _externals.toString() ).append( "\n" );
        s.append( " Stack     " ).append( formatStack() );
        
        return s.toString();
    }
    
}
