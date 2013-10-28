package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.*;
import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrPush extends AbstractStackInstruction
{
    
    private final MislValue _value;
    
    // ========================================
    
    public InstrPush(MislValue value)
    {
        if (!(value.isNumber() || value.isBoolean() || value.isString() || value.isNil()))
        {
            throw new ScriptEngineException("can only push constants");
        }
        
        _value = value;
    }
    
    public InstrPush(String value)
    {
        this(MislValue.valueOf(value));
    }
    
    public InstrPush(double value)
    {
        this(MislValue.valueOf(value));
    }
    
    public InstrPush(boolean value)
    {
        this(MislValue.valueOf(value));
    }
    
    // ========================================
    
    @Override
    protected void doExecute(Stack<MislValue> stack, ScriptContext context)
    {
        stack.push(_value);
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "push " + _value.toString();
    }
}