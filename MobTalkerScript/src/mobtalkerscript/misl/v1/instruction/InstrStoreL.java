package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;

public class InstrStoreL extends AbstractMislInstruction
{
    private final String _varName;
    
    // ========================================
    
    public InstrStoreL( String varName )
    {
        _varName = varName;
    }
    
    // ========================================
    
    @Override
    protected void doExecute( MislFrame frame, ScriptContext context )
    {
        MislValue value = frame.pop();
        
        if ( value == null )
        {
            throw new ScriptEngineException( "Stack was empty" );
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
