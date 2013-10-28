package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrCreateT extends AbstractStackInstruction
{
    
    private final int _size;
    
    // ========================================
    
    public InstrCreateT(int size)
    {
        _size = size;
    }
    
    // ========================================
    
    @Override
    protected void doExecute(Stack<MislValue> stack, ScriptContext context)
    {
        MislTable table = new MislTable(_size);
        
        for (int i = 0; i < _size; i++)
        {
            MislValue value = stack.pop();
            MislValue key = stack.pop();
            
            table.set(key, value);
        }
        
        stack.push(table);
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "create_t " + _size;
    }
    
}
