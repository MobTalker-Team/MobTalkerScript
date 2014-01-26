package mobtalkerscript.mts.v2.instruction;

public final class InstrLoadC extends MtsIndexedInstruction
{
    public InstrLoadC( int i )
    {
        super( i );
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        frame.pushConstant( _index );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "LOADC " + _index;
    }
}