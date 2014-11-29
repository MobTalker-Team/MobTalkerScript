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
package net.mobtalker.mobtalkerscript.v3;

public abstract class VariableDescription
{
    private final String _name;
    private final int _index;
    
    // ========================================
    
    public VariableDescription( String name, int index )
    {
        _name = name;
        _index = index;
    }
    
    // ========================================
    
    public String getName()
    {
        return _name;
    }
    
    public int getIndex()
    {
        return _index;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return _name;
    }
}
