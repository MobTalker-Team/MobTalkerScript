package mobtalkerscript.v2.instruction;

import mobtalkerscript.v2.value.*;

public final class InstrMul extends MtsArithmeticInstruction
{
    @Override
    protected MtsNumber calculate( MtsNumber a, MtsNumber b )
    {
        return a.mul( b );
    }
    
    @Override
    public String toString()
    {
        return "MUL";
    }
}
