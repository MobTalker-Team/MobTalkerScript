package mobtalkerscript.v2.instruction;

import mobtalkerscript.v2.value.*;

public class InstrEQ extends MtsComparisonInstruction
{
    @Override
    protected MtsBoolean compare( MtsValue a, MtsValue b )
    {
        return a.isEqual( b );
    }
    
    @Override
    public String toString()
    {
        return "EQ";
    }
}
