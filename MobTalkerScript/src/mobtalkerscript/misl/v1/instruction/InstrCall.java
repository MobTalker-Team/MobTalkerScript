package mobtalkerscript.misl.v1.instruction;

import java.util.*;

import mobtalkerscript.*;
import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.Stack;
import mobtalkerscript.util.logging.*;

public class InstrCall extends MislInstruction
{
    
    private final int _argCount;
    private final int _retCount;
    
    private MislInstruction _jumpPointer;
    
    // ========================================
    
    public InstrCall(int argCount, int retCount)
    {
        _argCount = argCount;
        _retCount = retCount;
    }
    
    // ========================================
    
    @Override
    public MislInstruction getNext()
    {
        return _jumpPointer;
    }
    
    // ========================================
    
    @Override
    public void execute(Stack<MislFrame> frameStack, ScriptContext context)
    {
        MislFrame curFrame = frameStack.peek();
        Stack<MislValue> curStack = curFrame.getStack();
        
        MislValue top = curStack.pop();
        
        if (top.isNativeFunction())
        {
            MislNativeFunction f = top.asNativeFunction();
            try
            {
                executeNativeFunction(f, curStack, context);
            }
            catch (ScriptRuntimeException ex)
            {
                throw new ScriptRuntimeException(ex.getMessage() + " (at line: %s)",
                                                 curFrame.getCurrentLine());
            }
        }
        else if (top.isFunction())
        {
            MislFunction f = top.asFunction();
            executeScriptedFunction(f, frameStack, context);
        }
        else
        {
            throw new ScriptRuntimeException("expected function, got %s (at line: %s)",
                                             top.getTypeName(),
                                             curFrame.getCurrentLine());
        }
    }
    
    private void executeScriptedFunction(MislFunction f, Stack<MislFrame> frameStack, ScriptContext context)
    {
        MislFrame oldFrame = frameStack.peek();
        Stack<MislValue> oldStack = oldFrame.getStack();
        
        MislFrame newFrame = new MislFrame(_next, _retCount, oldFrame.getCurrentLine());
        Stack<MislValue> newStack = newFrame.getStack();
        
        int paramCount = Math.max(f.getArgCount(), _argCount);
        for (int i = 0; i < paramCount; i++)
        {
            MislValue param = oldStack.isEmpty() ? MislValue.NIL : oldStack.pop();
            newStack.push(param);
        }
        
        if (MTSLog.isFinerEnabled())
        {
            MTSLog.finer("Call stack: %s", newStack.toString());
        }
        
        frameStack.push(newFrame);
        
        context.enterFunctionScope();
        
        _jumpPointer = f.getInstruction();
    }
    
    private void executeNativeFunction(MislNativeFunction f, Stack<MislValue> stack, ScriptContext context)
    {
        MislValue[] args = new MislValue[_argCount];
        
        for (int i = _argCount - 1; i >= 0; i--)
        {
            args[i] = stack.pop();
        }
        
        if (MTSLog.isFinerEnabled())
        {
            MTSLog.finer("Call stack: %s", Arrays.toString(args));
        }
        
        IBindings env = context.getCurrentScope();
        
        MislValue result = f.call(env, args);
        
        if (_retCount > 0)
        {
            stack.push(result == null ? MislValue.NIL : result);
        }
        
        _jumpPointer = _next;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "call " + _argCount + " " + _retCount;
    }
}