package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrTailCall extends AbstractInstrCall
{
    
    public InstrTailCall( int argCount, int returnCount )
    {
        super( argCount, returnCount );
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected void executeScriptedFunction( MislFunction f, Stack<MislFrame> frameStack, ScriptContext context )
    {
        MislFrame curFrame = frameStack.peek();
        
//        if ( _argCount > 0 )
//        {
//            curStack.swap( _argCount );
//        }
        
        pushMissingArguments( f.getParamCount() - _argCount, curFrame );
        
        context.leaveFunctionScope();
        context.enterFunctionScope();
        
        _jumpPointer = f.getInstruction();
    }
    
    @Override
    public String toString()
    {
        return String.format( "%1$-10s %2$s", "TAILCALL", _argCount + " " + _returnCount );
    }
    
}
