package mobtalkerscript.v2.instruction;

import mobtalkerscript.v2.value.*;

public final class InstrLoadNil extends MtsInstruction
{
    public InstrLoadNil()
    {}
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        frame.push( MtsValue.NIL );
    }
    
    @Override
    public int stackSizeChange()
    {
        return 1;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "LOADNIL";
    }
}