package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrNeg extends AbstractStackInstruction
{
    
    @Override
    protected void doExecute(Stack<MislValue> stack, ScriptContext context)
    {
        MislNumber value = stack.pop().asNumber();
        
        MislNumber negated = value.neg();
        
        stack.push(negated);
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "neg";
    }
    
}
