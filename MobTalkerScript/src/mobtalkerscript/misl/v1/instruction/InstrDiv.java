package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.value.*;

public class InstrDiv extends AbstractNumberBinaryOperator
{
    
    @Override
    protected MislNumber calculate(MislNumber a, MislNumber b)
    {
        return MislNumber.div(a, b);
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "div";
    }
    
}