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
    
    private MislInstruction _jumpPointer;
    
    // ========================================
    
    public InstrCall( int argCount, int retCount )
    {
        _argCount = argCount;
        _returnCount = retCount;
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
        MislFrame frame = frameStack.peek();
        MislValue top = frame.pop();
        
        if ( top.isNativeFunction() )
        {
            try
            {
                executeNativeFunction( top.asNativeFunction(), frame, context );
            }
            catch ( ScriptRuntimeException ex )
            {
                throw new ScriptRuntimeException( "%s (at %s:%s)",
                                                  ex.getMessage(),
                                                  frame.getSourceName(),
                                                  frame.getSourceLine() );
            }
        }
        else if ( top.isFunction() )
        {
            executeScriptedFunction( top.asFunction(), frameStack, context );
        }
        else
        {
            throw new ScriptRuntimeException( "expected function, got %s (at %s:%s)",
                                              top.getTypeName(),
                                              frame.getSourceName(),
                                              frame.getSourceLine() );
        }
    }
    
    private void executeScriptedFunction( MislFunction f, Stack<MislFrame> frameStack, ScriptContext context )
    {
        MislFrame oldFrame = frameStack.peek();
        MislFrame newFrame = new MislFrame( _next, _returnCount );
        
        int paramCount = Math.max( f.getArgCount(), _argCount );
        for ( int i = 0; i < paramCount; i++ )
        {
            MislValue param = oldFrame.isEmpty() ? MislValue.NIL : oldFrame.pop();
            newFrame.push( param );
        }
        
        MTSLog.finer( "[Engine] Call stack: %s", newFrame.toString() );
        
        frameStack.push( newFrame );
        
        context.enterFunctionScope();
        
        _jumpPointer = f.getInstruction();
    }
    
    private void executeNativeFunction( MislNativeFunction f, MislFrame frame, ScriptContext context )
    {
        MislValue[] args = new MislValue[_argCount];
        
        for ( int i = _argCount - 1; i >= 0; i-- )
        {
            args[i] = frame.pop();
        }
        
        MTSLog.finer( "[Engine] Call stack: %s", Arrays.toString( args ) );
        
        IBindings env = context.getCurrentScope();
        
        MislValue result = f.call( env, args );
        
        if ( result == null )
        {
            for ( int i = 0; i < _returnCount; i++ )
            {
                frame.push( MislValue.NIL );
            }
        }
        else if ( result.isArray() )
        {
            MislArray arr = result.asArray();
            
            for ( int i = 0; i < _returnCount; i++ )
            {
                frame.push( arr.get( i ) );
            }
        }
        else if ( _returnCount > 0 )
        {
            frame.push( result );
            
            for ( int i = 1; i < _returnCount; i++ )
            {
                frame.push( MislValue.NIL );
            }
        }
        
        _jumpPointer = _next;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return String.format( "%1$-10s %2$s", "CALL", _argCount + " " + _returnCount );
    }
}