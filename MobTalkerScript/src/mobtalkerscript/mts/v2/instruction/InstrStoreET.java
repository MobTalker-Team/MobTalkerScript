package mobtalkerscript.mts.v2.instruction;

import mobtalkerscript.mts.v2.value.*;

public class InstrStoreET extends MtsInstruction
{
    private final int _external;
    private final int _constant;
    
    // ========================================
    
    private InstrStoreET( int external, int constant )
    {
        _external = external;
        _constant = constant;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsValue table = frame.getExternal( _external ).get();
        MtsValue key = frame.getConstant( _constant );
        table.set( key, frame.pop() );
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
        return "STOREET " + _external + " " + _constant;
    }
}
