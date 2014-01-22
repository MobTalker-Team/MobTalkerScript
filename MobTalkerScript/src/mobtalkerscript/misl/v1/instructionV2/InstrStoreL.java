package mobtalkerscript.misl.v1.instructionV2;

import mobtalkerscript.misl.v1.*;

public final class InstrStoreL extends MislIndexedInstruction
{
    public InstrStoreL( int i )
    {
        super( i );
    }
    
    @Override
    public void execute( MislFrame frame )
    {
        frame.storeLocal( _index );
    }
}
