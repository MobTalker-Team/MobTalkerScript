package mobtalkerscript.misl.v1.instructionV2;

import mobtalkerscript.misl.v1.*;

public final class InstrLoadC extends MislIndexedInstruction
{
    public InstrLoadC( int i )
    {
        super( i );
    }
    
    @Override
    public void execute( MislFrame frame )
    {
        frame.pushConstant( _index );
    }
}