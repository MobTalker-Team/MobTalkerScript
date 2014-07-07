package net.mobtalker.mobtalkerscript.v2.instruction;

public class InstrDup extends MtsInstruction
{
    @Override
    public void execute( MtsFrame frame )
    {
        frame.duplicateTop();
    }
    
    @Override
    public int stackSizeChange()
    {
        return 1;
    }
    
    @Override
    public String toString()
    {
        return "DUP";
    }
}
