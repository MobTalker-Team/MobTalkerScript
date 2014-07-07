package net.mobtalker.mobtalkerscript.v2.instruction;

import net.mobtalker.mobtalkerscript.v2.value.*;

/**
 * Jump instruction that only jumps if the top of the stack is not true.
 */
public final class InstrNForLoop extends MtsJumpInstruction
{
    private final int _index;
    
    // ========================================
    
    /* package */InstrNForLoop( int index )
    {
        super();
        _index = index;
    }
    
    /* package */InstrNForLoop( int index, int offset )
    {
        super( offset );
        _index = index;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        FrameValue loopVar = frame.getLocal( _index );
        
        MtsNumber loopVal = loopVar.get().asNumber();
        MtsNumber limitVal = frame.getLocal( _index + 1 ).get().asNumber();
        MtsNumber stepVal = frame.getLocal( _index + 2 ).get().asNumber();
        
        loopVal = loopVal.add( stepVal );
        
        if ( stepVal.isPositive() )
        {
            if ( loopVal.compareTo( limitVal ) <= 0 )
            {
                loopVar.set( loopVal );
                return;
            }
        }
        else
        {
            if ( loopVal.compareTo( limitVal ) >= 0 )
            {
                loopVar.set( loopVal );
                return;
            }
        }
        
        super.execute( frame );
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
        return String.format( "NFORLOOP %s %s", _index, getDistance() );
    }
}
