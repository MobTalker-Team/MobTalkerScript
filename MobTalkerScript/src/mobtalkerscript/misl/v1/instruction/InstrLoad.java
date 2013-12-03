package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;

public class InstrLoad extends AbstractMislInstruction
{
    private final String _varName;
    
    // ========================================
    
    public InstrLoad( String var )
    {
        _varName = var;
    }
    
    // ========================================
    
    @Override
    protected void doExecute( MislFrame frame, ScriptContext context )
    {
        frame.push( context.getValue( _varName ) );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return String.format( "%1$-10s %2$s", "LOAD", _varName );
    }
}