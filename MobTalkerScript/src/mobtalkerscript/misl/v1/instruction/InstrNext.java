package mobtalkerscript.misl.v1.instruction;

import java.util.Map.Entry;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;

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
public class InstrNext extends AbstractMislInstruction
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
    protected void doExecute( MislFrame frame, ScriptContext context )
    {
        MislValue ctrl = frame.pop();
        MislTable t = frame.peek().asTable();
        
        Entry<MislValue, MislValue> next = t.getEntryAfter( ctrl );
        
        if ( next == null )
        {
            _next = _cont;
        }
        else
        {
            _next = _notNull;
            frame.push( next.getKey() );
            frame.push( next.getKey() );
            frame.push( next.getValue() );
        }
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "NEXT";
    }
}
