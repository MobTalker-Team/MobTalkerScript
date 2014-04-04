package mobtalkerscript.mts.v2.instruction;

import mobtalkerscript.mts.v2.*;

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
    
    @Override
    public String toString( MtsFunctionPrototype proto )
    {
        return toString() + " (" + proto.getLocals().get( _index ).getName() + ")";
    }
}
