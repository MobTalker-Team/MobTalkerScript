package mobtalkerscript.mts.v2.instruction;

import static mobtalkerscript.mts.v2.value.MtsNumber.*;
import mobtalkerscript.mts.v2.value.*;

/**
 * Jump instruction that only jumps if the top of the stack is not true.
 */
public final class InstrForPrep extends MtsInstruction
{
    private final int _loopIndex;
    private final int _limitIndex;
    private final int _stepIndex;
    
    // ========================================
    
    public InstrForPrep( int loopIndex, int limitIndex, int stepIndex )
    {
        _loopIndex = loopIndex;
        _limitIndex = limitIndex;
        _stepIndex = stepIndex;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsNumber stepVal = frame.pop().asNumber();
        MtsNumber limitVal = frame.pop().asNumber();
        MtsNumber loopVal = frame.pop().asNumber();
        
        loopVal = sub( loopVal, stepVal );
        
        frame.getLocal( _loopIndex ).set( loopVal );
        frame.getLocal( _limitIndex ).set( limitVal );
        frame.getLocal( _stepIndex ).set( stepVal );
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
        return String.format( "FORPREP %s %s %s", _loopIndex, _limitIndex, _stepIndex );
    }
}
