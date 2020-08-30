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
public final class InstrGForLoop extends MtsJumpInstruction
{
    private final int _index;
    private final int _values;
    
    // ========================================
    
    /* package */InstrGForLoop( int index, int values )
    {
        super();
        _index = index;
        _values = values;
    }
    
    /* package */InstrGForLoop( int index, int values, int offset )
    {
        super( offset );
        _index = index;
        _values = values;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsValue iterFunc = frame.getLocal( _index ).get();
        MtsValue state = frame.getLocal( _index + 1 ).get();
        
        FrameValue indexVar = frame.getLocal( _index + 2 );
        MtsValue index = indexVar.get();
        
        MtsVarargs results = iterFunc.call( state, index ).asVarArgs();
        
        MtsValue nextIndex = results.get( 0 );
        if ( !nextIndex.isNil() )
        {
            indexVar.set( nextIndex );
            for ( int i = 0; i < _values; i++ )
            {
                frame.getLocal( _index + 3 + i ).set( results.get( i ) );
            }
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
        return Instructions.GFL_NAME + " " + _index + " " + _values + " " + getDistance();
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.GFL );
        stream.writeByte( _index );
        stream.writeByte( _values );
        stream.writeByte( getDistance() );
    }
}
