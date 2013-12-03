package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;

public class InstrDecr extends AbstractMislInstruction
{
    @Override
    protected void doExecute( MislFrame frame, ScriptContext context )
    {
        frame.push( frame.pop().asNumber().decr() );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "DECR";
    }
}
