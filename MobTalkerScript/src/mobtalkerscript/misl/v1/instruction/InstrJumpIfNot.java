package mobtalkerscript.misl.v1.instruction;

import static mobtalkerscript.misl.v1.value.MislValue.*;
import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrJumpIfNot extends AbstractStackInstruction
{
    
    private MislInstruction _if;
    private final InstrLabel _else;
    
    // ========================================
    
    public InstrJumpIfNot(InstrLabel nextElse)
    {
        _else = nextElse;
    }
    
    // ========================================
    
    @Override
    void setNext(MislInstruction next)
    {
        super.setNext(next);
        _if = next;
    }
    
    // ========================================
    
    @Override
    protected void doExecute(Stack<MislValue> stack, ScriptContext context)
    {
        MislValue condition = stack.pop();
        
        if ((condition == FALSE) || (condition == NIL))
        {
            _next = _else;
        }
        else
        {
            _next = _if;
        }
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "jumpn " + _else.getName();
    }
    
}