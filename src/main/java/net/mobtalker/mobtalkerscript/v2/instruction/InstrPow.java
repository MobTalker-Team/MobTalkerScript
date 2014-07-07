package net.mobtalker.mobtalkerscript.v2.instruction;

import net.mobtalker.mobtalkerscript.v2.value.*;

public final class InstrPow extends MtsArithmeticInstruction
{
    @Override
    protected MtsNumber calculate( MtsNumber a, MtsNumber b )
    {
        return a.pow( b );
    }
    
    @Override
    public String toString()
    {
        return "POW";
    }
}
