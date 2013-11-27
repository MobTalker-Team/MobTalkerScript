package mobtalkerscript.misl.v1.instruction;

import java.util.*;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.Stack;
import mobtalkerscript.util.logging.*;

public class InstrCall extends MislInstruction
{
    
    private final int _argCount;
    private final int _returnCount;
    private final boolean _isTailCall;
    
    private MislInstruction _jumpPointer;
    
    // ========================================
    
    public InstrCall( int argCount, int retCount )
    {
        _argCount = argCount;
        _returnCount = retCount;
        _isTailCall = false;
    }
    
    public InstrCall( int argCount, int retCount, boolean isTailCall )
    {
        _argCount = argCount;
        _returnCount = retCount;
        _isTailCall = isTailCall;
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
    
    /**
     * Transfer arguments between two different stacks
     * [ A < B < C ] -> [ C < B < A ]
     */
    private static void transferArguments( int count,
                                           Stack<MislValue> sourceStack,
                                           Stack<MislValue> targetStack )
    {
        if ( count > 0 )
        {
            for ( int i = 0; i < count; i++ )
            {
                targetStack.push( sourceStack.pop() );
            }
        }
    }
    
    private static void pushMissingArguments( int count, Stack<MislValue> stack )
    {
        for ( int i = 0; i < count; i++ )
        {
            stack.push( MislValue.NIL );
        }
    }
    
    private void executeScriptedFunction( MislFunction f, Stack<MislFrame> frameStack, ScriptContext context )
    {
        MislFrame oldFrame = frameStack.peek();
        Stack<MislValue> oldStack = oldFrame.getStack();
        
        if ( _isTailCall )
        {
            if ( _argCount > 0 )
            {
                oldStack.swap( _argCount );
            }
            
            pushMissingArguments( f.getParamCount() - _argCount, oldStack );
            
            context.leaveFunctionScope();
            
            MTSLog.finer( "[Engine] TailCall stack: %s", oldStack );
        }
        else
        {
            MislFrame newFrame = new MislFrame( _next, _returnCount );
            Stack<MislValue> newStack = newFrame.getStack();
            
            transferArguments( Math.min( _argCount, f.getParamCount() ), oldStack, newStack );
            pushMissingArguments( f.getParamCount() - _argCount, newStack );
            
            frameStack.push( newFrame );
            
            MTSLog.finer( "[Engine] Call stack: %s", newStack );
        }
        
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
            for ( int i = 0; i < _returnCount; i++ )
            {
                stack.push( MislValue.NIL );
            }
        }
        else if ( result.isArray() )
        {
            MislArray arr = result.asArray();
            
            for ( int i = 0; i < _returnCount; i++ )
            {
                stack.push( arr.get( i ) );
            }
        }
        else if ( _returnCount > 0 )
        {
            stack.push( result );
            
            for ( int i = 1; i < _returnCount; i++ )
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
        return _isTailCall
                ? String.format( "%1$-10s %2$s", "TAILCALL", _argCount )
                : String.format( "%1$-10s %2$s", "CALL", _argCount + " " + _returnCount );
    }
}