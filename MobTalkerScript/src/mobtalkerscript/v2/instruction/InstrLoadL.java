package mobtalkerscript.v2.instruction;

import mobtalkerscript.v2.*;

public final class InstrLoadL extends MtsIndexedInstruction
{
    public InstrLoadL( int i )
    {
        super( i );
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        frame.pushLocal( _index );
    }
    
    @Override
    public int stackSizeChange()
    {
        return 1;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "LOADL " + _index;
    }
    
    @Override
    public String toString( MtsFunctionPrototype proto )
    {
        return toString() + " (" + proto.getLocalDescription( _index ).getName() + ")";
    }
}
