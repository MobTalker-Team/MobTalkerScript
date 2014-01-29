package mobtalkerscript.mts.v2.instruction;

public class InstrDecrL extends MtsIndexedInstruction
{
    public InstrDecrL( int i )
    {
        super( i );
    }
    
    @Override
    public void execute( MtsFrame frame )
    {
        frame.setLocal( _index, frame.getLocal( _index ).asNumber().decr() );
    }
    
    @Override
    public String toString()
    {
        return "DECRL " + _index;
    }
}
