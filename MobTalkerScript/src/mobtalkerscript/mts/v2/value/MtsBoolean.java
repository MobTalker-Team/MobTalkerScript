package mobtalkerscript.mts.v2.value;

public class MtsBoolean extends MtsValue
{
    public static MtsBoolean parse( MtsString s )
    {
        boolean b = Boolean.parseBoolean( s.asJavaString() );
        return valueOf( b );
    }
    
    public static MtsBoolean parse( MtsNumber n )
    {
        int i = (int) n.asJavaDouble();
        return valueOf( i != 0.0D );
    }
    
    public static boolean isTrue( MtsValue v )
    {
        return ( v != FALSE ) && ( v != NIL );
    }
    
    // ========================================
    
    private final boolean _value;
    private final Boolean _obj;
    
    private final String _string;
    private final MtsString _stringValue;
    
    // ========================================
    
    /* package */MtsBoolean( boolean value )
    {
        _value = value;
        _obj = Boolean.valueOf( value );
        
        _string = Boolean.toString( value );
        _stringValue = valueOf( _string );
    }
    
    // ========================================
    
    public MtsBoolean or( MtsBoolean other )
    {
        return valueOf( this._value || other._value );
    }
    
    public MtsBoolean and( MtsBoolean other )
    {
        return valueOf( this._value && other._value );
    }
    
    public MtsBoolean not()
    {
        return valueOf( !_value );
    }
    
    // ========================================
    
    @Override
    public MtsBoolean asBoolean()
    {
        return this;
    }
    
    // ========================================
    
    @Override
    public MtsString toMtsString()
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
    public MtsType getType()
    {
        return MtsType.BOOLEAN;
    }
    
    @Override
    public MtsBoolean isMtsEqual( MtsValue x )
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
