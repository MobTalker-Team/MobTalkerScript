package mobtalkerscript.misl.v1.instruction;

import java.util.*;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.Stack;
import mobtalkerscript.util.logging.*;

public class InstrCall extends MislInstruction
{
    
    private final int _argCount;
    private final int _retCount;
    
    private MislInstruction _jumpPointer;
    
    // ========================================
    
    public InstrCall( int argCount, int retCount )
    {
        _argCount = argCount;
        _retCount = retCount;
    }
    
    // ========================================
    
    @Override
    public MislInstruction getNext()
    {
        return _jumpPointer;
    }
    
    // ========================================
    
    @Override
    public void execute( Stack<MislFrame> frameStack, ScriptContext context )
    {
        MislFrame curFrame = frameStack.peek();
        Stack<MislValue> curStack = curFrame.getStack();
        
        MislValue top = curStack.pop();
        
        doCall( top, frameStack, context );
    }
    
    protected void doCall( MislValue value, Stack<MislFrame> frameStack, ScriptContext context )
    {
        MislFrame curFrame = frameStack.peek();
        Stack<MislValue> curStack = curFrame.getStack();
        
        if ( value.isNativeFunction() )
        {
            MislNativeFunction f = value.asNativeFunction();
            try
            {
                executeNativeFunction( f, curStack, context );
            }
            catch ( ScriptRuntimeException ex )
            {
                throw new ScriptRuntimeException( "%s (at %s:%s)",
                                                  ex.getMessage(),
                                                  curFrame.getSourceName(),
                                                  curFrame.getSourceLine() );
            }
        }
        else if ( value.isFunction() )
        {
            MislFunction f = value.asFunction();
            executeScriptedFunction( f, frameStack, context );
        }
        else
        {
            throw new ScriptRuntimeException( "expected function, got %s (at %s:%s)",
                                              value.getTypeName(),
                                              curFrame.getSourceName(),
                                              curFrame.getSourceLine() );
        }
    }
    
    private void executeScriptedFunction( MislFunction f, Stack<MislFrame> frameStack, ScriptContext context )
    {
        MislFrame oldFrame = frameStack.peek();
        Stack<MislValue> oldStack = oldFrame.getStack();
        
        MislFrame newFrame = new MislFrame( _next, _retCount );
        Stack<MislValue> newStack = newFrame.getStack();
        
        int paramCount = Math.max( f.getArgCount(), _argCount );
        for ( int i = 0; i < paramCount; i++ )
        {
            MislValue param = oldStack.isEmpty() ? MislValue.NIL : oldStack.pop();
            newStack.push( param );
        }
        
        MTSLog.finer( "[Engine] Call stack: %s", newStack.toString() );
        
        frameStack.push( newFrame );
        
        context.enterFunctionScope();
        
        _jumpPointer = f.getInstruction();
    }
    
    private void executeNativeFunction( MislNativeFunction f, Stack<MislValue> stack, ScriptContext context )
    {
        MislValue[] args = new MislValue[_argCount];
        
        for ( int i = _argCount - 1; i >= 0; i-- )
        {
            args[i] = stack.pop();
        }
        
        MTSLog.finer( "[Engine] Call stack: %s", Arrays.toString( args ) );
        
        IBindings env = context.getCurrentScope();
        
        MislValue result = f.call( env, args );
        
        if ( result == null )
        {
            for ( int i = 0; i < _retCount; i++ )
            {
                stack.push( MislValue.NIL );
            }
        }
        else if ( result.isArray() )
        {
            MislArray arr = result.asArray();
            
            for ( int i = 0; i < _retCount; i++ )
            {
                stack.push( arr.get( i ) );
            }
        }
        else
        {
            stack.push( result );
            
            for ( int i = 1; i < _retCount; i++ )
            {
                stack.push( MislValue.NIL );
            }
        }
        
        _jumpPointer = _next;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return String.format( "%1$-10s %2$s", "CALL", _argCount + " " + _retCount );
    }
}