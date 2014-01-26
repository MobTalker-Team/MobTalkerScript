package mobtalkerscript.mts.v2.instruction;

public final class InstrLoadE extends MtsIndexedInstruction
{
    public InstrLoadE( int i )
    {
        super( i );
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        frame.pushExternal( _index );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "LOADE " + _index;
    }
}
