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
        FrameValue register = frame.getLocal( _index );
        register.set( register.get().asNumber().incr() );
    }
    
    @Override
    public String toString()
    {
        return "INCRL " + _index;
    }
}
