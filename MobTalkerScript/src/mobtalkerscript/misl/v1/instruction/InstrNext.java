package mobtalkerscript.misl.v1.instruction;

import java.util.Map.Entry;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.Stack;

/**
 * Implements the "next" iterator function for generic for-loops.
 * <p>
 * Expects two values on the stack: The last key and the table to iterate. The key is pop'ed and the table is left on the stack.
 * <br>
 * Pushes back the result of the iterator function: The key (x2) and the value.
 * <p>
 * 
 * <pre>
 * [ t, k ] -> [ t ] | [ t, k, k, v ]
 * </pre>
 */
public class InstrNext extends AbstractStackInstruction
{
    private MislInstruction _notNull;
    private final InstrLabel _cont;
    
    // ========================================
    
    public InstrNext( InstrLabel cont )
    {
        _cont = cont;
    }
    
    // ========================================
    
    @Override
    void setNext( MislInstruction next )
    {
        super.setNext( next );
        _notNull = next;
    }
    
    // ========================================
    
    @Override
    protected void doExecute( Stack<MislValue> stack, ScriptContext context )
    {
        MislValue ctrl = stack.pop();
        MislTable t = stack.peek().asTable();
        
        Entry<MislValue, MislValue> next = t.getEntryAfter( ctrl );
        
        if ( next == null )
        {
            _next = _cont;
        }
        else
        {
            _next = _notNull;
            stack.push( next.getKey() );
            stack.push( next.getKey() );
            stack.push( next.getValue() );
        }
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "NEXT";
    }
}
