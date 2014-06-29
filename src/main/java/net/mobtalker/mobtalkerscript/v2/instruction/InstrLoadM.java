package net.mobtalker.mobtalkerscript.v2.instruction;

import net.mobtalker.mobtalkerscript.v2.value.*;

public final class InstrLoadM extends MtsIndexedInstruction
{
    /* package */InstrLoadM( int index )
    {
        super( index );
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsValue table = frame.pop();
        MtsValue key = frame.getConstant( _index );
        MtsValue method = table.get( key );
        
        frame.push( method );
        frame.push( table );
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
        return "LOADM " + _index;
    }
}
