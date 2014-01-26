package mobtalkerscript.mts.v2.instruction;

import mobtalkerscript.mts.v2.value.*;

public class InstrNEQ extends MtsComparisonInstruction
{
    @Override
    protected MtsValue compare( MtsValue a, MtsValue b )
    {
        return a.equalsMts( b ).not();
    }
    
    @Override
    public String toString()
    {
        return "NEQ";
    }
}
