package net.mobtalker.mobtalkerscript.v2.instruction;

import net.mobtalker.mobtalkerscript.v2.value.*;

public final class InstrMod extends MtsArithmeticInstruction
{
    @Override
    protected MtsNumber calculate( MtsNumber a, MtsNumber b )
    {
        return a.mod( b );
    }
    
    @Override
    public String toString()
    {
        return "MOD";
    }
}
