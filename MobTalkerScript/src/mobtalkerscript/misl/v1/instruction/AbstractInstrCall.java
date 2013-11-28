package mobtalkerscript.misl.v1.instruction;

import java.util.*;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.Stack;
import mobtalkerscript.util.logging.*;

public abstract class AbstractInstrCall extends MislInstruction
{
    
    protected final int _argCount;
    protected final int _returnCount;
    
    protected MislInstruction _jumpPointer;
    
    // ========================================
    
    protected AbstractInstrCall( int argCount, int returnCount )
    {
        _argCount = argCount;
        _returnCount = returnCount;
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
        
        if ( top.isNativeFunction() )
        {
            MislNativeFunction f = top.asNativeFunction();
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
        else if ( top.isFunction() )
        {
            MislFunction f = top.asFunction();
            executeScriptedFunction( f, frameStack, context );
        }
        else
        {
            throw new ScriptRuntimeException( "expected function, got %s (at %s:%s)",
                                              top.getTypeName(),
                                              curFrame.getSourceName(),
                                              curFrame.getSourceLine() );
        }
    }
    
    // ========================================
    
    protected abstract void executeScriptedFunction( MislFunction f,
                                                     Stack<MislFrame> frameStack,
                                                     ScriptContext context );
    
    protected void executeNativeFunction( MislNativeFunction f, Stack<MislValue> stack, ScriptContext context )
    {
        MislValue[] args = new MislValue[_argCount];
        
        for ( int i = _argCount - 1; i >= 0; i-- )
        {
            args[i] = stack.pop();
        }
        
        MTSLog.finer( "[Engine] Call stack: %s", Arrays.toString( args ) );
        
        MislValue result = f.call( context.getCurrentScope(), args );
        
        if ( result == null )
        {
            pushMissingArguments( _returnCount, stack );
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
            
            pushMissingArguments( _returnCount - 1, stack );
        }
        
        _jumpPointer = _next;
    }
    
    // ========================================
    
    /**
     * Transfer arguments between two different stacks
     * [ A < B < C ] -> [ C < B < A ]
     */
    protected static void transferArguments( int count,
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
    
    protected static void pushMissingArguments( int count, Stack<MislValue> stack )
    {
        for ( int i = 0; i < count; i++ )
        {
            stack.push( MislValue.NIL );
        }
    }
}
