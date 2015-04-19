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
package net.mobtalker.mobtalkerscript.v3.value;

import java.util.HashMap;

import com.google.common.collect.Maps;

public final class MtsType
{
    private static final HashMap<String, MtsType> _types = Maps.newHashMap();
    
    // ========================================
    
    public static MtsType forName( String name )
    {
        MtsType type = _types.get( name );
        if ( type == null )
        {
            type = new MtsType( name );
            _types.put( name, type );
        }
        
        return type;
    }
    
    // ========================================
    
    public static final MtsType NIL = forName( "nil" );
    public static final MtsType BOOLEAN = forName( "boolean" );
    public static final MtsType NUMBER = forName( "number" );
    public static final MtsType STRING = forName( "string" );
    public static final MtsType TABLE = forName( "table" );
    public static final MtsType FUNCTION = forName( "function" );
    public static final MtsType VARARGS = forName( "varargs" );
    public static final MtsType USERDATA = forName( "userdata" );
    
    // ========================================
    
    private final String _name;
    private MtsTable _metaTable;
    
    // ========================================
    
    private MtsType( String typeName )
    {
        _name = typeName;
    }
    
    private MtsType( String typeName, boolean metaTable )
    {
        _name = typeName;
        _metaTable = new MtsTable( 0, 0 );
    }
    
    // ========================================
    
    public String getName()
    {
        return _name;
    }
    
    // ========================================
    
    public boolean hasMetaTable()
    {
        return _metaTable != null;
    }
    
    public MtsTable getMetaTable()
    {
        return _metaTable;
    }
    
    /**
     * Sets the metatable used by all instances of this type.
     */
    public void setMetaTable( MtsValue table )
    {
        if ( table.isNil() )
            _metaTable = null;
        else if ( table.isTable() )
            _metaTable = table.asTable();
        else
            throw new IllegalArgumentException( "metatable must be a table (was " + table.getType() + ")" );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return _name;
    }
}
