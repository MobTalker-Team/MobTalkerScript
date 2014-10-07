package net.mobtalker.mobtalkerscript.v2.instruction;

import net.mobtalker.mobtalkerscript.v2.MtsFrame;

public class InstrLine extends MtsInstruction
{
    private final int _line;
    
    // ========================================
    
    public InstrLine( int line )
    {
        _line = line;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {}
    
    @Override
    public int stackSizeChange()
    {
        return 0;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "LINE " + _line;
    }
}
