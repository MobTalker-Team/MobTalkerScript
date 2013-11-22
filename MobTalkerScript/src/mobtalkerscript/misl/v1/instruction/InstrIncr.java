package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrIncr extends AbstractStackInstruction
{
    @Override
    protected void doExecute( Stack<MislValue> stack, ScriptContext context )
    {
        MislNumber value = stack.pop().asNumber();
        stack.push( value.incr() );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "INCR";
    }
}
