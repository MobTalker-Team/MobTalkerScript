package mobtalkerscript.mts.v2.instruction;

import static mobtalkerscript.mts.v2.value.MtsValue.*;

public final class InstrReturn extends MtsInstruction
{
    private final int _count;
    
    // ========================================
    
    public InstrReturn( int count )
    {
        _count = count;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        frame.push( arrayOf( frame.pop( _count ) ) );
    }
    
    @Override
    public boolean exits()
    {
        return true;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "RETURN " + _count;
    }
}
