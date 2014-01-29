package mobtalkerscript.mts.v2.instruction;

public class InstrIncrL extends MtsIndexedInstruction
{
    public InstrIncrL( int i )
    {
        super( i );
    }
    
    @Override
    public void execute( MtsFrame frame )
    {
        frame.setLocal( _index, frame.getLocal( _index ).asNumber().incr() );
    }
    
    @Override
    public String toString()
    {
        return "INCRL " + _index;
    }
}
