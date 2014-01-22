package mobtalkerscript.misl.v1.instructionV2;

import mobtalkerscript.misl.v1.*;

public final class InstrLoadL extends MislIndexedInstruction
{
    public InstrLoadL( int i )
    {
        super( i );
    }
    
    @Override
    public void execute( MislFrame frame )
    {
        frame.pushLocal( _index );
    }
}
