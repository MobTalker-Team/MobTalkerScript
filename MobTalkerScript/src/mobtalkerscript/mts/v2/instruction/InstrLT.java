package mobtalkerscript.mts.v2.instruction;

import static mobtalkerscript.mts.v2.value.MtsValue.*;
import mobtalkerscript.mts.v2.value.*;

public final class InstrLT extends MtsComparisonInstruction
{
    @Override
    protected MtsValue compare( MtsValue a, MtsValue b )
    {
        return valueOf( a.compareTo( b ) < 0 );
    }
    
    @Override
    public String toString()
    {
        return "LT";
    }
}
