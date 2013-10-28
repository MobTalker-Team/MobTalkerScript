package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.*;
import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrJumpF extends MislInstruction
{
    
    @Override
    void setNext(MislInstruction next)
    {}
    
    // ========================================
    
    @Override
    public void execute(Stack<MislFrame> frameStack, ScriptContext context)
    {
        MislFrame frame = frameStack.peek();
        Stack<MislValue> stack = frame.getStack();
        MislValue top = stack.pop();
        
        if (top.isNativeFunction())
        {
            MislNativeFunction f = top.asNativeFunction();
            try
            {
                f.call(context.getCurrentScope());
            }
            catch (ScriptRuntimeException ex)
            {
                throw new ScriptRuntimeException(ex.getMessage() + " (at line: %s)", frame.getCurrentLine());
            }
        }
        else if (top.isFunction())
        {
            context.leaveFunctionScope();
            context.enterFunctionScope();
            
            MislFunction f = top.asFunction();
            
            MislInstruction instr = f.getInstruction();
            _next = instr;
        }
        else
        {
            throw new ScriptRuntimeException("expected function, got %s (at line: %s)",
                                             top.getTypeName(),
                                             frame.getCurrentLine());
        }
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "jumpf";
    }
    
}