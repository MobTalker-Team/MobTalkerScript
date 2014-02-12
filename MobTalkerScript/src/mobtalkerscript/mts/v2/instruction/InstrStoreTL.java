package mobtalkerscript.mts.v2.instruction;

import mobtalkerscript.mts.v2.value.*;

public final class InstrStoreTL extends MtsIndexedInstruction
{
    public InstrStoreTL( int i )
    {
        super( i );
    }
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsValue value = frame.pop();
        MtsValue key = frame.pop();
        MtsValue t = frame.getLocal( _index ).get();
        t.set( key, value );
    }
    
    @Override
    public String toString()
    {
        return "STORETL " + _index;
    }
}
