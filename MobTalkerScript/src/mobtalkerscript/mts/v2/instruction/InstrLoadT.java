package mobtalkerscript.mts.v2.instruction;

import mobtalkerscript.mts.v2.value.*;

public class InstrLoadT extends MtsInstruction
{
    @Override
    public void execute( MtsFrame frame )
    {
        MtsValue key = frame.pop();
        frame.push( frame.pop().get( key ) );
    }
    
    @Override
    public String toString()
    {
        return "LOADT";
    }
}
