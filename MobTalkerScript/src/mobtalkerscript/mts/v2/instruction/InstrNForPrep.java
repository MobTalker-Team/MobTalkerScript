package mobtalkerscript.mts.v2.instruction;

import static mobtalkerscript.mts.v2.value.MtsNumber.*;
import mobtalkerscript.mts.v2.value.*;

/**
 * Jump instruction that only jumps if the top of the stack is not true.
 */
public final class InstrNForPrep extends MtsIndexedInstruction
{
    public InstrNForPrep( int i )
    {
        super( i );
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsNumber stepVal = frame.pop().asNumber();
        MtsNumber limitVal = frame.pop().asNumber();
        MtsNumber loopVal = frame.pop().asNumber();
        
        loopVal = sub( loopVal, stepVal );
        
        frame.getLocal( _index ).set( loopVal );
        frame.getLocal( _index + 1 ).set( limitVal );
        frame.getLocal( _index + 2 ).set( stepVal );
    }
    
    @Override
    public int stackSizeChange()
    {
        return -3;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "NFORPREP " + _index;
    }
}
