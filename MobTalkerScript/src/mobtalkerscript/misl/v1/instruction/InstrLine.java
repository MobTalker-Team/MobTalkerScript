package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;

public class InstrLine extends AbstractMislInstruction
{
    private final String _name;
    private final int _line;
    
    // ========================================
    
    public InstrLine( String name, int line )
    {
        _name = name;
        _line = line;
    }
    
    // ========================================
    
    @Override
    protected void doExecute( MislFrame frame, ScriptContext context )
    {
        frame.setSource( _name, _line );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return String.format( "%1$-10s %2$s", "LINE", _name + ":" + _line );
    }
}
