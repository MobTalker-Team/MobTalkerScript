package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrStoreL extends AbstractStackInstruction
{
    private final String _varName;
    
    // ========================================
    
    public InstrStoreL( String varName )
    {
        _varName = varName;
    }
    
    // ========================================
    
    @Override
    protected void doExecute( Stack<MislValue> stack, ScriptContext context )
    {
        MislValue value = stack.pop();
        
        if ( value == null )
        {
            throw new ScriptEngineException( "Command stack was empty" );
        }
        
        context.setValue( _varName, value );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return String.format( "%1$-10s %2$s", "STORE_L", _varName );
    }
}
