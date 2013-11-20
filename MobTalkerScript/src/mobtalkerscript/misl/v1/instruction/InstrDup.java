package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrDup extends AbstractStackInstruction
{
    
    @Override
    protected void doExecute( Stack<MislValue> stack, ScriptContext context )
    {
        MislValue value = stack.peek();
        stack.push( value );
    }
    
    @Override
    public String toString()
    {
        return "DUP";
    }
    
}
