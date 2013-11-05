package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrIncr extends AbstractStackInstruction
{
    private final String _var;
    
    // ========================================
    
    public InstrIncr(String var)
    {
        _var = var;
    }
    
    // ========================================
    
    @Override
    protected void doExecute(Stack<MislValue> stack, ScriptContext context)
    {
        IBindings env = context.getCurrentScope();
        
        MislNumber value = env.get(_var).asNumber();
        
        stack.push(value.incr());
    }
    
    @Override
    public String toString()
    {
        return "incr " + _var;
    }
    
}
