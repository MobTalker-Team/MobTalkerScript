package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;

public class InstrLoadT extends AbstractMislInstruction
{
    @Override
    protected void doExecute( MislFrame frame, ScriptContext context )
    {
        MislValue key = frame.pop();
        MislTable table = frame.pop().asTable();
        
        frame.push( table.getRaw( key ) );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "LOAD_T";
    }
}
