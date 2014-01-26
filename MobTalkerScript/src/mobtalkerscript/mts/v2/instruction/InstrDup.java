package mobtalkerscript.mts.v2.instruction;

public class InstrDup extends MtsInstruction
{
    @Override
    public void execute( MtsFrame frame )
    {
        frame.duplicateTop();
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "DUP";
    }
}
