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
