package mobtalkerscript.v2.instruction;

import mobtalkerscript.v2.value.*;

public final class InstrAdd extends MtsArithmeticInstruction
{
    @Override
    protected MtsNumber calculate( MtsNumber a, MtsNumber b )
    {
        return a.add( b );
    }
    
    @Override
    public String toString()
    {
        return "ADD";
    }
}
