package net.mobtalker.mobtalkerscript.v2.instruction;

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
    public int stackSizeChange()
    {
        return 0;
    }
    
    @Override
    public String toString()
    {
        return "INCRL " + _index;
    }
}
