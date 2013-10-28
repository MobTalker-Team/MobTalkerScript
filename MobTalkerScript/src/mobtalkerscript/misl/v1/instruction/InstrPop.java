package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrPop extends AbstractStackInstruction
{
    
    @Override
    protected void doExecute(Stack<MislValue> stack, ScriptContext context)
    {
        stack.pop();
    }
    
    @Override
    public String toString()
    {
        return "pop";
    }
    
}
