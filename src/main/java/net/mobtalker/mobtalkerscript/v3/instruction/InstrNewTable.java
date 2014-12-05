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
import java.util.*;

import net.mobtalker.mobtalkerscript.v3.MtsFrame;
import net.mobtalker.mobtalkerscript.v3.value.*;

import com.google.common.collect.Lists;

public class InstrNewTable extends MtsInstruction
{
    private final int _nListElements;
    private final int _nMapElements;
    
    // ========================================
    
    /* package */InstrNewTable( int listElements, int mapElements )
    {
        _nListElements = listElements;
        _nMapElements = mapElements;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsTable t = new MtsTable( _nListElements, _nMapElements );
        
        // List elements
        if ( _nListElements > 0 )
        {
            List<MtsValue> values = new ArrayList<>( _nListElements );
            
            { // First value can be varargs
                MtsValue value = frame.pop();
                if ( value.isVarArgs() )
                {
                    unpackVarargs( value.asVarArgs(), values );
                }
                else
                {
                    values.add( value );
                }
            }
            
            for ( int i = 1; i < _nListElements; i++ )
            {
                values.add( frame.pop() );
            }
            
            t.list().addAll( Lists.reverse( values ) );
        }
        
        // Key-Value pairs
        for ( int i = 0; i < _nMapElements; i++ )
        {
            MtsValue v = frame.pop();
            MtsValue k = frame.pop();
            t.map().put( k, v );
        }
        
        frame.push( t );
    }
    
    private static void unpackVarargs( MtsVarargs varargs, List<MtsValue> list )
    {
        int count = varargs.count();
        for ( int i = count - 1; i >= 0; --i )
        {
            list.add( varargs.get( i ) );
        }
    }
    
    @Override
    public int stackSizeChange()
    {
        return 1 - _nListElements - ( _nMapElements * 2 );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return Instructions.NEWTBL_NAME + " " + _nListElements + " " + _nMapElements;
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.NEWTBL );
        stream.writeByte( _nListElements );
        stream.writeByte( _nMapElements );
    }
}
