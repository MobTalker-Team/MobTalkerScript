/*
 * Copyright (C) 2013-2014 Chimaine
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mobtalker.mobtalkerscript.v2.instruction;

import net.mobtalker.mobtalkerscript.v2.*;
import net.mobtalker.mobtalkerscript.v2.value.MtsNumber;

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
