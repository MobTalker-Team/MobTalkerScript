package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;

public class InstrIncr extends AbstractMislInstruction
{
    @Override
    protected void doExecute( MislFrame frame, ScriptContext context )
    {
        frame.push( frame.pop().asNumber().incr() );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "INCR";
    }
}