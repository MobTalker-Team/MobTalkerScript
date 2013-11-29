package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;

public class InstrStoreTL extends AbstractMislInstruction
{
    @Override
    protected void doExecute( MislFrame frame, ScriptContext context )
    {
        MislValue v = frame.pop();
        MislTable t = frame.pop().asTable();
        t.set( t.getNextIndex(), v );
    }
    
    @Override
    public String toString()
    {
        return "STORE_TL";
    }
}
