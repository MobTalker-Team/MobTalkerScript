package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrLoad extends AbstractStackInstruction
{
    private final String _var;
    
    // ========================================
    
    public InstrLoad(String var)
    {
        _var = var;
    }
    
    // ========================================
    
    @Override
    protected void doExecute(Stack<MislValue> stack, ScriptContext context)
    {
        IBindings env = context.getCurrentScope();
        
        MislValue value = env.get(_var);
        
        stack.push(value);
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "load " + _var;
    }
}