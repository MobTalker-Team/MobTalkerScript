package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.value.*;

public class InstrMod extends AbstractNumberBinaryOperator
{
    
    @Override
    protected MislNumber calculate(MislNumber a, MislNumber b)
    {
        return MislNumber.mod(a, b);
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "mod";
    }
    
}