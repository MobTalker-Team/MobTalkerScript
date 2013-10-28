package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.value.*;

public class InstrNotEqual extends AbstractBinaryOperator
{
    
    @Override
    protected MislValue getResult(MislValue a, MislValue b)
    {
        return a.equal(b).not();
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "neq";
    }
    
}
