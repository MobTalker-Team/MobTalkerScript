package mobtalkerscript.mts.v2.instruction;

import static mobtalkerscript.mts.v2.value.MtsNumber.*;
import mobtalkerscript.mts.v2.value.*;

/**
 * Jump instruction that only jumps if the top of the stack is not true.
 */
public final class InstrForLoop extends MtsJumpInstruction
{
    private final int _loopIndex;
    private final int _limitIndex;
    private final int _stepIndex;
    
    // ========================================
    
    public InstrForLoop( int loopIndex, int limitIndex, int stepIndex )
    {
        super();
        _loopIndex = loopIndex;
        _limitIndex = limitIndex;
        _stepIndex = stepIndex;
    }
    
    public InstrForLoop( int loopIndex, int limitIndex, int stepIndex, int offset )
    {
        super( offset );
        _loopIndex = loopIndex;
        _limitIndex = limitIndex;
        _stepIndex = stepIndex;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        FrameValue loopVar = frame.getLocal( _loopIndex );
        
        MtsNumber loopVal = loopVar.get().asNumber();
        MtsNumber limitVal = frame.getLocal( _limitIndex ).get().asNumber();
        MtsNumber stepVal = frame.getLocal( _stepIndex ).get().asNumber();
        
        loopVal = add( loopVal, stepVal );
        
        if ( stepVal.isPositive() && ( loopVal.compareTo( limitVal ) <= 0 ) )
        {
            loopVar.set( loopVal );
        }
        else if ( loopVal.compareTo( limitVal ) >= 0 )
        {
            loopVar.set( loopVal );
        }
        else
        {
            super.execute( frame );
        }
    }
    
    @Override
    public int stackSizeChange()
    {
        return 0;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return String.format( "FORLOOP %s %s %s %s", _loopIndex, _limitIndex, _stepIndex, getDistance() );
    }
}
