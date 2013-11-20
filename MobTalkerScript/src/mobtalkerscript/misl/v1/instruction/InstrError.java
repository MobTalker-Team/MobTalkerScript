package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrError extends AbstractFrameInstruction
{
    @Override
    protected void doExecute( MislFrame frame, ScriptContext context )
    {
        Stack<MislValue> stack = frame.getStack();
        MislString msg = stack.pop().asString();
        
        throw new ScriptRuntimeException( "%s (at %s:%s)",
                                          msg.toJava(),
                                          frame.getSourceName(),
                                          frame.getSourceLine() );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "ERROR";
    }
}
