package mobtalkerscript.v2.instruction;

import mobtalkerscript.v2.value.*;

public final class InstrDiv extends MtsArithmeticInstruction
{
    @Override
    protected MtsNumber calculate( MtsNumber a, MtsNumber b )
    {
        return a.div( b );
    }
    
    @Override
    public String toString()
    {
        return "DIV";
    }
}
