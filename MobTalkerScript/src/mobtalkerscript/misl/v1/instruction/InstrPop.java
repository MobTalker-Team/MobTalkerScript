package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;

public class InstrPop extends AbstractMislInstruction
{
    @Override
    protected void doExecute( MislFrame frame, ScriptContext context )
    {
        frame.pop();
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "POP";
    }
}
