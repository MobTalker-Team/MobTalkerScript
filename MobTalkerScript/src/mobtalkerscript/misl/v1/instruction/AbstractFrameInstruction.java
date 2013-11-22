package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.util.*;

/**
 * Simplified MtsInstruction where the execute method only receives the current frame.
 * 
 * @author Chimaine
 */
public abstract class AbstractFrameInstruction extends MislInstruction
{
    
    @Override
    public void execute( Stack<MislFrame> frameStack, ScriptContext context )
    {
        MislFrame curFrame = frameStack.peek();
        
        try
        {
            doExecute( curFrame, context );
        }
        catch ( ScriptRuntimeException ex )
        {
            throw new ScriptRuntimeException( "%s (at %s:%s)",
                                              ex.getMessage(),
                                              curFrame.getSourceName(),
                                              curFrame.getSourceLine() );
        }
    }
    
    protected abstract void doExecute( MislFrame frame, ScriptContext context );
    
}
