package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;

public class InstrNeg extends AbstractMislInstruction
{
    @Override
    protected void doExecute( MislFrame frame, ScriptContext context )
    {
        frame.push( frame.pop().asNumber().neg() );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "NEG";
    }
}
