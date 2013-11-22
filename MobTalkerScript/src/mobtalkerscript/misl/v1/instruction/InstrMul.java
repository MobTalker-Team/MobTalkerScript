package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.value.*;

public class InstrMul extends AbstractNumberBinaryOperator
{
    @Override
    protected MislNumber calculate( MislNumber a, MislNumber b )
    {
        return MislNumber.mul( a, b );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "MUL";
    }
}