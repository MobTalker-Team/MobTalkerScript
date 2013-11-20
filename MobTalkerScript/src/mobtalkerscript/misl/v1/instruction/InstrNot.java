package mobtalkerscript.misl.v1.instruction;

import static mobtalkerscript.misl.v1.value.MislValue.*;
import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrNot extends AbstractStackInstruction
{
    @Override
    protected void doExecute( Stack<MislValue> stack, ScriptContext context )
    {
        MislValue a = stack.pop();
        
        if ( MislBoolean.isTrue( a ) )
        {
            stack.push( FALSE );
        }
        else
        {
            stack.push( TRUE );
        }
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "NOT";
    }
}
