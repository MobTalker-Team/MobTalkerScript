/*
 * Copyright (C) 2013-2014 Chimaine
 * 
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
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
        return String.format( "GFORLOOP %s %s %s", _index, _values, getDistance() );
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeShort( 0x09 );
        stream.writeByte( _index );
        stream.writeByte( _values );
        stream.writeByte( getDistance() );
    }
}
