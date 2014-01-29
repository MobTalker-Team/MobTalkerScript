package mobtalkerscript.mts.v2.instruction;

import mobtalkerscript.mts.v2.value.*;

public class InstrLoadTE extends MtsIndexedInstruction
{
    public InstrLoadTE( int i )
    {
        super( i );
    }
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsTable t = frame.getExternal( _index ).get().asTable();
        frame.push( t.get( frame.pop() ) );
    }
    
    @Override
    public String toString()
    {
        return "LOADTE " + _index;
    }
}
