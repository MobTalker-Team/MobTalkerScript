package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrStoreT extends AbstractStackInstruction
{
    @Override
    protected void doExecute( Stack<MislValue> stack, ScriptContext context )
    {
        MislValue value = stack.pop();
        MislValue key = stack.pop();
        MislTable table = stack.pop().asTable();
        
        table.set( key, value );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "STORE_T";
    }
}
