package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.value.*;

public class InstrAdd extends AbstractNumberBinaryOperator
{
    
    @Override
    protected MislNumber calculate(MislNumber a, MislNumber b)
    {
        return MislNumber.add(a, b);
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "add";
    }
}