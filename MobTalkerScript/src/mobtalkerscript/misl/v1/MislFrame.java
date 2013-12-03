package mobtalkerscript.misl.v1;

import mobtalkerscript.misl.v1.instruction.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public final class MislFrame
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
    
    public MislInstruction getReturnPointer()
    {
        return _retPointer;
    }
    
    public void setReturnPointer( MislInstruction retPointer )
    {
        _retPointer = retPointer;
    }
    
    // ========================================
    
    public int getReturnCount()
    {
        return _retCount;
    }
    
    public void setReturnCount( int retCount )
    {
        _retCount = retCount;
    }
    
    // ========================================
    
    public String getSourceName()
    {
        return _sourceName;
    }
    
    public int getSourceLine()
    {
        return _sourceLine;
    }
    
    public void setSource( String name, int line )
    {
        _sourceName = name;
        _sourceLine = line;
    }
    
    // ========================================
    
    public void push( MislValue o )
    {
        _stack.push( o );
    }
    
    public MislValue pop()
    {
        return _stack.pop();
    }
    
    public MislValue peek()
    {
        return _stack.peek();
    }
    
    public boolean isEmpty()
    {
        return _stack.isEmpty();
    }
    
}
