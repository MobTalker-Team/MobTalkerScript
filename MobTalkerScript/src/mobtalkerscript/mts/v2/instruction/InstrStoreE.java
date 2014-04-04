package mobtalkerscript.mts.v2.instruction;

import mobtalkerscript.mts.v2.*;

public final class InstrStoreE extends MtsIndexedInstruction
{
    public InstrStoreE( int i )
    {
        super( i );
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        frame.storeExternal( _index );
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
        return "STOREE " + _index;
    }
    
    @Override
    public String toString( MtsFunctionPrototype proto )
    {
        return toString() + " (" + proto.getExternals().get( _index ).getName() + ")";
    }
}
