package mobtalkerscript.mts.v2.value;

import java.util.*;

import com.google.common.collect.*;

public final class MtsType
{
    private static final HashMap<String, MtsType> _types = Maps.newHashMap();
    
    // ========================================
    
    public static MtsType forName( String name )
    {
        MtsType type = _types.get( name );
        if ( type == null )
        {
            type = new MtsType( name );
            _types.put( name, type );
        }
        
        return type;
    }
    
    // ========================================
    
    public static final MtsType NIL = forName( "nil" );
    public static final MtsType BOOLEAN = forName( "boolean" );
    public static final MtsType NUMBER = forName( "number" );
    public static final MtsType STRING = forName( "string" );
    public static final MtsType TABLE = forName( "table" );
    public static final MtsType FUNCTION = forName( "function" );
    public static final MtsType VARARGS = forName( "varargs" );
    public static final MtsType OBJECT = forName( "userdata" );
    
    // ========================================
    
    private final String _typeName;
    
    private MtsType( String typeName )
    {
        _typeName = typeName;
    }
    
    public String getName()
    {
        return _typeName;
    }
    
    @Override
    public String toString()
    {
        return _typeName;
    }
}
