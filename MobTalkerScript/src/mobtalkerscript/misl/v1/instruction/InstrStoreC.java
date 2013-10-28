package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.*;
import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrStoreC extends AbstractStackInstruction
{
    
    private final String _varName;
    private final MislValue _constant;
    
    // ========================================
    
    public InstrStoreC(String varName, MislValue value)
    {
        if (!(value.isNumber() || value.isBoolean() || value.isString() || value.isNil()))
        {
            throw new ScriptEngineException("can only push constants");
        }
        
        _varName = varName;
        _constant = value;
    }
    
    // ========================================
    
    @Override
    protected void doExecute(Stack<MislValue> stack, ScriptContext context)
    {
        IBindings env = context.getScriptEnvironment();
        
        env.set(_varName, _constant);
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "store_c " + _varName + " " + _constant;
    }
}