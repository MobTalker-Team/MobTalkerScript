package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrStoreTL extends AbstractStackInstruction
{
    @Override
    protected void doExecute( Stack<MislValue> stack, ScriptContext context )
    {
        MislValue v = stack.pop();
        MislTable t = stack.pop().asTable();
        MislNumber k = t.getNextIndex().asNumber();
        
        t.set( k, v );
    }
    
    @Override
    public String toString()
    {
        return "STORE_TL";
    }
}
