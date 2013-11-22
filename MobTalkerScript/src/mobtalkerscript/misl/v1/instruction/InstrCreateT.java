package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrCreateT extends AbstractStackInstruction
{
    
    private final int _size;
    
    // ========================================
    
    public InstrCreateT( int size )
    {
        _size = size;
    }
    
    // ========================================
    
    @Override
    protected void doExecute( Stack<MislValue> stack, ScriptContext context )
    {
        MislTable table = new MislTable( _size );
        stack.push( table );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return String.format( "%1$-10s %2$s", "CREATE_T", _size );
    }
    
}
