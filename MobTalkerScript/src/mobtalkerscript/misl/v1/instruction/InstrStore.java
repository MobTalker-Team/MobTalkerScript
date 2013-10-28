package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.*;
import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrStore extends AbstractStackInstruction
{
    
    private final String _varName;
    
    // ========================================
    
    public InstrStore(String varName)
    {
        _varName = varName;
    }
    
    // ========================================
    
    @Override
    protected void doExecute(Stack<MislValue> stack, ScriptContext context)
    {
        MislValue value = stack.pop();
        
        if (value == null)
        {
            throw new ScriptEngineException("Command stack was empty");
        }
        
        IBindings env = context.getScriptEnvironment();
        
        env.set(_varName, value);
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "store " + _varName;
    }
}