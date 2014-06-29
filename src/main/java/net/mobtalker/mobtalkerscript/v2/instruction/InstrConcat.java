package net.mobtalker.mobtalkerscript.v2.instruction;

import net.mobtalker.mobtalkerscript.v2.value.*;

public class InstrConcat extends MtsInstruction
{
    private final int _count;
    
    // ========================================
    
    /* package */InstrConcat( int count )
    {
        _count = count;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        frame.push( MtsString.concat( frame.pop( _count ) ) );
    }
    
    @Override
    public int stackSizeChange()
    {
        return _count - 1;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "CONCAT " + _count;
    }
}
