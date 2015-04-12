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
package net.mobtalker.mobtalkerscript.api;

import java.util.HashMap;

import com.google.common.collect.Maps;

public enum TemperatureCategory
{
    ICY( "icy" ),
    COLD( "cold" ),
    NORMAL( "normal" ),
    WARM( "warm" ),
    HOT( "hot" ),
    HELLISH( "hellish" ),

    ; // ========================================

    private static final HashMap<String, TemperatureCategory> _values;

    static
    {
        _values = Maps.newHashMapWithExpectedSize( values().length );
        for ( TemperatureCategory value : values() )
        {
            _values.put( value.getName(), value );
        }
    }

    public static TemperatureCategory forName( String name )
    {
        return _values.get( name );
    }

    // ========================================

    private final String _name;

    private TemperatureCategory( String name )
    {
        _name = name;
    }

    public String getName()
    {
        return _name;
    }
}
