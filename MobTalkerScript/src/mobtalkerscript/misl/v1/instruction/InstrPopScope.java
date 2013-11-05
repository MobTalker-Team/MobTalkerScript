package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.util.*;

public class InstrPopScope extends MislInstruction
{
    @Override
    public void execute(Stack<MislFrame> frameStack, ScriptContext context)
    {
        context.leaveBlockScope();
    }
    
    @Override
    public String toString()
    {
        return "pop_scope";
    }
    
}
