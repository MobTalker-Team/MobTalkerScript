package mobtalkerscript.mts.v2.instruction;

import mobtalkerscript.mts.v2.value.*;

public class InstrStoreT extends MtsInstruction
{
    @Override
    public void execute( MtsFrame frame )
    {
        MtsValue t = frame.pop();
        MtsValue key = frame.pop();
        t.set( key, frame.pop() );
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
