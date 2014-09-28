package net.mobtalker.mobtalkerscript.api;

import java.util.HashMap;

import com.google.common.collect.Maps;

public enum EntityReaction
{
    NEUTRAL( "neutral" ),
    HOSTILE( "hostile" ),
    FRIENDLY( "friendly" ),
    SCARED( "scared" ),
    
    ; // ========================================
    
    private static final HashMap<String, EntityReaction> _values;
    
    static
    {
        _values = Maps.newHashMapWithExpectedSize( values().length );
        for ( EntityReaction value : values() )
        {
            _values.put( value.getName(), value );
        }
    }
    
    public static EntityReaction forName( String name )
    {
        return _values.get( name );
    }
    
    // ========================================
    
    private final String _name;
    
    private EntityReaction( String name )
    {
        _name = name;
    }
    
    public String getName()
    {
        return _name;
    }
}
