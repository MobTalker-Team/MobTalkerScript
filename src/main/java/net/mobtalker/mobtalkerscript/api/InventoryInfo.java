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
