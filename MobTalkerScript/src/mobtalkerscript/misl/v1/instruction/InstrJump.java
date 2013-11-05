package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.util.*;

public class InstrJump extends MislInstruction
{
    
    private final InstrLabel _label;
    private final boolean _popFrame;
    private final boolean _popScope;
    
    // ========================================
    
    public InstrJump(InstrLabel label, boolean popFrame, boolean popScope)
    {
        if (label == null)
        {
            throw new NullPointerException("label");
        }
        
        _label = label;
        _popFrame = popFrame;
        _popScope = popScope;
    }
    
    // ========================================
    
    @Override
    public MislInstruction getNext()
    {
        return _label;
    }
    
    // ========================================
    
    @Override
    public void execute(Stack<MislFrame> frameStack, ScriptContext context)
    {
        if (_popFrame)
        {
            frameStack.pop();
        }
        
        if (_popScope)
        {
            context.leaveFunctionScope();
            context.enterFunctionScope();
        }
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "jump " + _label.getName();
    }
    
}