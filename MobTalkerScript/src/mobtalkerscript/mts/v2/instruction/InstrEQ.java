package mobtalkerscript.mts.v2.instruction;

import mobtalkerscript.mts.v2.value.*;

public class InstrEQ extends MtsComparisonInstruction
{
    @Override
    protected MtsValue compare( MtsValue a, MtsValue b )
    {
        return a.isMtsEqual( b );
    }
    
    @Override
    public String toString()
    {
        return "EQ";
    }
}
