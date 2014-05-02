package mobtalkerscript.mts.v2.instruction;

import mobtalkerscript.mts.v2.value.*;

public final class InstrReturn extends MtsInstruction
{
    private final int _count;
    
    // ========================================
    
    /* package */InstrReturn( int count )
    {
        _count = count;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        frame.push( new MtsVarArgs( frame.pop( _count ) ) );
    }
    
    @Override
    public boolean exits()
    {
        return true;
    }
    
    @Override
    public int stackSizeChange()
    {
        return 1 - _count;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "RETURN " + _count;
    }
}
