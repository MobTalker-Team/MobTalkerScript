package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class MislFrame
{
    private final Stack<MislValue> _stack;
    
    private MislInstruction _retPointer;
    private int _retCount;
    
    private int _curLine;
    
    // ========================================
    
    public MislFrame(MislInstruction retPointer, int retCount, int line)
    {
        _stack = Stack.newStack();
        
        _retPointer = retPointer;
        _retCount = retCount;
        _curLine = line;
    }
    
    // ========================================
    
    public Stack<MislValue> getStack()
    {
        return _stack;
    }
    
    // ========================================
    
    public MislInstruction getReturnPointer()
    {
        return _retPointer;
    }
    
    public int getReturnCount()
    {
        return _retCount;
    }
    
    public int getCurrentLine()
    {
        return _curLine;
    }
    
    // ========================================
    
    public void setRetPointer(MislInstruction retPointer)
    {
        _retPointer = retPointer;
    }
    
    public void setRetCount(int retCount)
    {
        _retCount = retCount;
    }
    
    public void setCurrentLine(int line)
    {
        _curLine = line;
    }
}
