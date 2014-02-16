package mobtalkerscript.mts.v2.instruction;

import static mobtalkerscript.mts.v2.instruction.MtsArithmeticInstruction.*;

public class InstrNeg extends MtsInstruction
{
    @Override
    public void execute( MtsFrame frame )
    {
        frame.push( coerce( frame.pop() ).neg() );
    }
    
    @Override
    public int stackSizeChange()
    {
        return 0;
    }
    
    @Override
    public String toString()
    {
        return "NEG";
    }
}
