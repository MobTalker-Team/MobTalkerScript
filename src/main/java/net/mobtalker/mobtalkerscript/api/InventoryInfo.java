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

import java.util.*;

import com.google.common.collect.Maps;

public class InventoryInfo
{
    private final HashMap<ItemInfo, Integer> _items;
    
    // ========================================
    
    public InventoryInfo()
    {
        _items = Maps.newHashMapWithExpectedSize( 32 );
    }
    
    // ========================================
    
    public int count()
    {
        return _items.size();
    }
    
    // ========================================
    
    public void add( ItemInfo item, int count )
    {
        _items.put( item, count );
    }
    
    public int getAmount( ItemInfo item )
    {
        Integer count = _items.get( item );
        return count == null ? 0 : count.intValue();
    }
    
    // ========================================
    
    public Set<ItemInfo> getItems()
    {
        return _items.keySet();
    }
}
