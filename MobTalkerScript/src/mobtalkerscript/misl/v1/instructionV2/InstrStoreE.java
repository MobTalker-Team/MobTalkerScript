package mobtalkerscript.misl.v1.instructionV2;

import mobtalkerscript.misl.v1.*;

public final class InstrStoreE extends MislIndexedInstruction
{
    public InstrStoreE( int i )
    {
        super( i );
    }
    
    @Override
    public void execute( MislFrame frame )
    {
        frame.storeExternal( _index );
    }
}
