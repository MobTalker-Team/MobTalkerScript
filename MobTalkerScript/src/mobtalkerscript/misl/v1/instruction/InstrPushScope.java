package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.util.*;

public class InstrPushScope extends MislInstruction
{
    @Override
    public void execute( Stack<MislFrame> frameStack, ScriptContext context )
    {
        context.enterBlockScope();
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "SCOPE_PUSH";
    }
}
