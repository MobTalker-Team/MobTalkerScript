package mobtalkerscript.v2.instruction;

public class InstrDecrL extends MtsIndexedInstruction
{
    public InstrDecrL( int i )
    {
        super( i );
    }
    
    @Override
    public void execute( MtsFrame frame )
    {
        FrameValue register = frame.getLocal( _index );
        register.set( register.get().asNumber().decr() );
    }
    
    @Override
    public int stackSizeChange()
    {
        return 0;
    }
    
    @Override
    public String toString()
    {
        return "DECRL " + _index;
    }
}
