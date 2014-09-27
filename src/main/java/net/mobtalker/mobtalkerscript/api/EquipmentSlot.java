package net.mobtalker.mobtalkerscript.api;

import java.util.HashMap;

import com.google.common.collect.Maps;

public enum EquipmentSlot
{
    TOOL( "tool", 0 ),
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
    
    private EquipmentSlot( String name, int id )
    {
        _name = name;
        _id = id;
    }
    
    public String getName()
    {
        return _name;
    }
    
    public int getID()
    {
        return _id;
    }
}
