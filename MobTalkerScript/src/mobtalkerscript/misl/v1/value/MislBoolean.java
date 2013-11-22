package mobtalkerscript.misl.v1.value;

public class MislBoolean extends MislValue
{
    
    public static MislBoolean parse( MislString s )
    {
        boolean b = Boolean.parseBoolean( s.toJava() );
        return valueOf( b );
    }
    
    public static MislBoolean parse( MislNumber n )
    {
        int i = (int) n.toJava();
        return valueOf( i != 0 );
    }
    
    public static boolean isTrue( MislValue v )
    {
        return ( v != FALSE ) && ( v != NIL );
    }
    
    // ========================================
    
    private final boolean _value;
    private final Boolean _obj;
    
    private final String _string;
    private final MislString _stringValue;
    
    // ========================================
    
    MislBoolean( boolean value )
    {
        _value = value;
        _obj = Boolean.valueOf( value );
        
        _string = Boolean.toString( value );
        _stringValue = valueOf( _string );
    }
    
    // ========================================
    
    public static MislBoolean or( MislBoolean x, MislBoolean y )
    {
        return valueOf( x._value || y._value );
    }
    
    public static MislBoolean and( MislBoolean x, MislBoolean y )
    {
        return valueOf( x._value && y._value );
    }
    
    public MislBoolean not()
    {
        return valueOf( !_value );
    }
    
    // ========================================
    
    @Override
    public MislBoolean asBoolean()
    {
        return this;
    }
    
    // ========================================
    
    @Override
    public MislString toMtsString()
    {
        return _stringValue;
    }
    
    // ========================================
    
    @Override
    public boolean isBoolean()
    {
        return true;
    }
    
    @Override
    public String getTypeName()
    {
        return "boolean";
    }
    
    @Override
    public MislBoolean equal( MislValue x )
    {
        return valueOf( this == x );
    }
    
    // ========================================
    
    public boolean toJava()
    {
        return _value;
    }
    
    @Override
    public String toString()
    {
        return _string;
    }
    
    @Override
    public int hashCode()
    {
        return _obj.hashCode();
    }
    
    @Override
    public boolean equals( Object o )
    {
        // Since we can rely on the fact that there are exactly one object for true and false,
        // we just have to check the references.
        return o == this;
    }
    
}
