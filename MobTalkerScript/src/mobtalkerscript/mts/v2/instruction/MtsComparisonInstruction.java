package mobtalkerscript.mts.v2.instruction;

import mobtalkerscript.mts.v2.value.*;

/* package */abstract class MtsComparisonInstruction extends MtsInstruction
{
    @Override
    public final void execute( MtsFrame frame )
    {
        MtsValue b = frame.pop();
        frame.push( compare( frame.pop(), b ) );
    }
    
    protected abstract MtsBoolean compare( MtsValue a, MtsValue b );
    
    @Override
    public final int stackSizeChange()
    {
        return -1;
    }
}
