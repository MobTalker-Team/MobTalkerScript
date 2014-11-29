package net.mobtalker.mobtalkerscript.v3.instruction;

import net.mobtalker.mobtalkerscript.v3.MtsFrame;

public class InstrVarargsAll extends MtsInstruction
{
    @Override
    public void execute( MtsFrame frame )
    {
        frame.pushVarargs();
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
        return "VARARGS -1";
    }
}
