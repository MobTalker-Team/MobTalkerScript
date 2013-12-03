package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;

public class InstrError extends AbstractMislInstruction
{
    @Override
    protected void doExecute( MislFrame frame, ScriptContext context )
    {
        throw new ScriptRuntimeException( "%s (at %s:%s)",
                                          frame.pop().toMtsString(),
                                          frame.getSourceName(),
                                          frame.getSourceLine() );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "ERROR";
    }
}
