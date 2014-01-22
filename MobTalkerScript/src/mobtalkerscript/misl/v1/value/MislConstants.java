package mobtalkerscript.misl.v1.value;

public interface MislConstants
{
    public static final String TYPENAME_NIL = "nil";
    public static final String TYPENAME_NUMBER = "number";
    public static final String TYPENAME_BOOLEAN = "boolean";
    public static final String TYPENAME_STRING = "string";
    public static final String TYPENAME_TABLE = "table";
    public static final String TYPENAME_FUNCTION = "function";
    public static final String TYPENAME_JOBJECT = "userdata";
    public static final String TYPENAME_ARRAY = "array";
    
    public static final MislNil NIL = new MislNil();
    
    public static final MislNumber ZERO = new MislNumber( 0 );
    public static final MislNumber ONE = new MislNumber( 1 );
    
    public static final MislBoolean TRUE = new MislBoolean( true );
    public static final MislBoolean FALSE = new MislBoolean( false );
    
    public static final MislString EMPTY_STRING = new MislString( "" );
    
    public static final MislArray EMPTY_ARRAY = new MislArray();
}
