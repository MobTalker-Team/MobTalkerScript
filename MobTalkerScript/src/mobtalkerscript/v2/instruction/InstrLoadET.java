package mobtalkerscript.v2.instruction;

import mobtalkerscript.v2.value.*;

public class InstrLoadET extends MtsInstruction
{
    private final int _external;
    private final int _constant;
    
    // ========================================
    
    private InstrLoadET( int external, int constant )
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
        frame.push( table.get( key ) );
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
        return "LOADTE " + _external + " " + _constant;
    }
}
