package mobtalkerscript.v2.instruction;

import mobtalkerscript.v2.value.*;

public class InstrStoreLT extends MtsInstruction
{
    private final int _local;
    private final int _constant;
    
    // ========================================
    
    /* package */InstrStoreLT( int local, int constant )
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
        table.set( key, frame.pop(), true );
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
        return "STORELT " + _local + " " + _constant;
    }
}
