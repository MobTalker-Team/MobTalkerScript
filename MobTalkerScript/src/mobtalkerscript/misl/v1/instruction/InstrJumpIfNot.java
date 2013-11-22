package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrJumpIfNot extends AbstractStackInstruction
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
    protected void doExecute( Stack<MislValue> stack, ScriptContext context )
    {
        MislValue condition = stack.pop();
        
        if ( MislBoolean.isTrue( condition ) )
        {
            _next = _if;
        }
        else
        {
            _next = _else;
        }
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return String.format( "%1$-10s %2$s", "JUMP_IFN", _else.getName() );
    }
}