package net.mobtalker.mobtalkerscript.api;

import java.util.HashMap;

import com.google.common.collect.Maps;

public enum HumidityCategory
{
    ARID( "arid" ),
    NORMAL( "normal" ),
    DAMP( "damp" ),
    
    ; // ========================================
    
    private static final HashMap<String, HumidityCategory> _values;
    
    static
    {
        _values = Maps.newHashMapWithExpectedSize( values().length );
        for ( HumidityCategory value : values() )
        {
            _values.put( value.getName(), value );
        }
    }
    
    public static HumidityCategory forName( String name )
    {
        return _values.get( name );
    }
    
    // ========================================
    
    private final String _name;
    
    private HumidityCategory( String name )
    {
        _name = name;
    }
    
    public String getName()
    {
        return _name;
    }
}
