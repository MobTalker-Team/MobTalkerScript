package net.mobtalker.mobtalkerscript.v2.instruction;

import net.mobtalker.mobtalkerscript.v2.*;

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
    
    @Override
    public int stackSizeChange()
    {
        return 1;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "LOADC " + _index;
    }
    
    @Override
    public String toString( MtsFunctionPrototype proto )
    {
        return toString() + " (" + proto.getConstant( _index ) + ")";
    }
}