package net.mobtalker.mobtalkerscript.v2.instruction;

import net.mobtalker.mobtalkerscript.v2.value.*;

public final class InstrLT extends MtsComparisonInstruction
{
    @Override
    protected MtsBoolean compare( MtsValue a, MtsValue b )
    {
        return a.isLess( b );
    }
    
    @Override
    public String toString()
    {
        return "LT";
    }
}
