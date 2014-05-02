package mobtalkerscript.mts.v2.value;

public enum MtsType
{
    NIL( "nil" ), //
    BOOLEAN( "boolean" ),
    NUMBER( "number" ),
    STRING( "string" ),
    TABLE( "table" ),
    FUNCTION( "function" ),
    VARARGS( "varargs" ),
    OBJECT( "object" );
    
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
