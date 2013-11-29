package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrCall extends AbstractInstrCall
{
    
    public InstrCall( int argCount, int returnCount )
    {
        super( argCount, returnCount );
    }
    
    // ========================================
    
    @Override
    protected void executeScriptedFunction( MislFunction f, Stack<MislFrame> frameStack, ScriptContext context )
    {
        MislFrame oldFrame = frameStack.peek();
        Stack<MislValue> oldStack = oldFrame.getStack();
        
        MislFrame newFrame = new MislFrame( _next, _returnCount );
        Stack<MislValue> newStack = newFrame.getStack();
        
        transferArguments( Math.min( _argCount, f.getParamCount() ), oldStack, newStack );
        pushMissingArguments( f.getParamCount() - _argCount, newStack );
        
        frameStack.push( newFrame );
        
        context.enterFunctionScope();
        
        _jumpPointer = f.getInstruction();
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return String.format( "%1$-10s %2$s", "CALL", _argCount + " " + _returnCount );
    }
}