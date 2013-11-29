package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;

public class InstrJumpIfNot extends AbstractMislInstruction
{
    private MislInstruction _if;
    private final InstrLabel _else;
    
    // ========================================
    
    public InstrJumpIfNot( InstrLabel nextElse )
    {
        _else = nextElse;
    }
    
    // ========================================
    
    @Override
    void setNext( MislInstruction next )
    {
        super.setNext( next );
        _if = next;
    }
    
    // ========================================
    
    @Override
    protected void doExecute( MislFrame frame, ScriptContext context )
    {
        _next = MislBoolean.isTrue( frame.pop() ) ? _if : _else;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return String.format( "%1$-10s %2$s", "JUMP_IFN", _else.getName() );
    }
}