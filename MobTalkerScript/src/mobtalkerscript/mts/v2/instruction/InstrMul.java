package mobtalkerscript.mts.v2.instruction;

import static mobtalkerscript.mts.v2.value.MtsNumber.*;
import mobtalkerscript.mts.v2.value.*;

public final class InstrMul extends MtsArithmeticInstruction
{
    @Override
    protected MtsNumber calculate( MtsNumber a, MtsNumber b )
    {
        return mul( a, b );
    }
    
    @Override
    public String toString()
    {
        return "MUL";
    }
}
