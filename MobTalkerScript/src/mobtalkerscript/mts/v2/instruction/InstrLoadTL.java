package mobtalkerscript.mts.v2.instruction;

import mobtalkerscript.mts.v2.value.*;

public class InstrLoadTL extends MtsIndexedInstruction
{
    public InstrLoadTL( int i )
    {
        super( i );
    }
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsTable t = frame.getLocal( _index ).get().asTable();
        frame.push( t.get( frame.pop() ) );
    }
    
    @Override
    public String toString()
    {
        return "LOADTL " + _index;
    }
}
