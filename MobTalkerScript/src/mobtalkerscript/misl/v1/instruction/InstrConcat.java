package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrConcat extends AbstractStackInstruction
{
    private final int _count;
    
    // ========================================
    
    public InstrConcat( int count )
    {
        _count = count;
    }
    
    // ========================================
    
    @Override
    protected void doExecute( Stack<MislValue> stack, ScriptContext context )
    {
        MislString[] strs = new MislString[_count];
        
        for ( int i = ( _count - 1 ); i >= 0; i-- )
        {
            strs[i] = stack.pop().toMtsString();
        }
        
        stack.push( MislString.concat( strs ) );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return String.format( "%1$-10s %2$s", "CONCAT", _count );
    }
}
