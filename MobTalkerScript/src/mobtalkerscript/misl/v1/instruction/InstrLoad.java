package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrLoad extends AbstractStackInstruction
{
    private final String _varName;
    
    // ========================================
    
    public InstrLoad( String var )
    {
        _varName = var;
    }
    
    // ========================================
    
    @Override
    protected void doExecute( Stack<MislValue> stack, ScriptContext context )
    {
        MislValue value = context.getValue( _varName );
        
        stack.push( value );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return String.format( "%1$-10s %2$s", "LOAD", _varName );
    }
}