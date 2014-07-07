package net.mobtalker.mobtalkerscript.v2.instruction;

public class InstrNeg extends MtsInstruction
{
    @Override
    public void execute( MtsFrame frame )
    {
        frame.push( MtsArithmeticInstruction.coerce( frame.pop() ).neg() );
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
