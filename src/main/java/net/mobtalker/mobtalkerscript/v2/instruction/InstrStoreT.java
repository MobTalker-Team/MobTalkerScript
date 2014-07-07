package net.mobtalker.mobtalkerscript.v2.instruction;

import net.mobtalker.mobtalkerscript.v2.value.*;

public class InstrStoreT extends MtsInstruction
{
    @Override
    public void execute( MtsFrame frame )
    {
        MtsValue key = frame.pop();
        MtsValue t = frame.pop();
        t.set( key, frame.pop(), true );
    }
    
    @Override
    public int stackSizeChange()
    {
        return -3;
    }
    
    @Override
    public String toString()
    {
        return "STORET";
    }
}
