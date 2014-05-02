package mobtalkerscript.mts.v2.instruction;

import mobtalkerscript.mts.v2.value.*;

public class InstrLoadLT extends MtsInstruction
{
    private final int _local;
    private final int _constant;
    
    // ========================================
    
    private InstrLoadLT( int local, int constant )
    {
        _local = local;
        _constant = constant;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsValue table = frame.getLocal( _local ).get();
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
        return "LOADLE " + _local + " " + _constant;
    }
}
