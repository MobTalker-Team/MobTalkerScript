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
    public static final MtsType USERDATA = forName( "userdata" );
    
    // ========================================
    
    private final String _name;
    private MtsTable _metaTable;
    
    // ========================================
    
    private MtsType( String typeName )
    {
        _name = typeName;
    }
    
    private MtsType( String typeName, boolean metaTable )
    {
        _name = typeName;
        _metaTable = new MtsTable( 0, 0 );
    }
    
    // ========================================
    
    public String getName()
    {
        return _name;
    }
    
    // ========================================
    
    public boolean hasMetaTable()
    {
        return _metaTable != null;
    }
    
    public MtsTable getMetaTable()
    {
        return _metaTable;
    }
    
    /**
     * Sets the metatable used by all instances of this type.
     */
    public void setMetaTable( MtsValue table )
    {
        if ( table.isNil() )
            _metaTable = null;
        else if ( table.isTable() )
            _metaTable = table.asTable();
        else
            throw new IllegalArgumentException( "metatable must be a table (was " + table.getType() + ")" );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return _name;
    }
}
