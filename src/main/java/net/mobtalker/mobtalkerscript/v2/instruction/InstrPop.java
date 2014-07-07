package net.mobtalker.mobtalkerscript.v2.instruction;

public class InstrPop extends MtsInstruction
{
    @Override
    public void execute( MtsFrame frame )
    {
        frame.pop();
    }
    
    @Override
    public int stackSizeChange()
    {
        return -1;
    }
    
    @Override
    public String toString()
    {
        return "POP";
    }
}
