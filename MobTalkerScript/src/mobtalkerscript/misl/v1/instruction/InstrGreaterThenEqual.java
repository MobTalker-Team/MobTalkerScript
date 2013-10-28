package mobtalkerscript.misl.v1.instruction;

import static mobtalkerscript.misl.v1.value.MislValue.*;
import mobtalkerscript.*;
import mobtalkerscript.misl.v1.value.*;

public class InstrGreaterThenEqual extends AbstractBinaryOperator
{
    
    @Override
    protected MislValue getResult(MislValue a, MislValue b)
    {
        if (!(a.isNumber() || a.isString()))
        {
            throw new ScriptRuntimeException("can only compare numbers and strings, got %s", a.getTypeName());
        }
        if (!(b.isNumber() || b.isString()))
        {
            throw new ScriptRuntimeException("can only compare numbers and strings, got %s", a.getTypeName());
        }
        
        return valueOf(a.compareTo(b) >= 0);
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "gte";
    }
}
