package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class MislFrame
{
    private final Stack<MislValue> _stack;
    
    private MislInstruction _retPointer;
    private int _retCount;
    
    private String _sourceName;
    private int _sourceLine;
    
    // ========================================
    
    public MislFrame( MislInstruction returnPointer, int returnCount )
    {
        _stack = Stack.newStack();
        
        _retPointer = returnPointer;
        _retCount = returnCount;
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
    
    public String getSourceName()
    {
        return _sourceName;
    }
    
    public int getSourceLine()
    {
        return _sourceLine;
    }
    
    // ========================================
    
    public void setReturnPointer( MislInstruction retPointer )
    {
        _retPointer = retPointer;
    }
    
    public void setReturnCount( int retCount )
    {
        _retCount = retCount;
    }
    
    public void setSource( String name, int line )
    {
        _sourceName = name;
        _sourceLine = line;
    }
}
