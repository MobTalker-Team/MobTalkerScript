package mobtalkerscript.misl.v1.instruction;

import static mobtalkerscript.misl.v1.value.MislValue.*;
import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;

public class InstrNot extends AbstractMislInstruction
{
    @Override
    protected void doExecute( MislFrame frame, ScriptContext context )
    {
        if ( MislBoolean.isTrue( frame.pop() ) )
        {
            frame.push( FALSE );
        }
        else
        {
            frame.push( TRUE );
        }
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "NOT";
    }
}
