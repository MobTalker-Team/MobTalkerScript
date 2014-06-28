package mobtalkerscript.v2.instruction;

import static mobtalkerscript.v2.value.MtsString.*;

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
        frame.push( concat( frame.pop( _count ) ) );
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
