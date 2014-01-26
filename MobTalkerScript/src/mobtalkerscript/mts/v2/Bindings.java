package mobtalkerscript.mts.v2;

import java.util.*;

import mobtalkerscript.mts.v2.value.*;

public class Bindings
{
    private final Map<String, MtsValue> _bindings;
    
    // ========================================
    
    {
        _bindings = new HashMap<String, MtsValue>();
    }
    
    // ========================================
    
    public boolean contains( String key )
    {
        return _bindings.containsKey( key );
    }
    
    public MtsValue get( String key )
    {
        MtsValue r = _bindings.get( key );
        return r == null ? MtsValue.NIL : r;
    }
    
    public MtsValue set( String key, MtsValue value )
    {
        return _bindings.put( key, value );
    }
}
