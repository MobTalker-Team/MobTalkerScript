package mobtalkerscript.mts.v2.instruction;

public final class InstrStoreL extends MtsIndexedInstruction
{
    public InstrStoreL( int i )
    {
        super( i );
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        frame.storeLocal( _index );
    }
    
    @Override
    public int stackSizeChange()
    {
        return -1;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "STOREL " + _index;
    }
}
