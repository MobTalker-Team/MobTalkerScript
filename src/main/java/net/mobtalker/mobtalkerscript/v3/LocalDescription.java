/*
 * Copyright (C) 2013-2015 Chimaine
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
package net.mobtalker.mobtalkerscript.v3;

/**
 * Debug information about a local variable
 */
public class LocalDescription extends VariableDescription
{
    private final int _start;
    private int _end;
    
    // ========================================
    
    public LocalDescription( String name, int index, int start )
    {
        super( name, index );
        _start = start;
    }
    
    public LocalDescription( String name, int index, int start, int end )
    {
        super( name, index );
        _start = start;
        _end = end;
    }
    
    // ========================================
    
    /**
     * First instruction index where this local is defined
     */
    public int getStart()
    {
        return _start;
    }
    
    /**
     * Last instruction index where this local is defined
     */
    public int getEnd()
    {
        return _end;
    }
    
    public void setEnd( int end )
    {
        _end = end;
    }
}
