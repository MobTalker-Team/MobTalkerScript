package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;

public class InstrDup extends AbstractMislInstruction
{
    @Override
    protected void doExecute( MislFrame frame, ScriptContext context )
    {
        frame.push( frame.peek() );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "DUP";
    }
}
