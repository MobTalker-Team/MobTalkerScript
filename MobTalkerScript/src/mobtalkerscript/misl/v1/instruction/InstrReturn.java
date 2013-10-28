package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrReturn extends MislInstruction
{
    
    public InstrReturn()
    {}
    
    // ========================================
    
    @Override
    public void execute(Stack<MislFrame> frameStack, ScriptContext context)
    {
        MislFrame oldFrame = frameStack.pop();
        
        _next = oldFrame.getReturnPointer();
        
        if (!frameStack.isEmpty())
        {
            MislFrame curFrame = frameStack.peek();
            
            curFrame.setCurrentLine(oldFrame.getCurrentLine());
            
            Stack<MislValue> oldStack = oldFrame.getStack();
            Stack<MislValue> curStack = curFrame.getStack();
            
            // Quite useless, since we only allow one return value
            for (int i = 0; i < oldFrame.getReturnCount(); i++)
            {
                MislValue returnValue = oldStack.isEmpty() ? MislValue.NIL : oldStack.pop();
                curStack.push(returnValue);
            }
            
            // Leftovers will be discarded with the stack.
        }
        
        context.leaveFunctionScope();
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "return";
    }
    
}