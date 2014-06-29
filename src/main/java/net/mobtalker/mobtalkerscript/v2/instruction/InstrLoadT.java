package net.mobtalker.mobtalkerscript.v2.instruction;

import net.mobtalker.mobtalkerscript.v2.value.*;

public class InstrLoadT extends MtsInstruction
{
    @Override
    public void execute( MtsFrame frame )
    {
        MtsValue key = frame.pop();
        frame.push( frame.pop().get( key, true ) );
    }
    
    @Override
    public int stackSizeChange()
    {
        return -1;
    }
    
    @Override
    public String toString()
    {
        return "LOADT";
    }
}
