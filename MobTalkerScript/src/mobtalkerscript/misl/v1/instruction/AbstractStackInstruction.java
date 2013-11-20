package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

/**
 * Simplified MtsInstruction where the execute method only receives the current frame's stack.
 * 
 * @author Chimaine
 */
abstract class AbstractStackInstruction extends MislInstruction
{
    
    @Override
    public void execute( Stack<MislFrame> frameStack, ScriptContext context )
    {
        MislFrame curFrame = frameStack.peek();
        Stack<MislValue> curStack = curFrame.getStack();
        
        try
        {
            doExecute( curStack, context );
        }
        catch ( ScriptRuntimeException ex )
        {
            throw new ScriptRuntimeException( "%s (at %s:%s)",
                                              ex.getMessage(),
                                              curFrame.getSourceName(),
                                              curFrame.getSourceLine() );
        }
    }
    
    protected abstract void doExecute( Stack<MislValue> stack, ScriptContext context );
    
}
