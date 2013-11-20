package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.util.*;

/**
 * Represents a single instruction executed by the engine.
 * <p>
 * Instructions basicly represent a double-linked list, they store references to their previous and follow-up instruction. <br>
 * It is important to note that some instructions will only return a correct next instruction after they have been executed and
 * garantuee it only once per execution.
 * 
 * @author Chimaine
 */
public abstract class MislInstruction
{
    protected MislInstruction _prev;
    protected MislInstruction _next;
    
    // ========================================
    
    public MislInstruction getPrev()
    {
        return _prev;
    }
    
    public MislInstruction getNext()
    {
        return _next;
    }
    
    // ========================================
    
    /* package */void setPrev( MislInstruction prev )
    {
        _prev = prev;
    }
    
    /* package */void setNext( MislInstruction next )
    {
        _next = next;
    }
    
    // ========================================
    
    public abstract void execute( Stack<MislFrame> frameStack, ScriptContext context );
    
    // ========================================
    
    public boolean pausesExecution()
    {
        return false;
    }
    
    // ========================================
    
    @Override
    public abstract String toString();
    
    @Override
    protected final Object clone() throws CloneNotSupportedException
    {
        throw new CloneNotSupportedException();
    }
}
