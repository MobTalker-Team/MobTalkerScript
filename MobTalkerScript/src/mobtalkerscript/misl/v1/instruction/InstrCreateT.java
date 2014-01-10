package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;

public class InstrCreateT extends AbstractMislInstruction
{
    
    private final int _size;
    
    // ========================================
    
    public InstrCreateT( int size )
    {
        _size = size;
    }
    
    // ========================================
    
    @Override
    protected void doExecute( MislFrame frame, ScriptContext context )
    {
        // TODO Size
        MislTable table = new MislTable();
        frame.push( table );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return String.format( "%1$-10s %2$s", "CREATE_T", _size );
    }
    
}
