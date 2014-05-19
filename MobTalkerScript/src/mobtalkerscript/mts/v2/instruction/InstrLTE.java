package mobtalkerscript.mts.v2.instruction;

import mobtalkerscript.mts.v2.value.*;

public final class InstrLTE extends MtsComparisonInstruction
{
    @Override
    protected MtsBoolean compare( MtsValue a, MtsValue b )
    {
        return a.isLessOrEqual( b );
    }
    
    @Override
    public String toString()
    {
        return "LTE";
    }
}
