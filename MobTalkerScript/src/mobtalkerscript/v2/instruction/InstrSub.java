package mobtalkerscript.v2.instruction;

import mobtalkerscript.v2.value.*;

public class InstrSub extends MtsArithmeticInstruction
{
    @Override
    protected MtsNumber calculate( MtsNumber a, MtsNumber b )
    {
        return a.sub( b );
    }
    
    @Override
    public String toString()
    {
        return "SUB";
    }
}
