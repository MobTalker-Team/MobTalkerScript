package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.value.*;

public class InstrSub extends AbstractNumberBinaryOperator
{
    
    @Override
    protected MislNumber calculate(MislNumber a, MislNumber b)
    {
        return MislNumber.sub(a, b);
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "sub";
    }
}