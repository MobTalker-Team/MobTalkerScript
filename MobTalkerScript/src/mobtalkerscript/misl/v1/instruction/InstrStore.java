package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;

public class InstrStore extends AbstractMislInstruction
{
    private final String _varName;
    
    // ========================================
    
    public InstrStore( String varName )
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
        
        context.setExistingValue( _varName, value );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return String.format( "%1$-10s %2$s", "STORE", _varName );
    }
}