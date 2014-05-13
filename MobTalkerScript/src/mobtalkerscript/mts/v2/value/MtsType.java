package mobtalkerscript.mts.v2.value;

public final class MtsType
{
    public static final MtsType NIL = new MtsType( "nil" );
    public static final MtsType BOOLEAN = new MtsType( "boolean" );
    public static final MtsType NUMBER = new MtsType( "number" );
    public static final MtsType STRING = new MtsType( "string" );
    public static final MtsType TABLE = new MtsType( "table" );
    public static final MtsType FUNCTION = new MtsType( "function" );
    public static final MtsType VARARGS = new MtsType( "varargs" );
    public static final MtsType OBJECT = new MtsType( "userdata" );
    
    // ========================================
    
    private final String _typeName;
    
    public MtsType( String typeName )
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
