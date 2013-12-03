package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;

public class InstrAnd extends AbstractMislInstruction
{
    
    private MislInstruction _b;
    private MislInstruction _cont;
    
    // ========================================
    
    public InstrAnd( MislInstruction cont )
    {
        _cont = cont;
    }
    
    // ========================================
    
    @Override
    void setNext( MislInstruction next )
    {
        super.setNext( next );
        _b = next;
    }
    
    // ========================================
    
    @Override
    protected void doExecute( MislFrame frame, ScriptContext context )
    {
        if ( MislBoolean.isTrue( frame.peek() ) )
        {
            _next = _b;
            frame.pop();
        }
        else
        {
            _next = _cont;
        }
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "AND";
    }
}
