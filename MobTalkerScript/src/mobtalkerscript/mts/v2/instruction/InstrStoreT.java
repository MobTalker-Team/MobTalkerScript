package mobtalkerscript.mts.v2.instruction;

import mobtalkerscript.mts.v2.value.*;

public class InstrStoreT extends MtsInstruction
{
    @Override
    public void execute( MtsFrame frame )
    {
        MtsValue value = frame.pop();
        MtsValue key = frame.pop();
        frame.pop().set( key, value );
    }
    
    @Override
    public String toString()
    {
        return "LOADT";
    }
}
