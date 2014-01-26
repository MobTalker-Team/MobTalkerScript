package mobtalkerscript.mts.v2.instruction;

public final class InstrStoreE extends MtsIndexedInstruction
{
    public InstrStoreE( int i )
    {
        super( i );
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        frame.storeExternal( _index );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "STOREE " + _index;
    }
}
