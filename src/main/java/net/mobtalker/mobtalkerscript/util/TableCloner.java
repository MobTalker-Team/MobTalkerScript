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
package net.mobtalker.mobtalkerscript.util;

import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;

import java.util.Map;

import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.MtsTable.Entry;

import com.google.common.collect.Maps;

public class TableCloner
{
    private final Map<MtsTable, MtsTable> _cache;
    
    // ========================================
    
    {
        _cache = Maps.newIdentityHashMap();
    }
    
    // ========================================
    
    public MtsValue clone( MtsValue o )
    {
        if ( o.isTable() )
        {
            MtsTable t = o.asTable();
            
            MtsTable clone = _cache.get( t );
            
            if ( clone == null )
            {
                clone = new MtsTable( t.listSize(), t.tableSize() );
                _cache.put( t, clone );
            }
            
            MtsValue k = NIL;
            Entry entry;
            while ( ( entry = t.getNext( k ) ) != null )
            {
                clone.set( clone( k = entry.getKey() ), clone( entry.getValue() ) );
            }
            
            return clone;
        }
        else
        {
            return o;
        }
    }
}
