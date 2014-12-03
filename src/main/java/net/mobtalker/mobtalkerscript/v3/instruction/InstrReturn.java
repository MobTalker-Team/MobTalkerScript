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

import static net.mobtalker.mobtalkerscript.v3.value.MtsValue.*;

import java.util.*;

import net.mobtalker.mobtalkerscript.v3.MtsFrame;
import net.mobtalker.mobtalkerscript.v3.value.*;

import com.google.common.collect.Lists;

public final class InstrReturn extends MtsInstruction
{
    private final int _count;
    
    // ========================================
    
    /* package */InstrReturn( int count )
    {
        _count = count;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        if ( _count == 0 )
        {
            frame.push( EMPTY_VARARGS );
        }
        else if ( frame.peek() instanceof MtsTailcall )
        {
            return;
        }
        else
        {
            ArrayList<MtsValue> values = new ArrayList<>( _count );
            
            { // Unpack first (last) return value
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
            
            for ( int i = 1; i < _count; i++ )
            {
                values.add( frame.pop() );
            }
            
            if ( values.size() > 1 )
            {
                frame.push( MtsVarArgs.of( Lists.reverse( values ) ) );
            }
            else if ( values.size() == 1 )
            {
                frame.push( MtsVarArgs.of( values.get( 0 ) ) );
            }
            else
            {
                frame.push( EMPTY_VARARGS );
            }
        }
    }
    
    private static void unpackVarargs( MtsVarArgs varargs, List<MtsValue> list )
    {
        int count = varargs.count();
        for ( int i = count - 1; i >= 0; --i )
        {
            list.add( varargs.get( i ) );
        }
    }
    
    @Override
    public boolean exits()
    {
        return true;
    }
    
    @Override
    public int stackSizeChange()
    {
        return 1 - _count;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "RETURN " + _count;
    }
}
