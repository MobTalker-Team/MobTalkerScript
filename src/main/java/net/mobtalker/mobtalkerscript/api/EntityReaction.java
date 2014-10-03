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
package net.mobtalker.mobtalkerscript.api;

import java.util.HashMap;

import com.google.common.collect.Maps;

public enum EntityReaction
{
    NEUTRAL( "neutral" ),
    HOSTILE( "hostile" ),
    FRIENDLY( "friendly" ),
    SCARED( "scared" ),
    
    ; // ========================================
    
    private static final HashMap<String, EntityReaction> _values;
    
    static
    {
        _values = Maps.newHashMapWithExpectedSize( values().length );
        for ( EntityReaction value : values() )
        {
            _values.put( value.getName(), value );
        }
    }
    
    public static EntityReaction forName( String name )
    {
        return _values.get( name );
    }
    
    // ========================================
    
    private final String _name;
    
    private EntityReaction( String name )
    {
        _name = name;
    }
    
    public String getName()
    {
        return _name;
    }
}
