package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrLoadT extends AbstractStackInstruction
{
    
    @Override
    protected void doExecute(Stack<MislValue> stack, ScriptContext context)
    {
        MislValue key = stack.pop();
        MislTable table = stack.pop().asTable();
        
        MislValue value = table.get(key);
        
        stack.push(value);
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "load_t";
    }
    
}
