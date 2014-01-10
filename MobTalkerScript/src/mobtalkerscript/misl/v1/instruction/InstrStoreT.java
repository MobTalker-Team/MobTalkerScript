package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;

public class InstrStoreT extends AbstractMislInstruction
{
    @Override
    protected void doExecute( MislFrame frame, ScriptContext context )
    {
        MislValue value = frame.pop();
        MislValue key = frame.pop();
        MislTable table = frame.pop().asTable();
        
        table.setRaw( key, value );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "STORE_T";
    }
}
