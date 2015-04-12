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

import net.mobtalker.mobtalkerscript.v3.value.MtsString;

import com.google.common.collect.Maps;

public enum EquipmentSlot
{
    HEAD( "head", 1 ),
    CHEST( "chest", 2 ),
    LEGS( "legs", 3 ),
    FEET( "feet", 4 ),

    ; // ========================================

    private static final HashMap<String, EquipmentSlot> _values;

    static
    {
        _values = Maps.newHashMapWithExpectedSize( values().length );
        for ( EquipmentSlot value : values() )
        {
            _values.put( value.getName(), value );
        }
    }

    public static EquipmentSlot forName( String name )
    {
        return _values.get( name );
    }

    // ========================================

    private final String _name;
    private final int _id;
    private final MtsString _key;

    private EquipmentSlot( String name, int id )
    {
        _name = name;
        _id = id;
        _key = MtsString.of( name );
    }

    public String getName()
    {
        return _name;
    }

    public int getID()
    {
        return _id;
    }

    public MtsString getKey()
    {
        return _key;
    }
}
