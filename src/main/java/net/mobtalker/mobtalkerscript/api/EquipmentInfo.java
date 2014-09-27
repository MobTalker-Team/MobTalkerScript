package net.mobtalker.mobtalkerscript.api;

import java.util.*;

import com.google.common.collect.Maps;

public class EquipmentInfo
{
    private final EnumMap<EquipmentSlot, ItemInfo> _items;
    
    // ========================================
    
    public EquipmentInfo()
    {
        _items = Maps.newEnumMap( EquipmentSlot.class );
    }
    
    // ========================================
    
    public int count()
    {
        return _items.size();
    }
    
    // ========================================
    
    public void add( EquipmentSlot slot, ItemInfo item )
    {
        _items.put( slot, item );
    }
    
    public ItemInfo getItem( EquipmentSlot slot )
    {
        return _items.get( slot );
    }
    
    // ========================================
    
    public Set<EquipmentSlot> getSlots()
    {
        return _items.keySet();
    }
}
