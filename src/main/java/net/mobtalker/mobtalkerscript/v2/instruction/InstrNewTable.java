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

import net.mobtalker.mobtalkerscript.v2.MtsFrame;
import net.mobtalker.mobtalkerscript.v2.value.*;

import com.google.common.collect.Lists;

public class InstrNewTable extends MtsInstruction
{
    private final int _nListElements;
    private final int _nHashElements;
    
    // ========================================
    
    /* package */InstrNewTable( int listElements, int hashElements )
    {
        _nListElements = listElements;
        _nHashElements = hashElements;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsTable t = new MtsTable( Math.max( _nListElements, 16 ), Math.max( _nHashElements, 16 ) );
        
        // List elements
        t.add( Lists.reverse( frame.pop( _nListElements ) ) );
        
        // Key-Value pairs
        for ( int i = 0; i < _nHashElements; i++ )
        {
            MtsValue v = frame.pop();
            MtsValue k = frame.pop();
            t.set( k, v, true );
        }
        
        frame.push( t );
    }
    
    @Override
    public int stackSizeChange()
    {
        return 1 - _nListElements - ( _nHashElements * 2 );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "NEWTABLE " + _nListElements + " " + _nHashElements;
    }
}
