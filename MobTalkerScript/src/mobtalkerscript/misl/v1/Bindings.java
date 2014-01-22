package mobtalkerscript.misl.v1;

import java.util.*;

import mobtalkerscript.misl.v1.value.*;

public class Bindings
{
    private final Map<String, MislValue> _bindings;
    
    // ========================================
    
    {
        _bindings = new HashMap<String, MislValue>();
    }
    
    // ========================================
    
    public boolean contains( String key )
    {
        return _bindings.containsKey( key );
    }
    
    public MislValue get( String key )
    {
        MislValue r = _bindings.get( key );
        return r == null ? MislValue.NIL : r;
    }
    
    public MislValue set( String key, MislValue value )
    {
        return _bindings.put( key, value );
    }
}
