/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.instruction;

import java.io.*;

import net.mobtalker.mobtalkerscript.v3.*;
import net.mobtalker.mobtalkerscript.v3.value.*;

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
        
        MtsValue loopVal = loopVar.get();
        MtsValue limitVal = frame.getLocal( _index + 1 ).get();
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
        return Instructions.NFL_NAME + " " + _index + " " + getDistance();
    }
    
    @Override
    public String toString( MtsFunctionPrototype proto )
    {
        return toString() + " (" + proto.getLocalDescription( _index ).getName() + ")";
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.NFL );
        stream.writeByte( _index );
        stream.writeShort( getDistance() );
    }
}
