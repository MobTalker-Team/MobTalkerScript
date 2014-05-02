package mobtalkerscript.mts.v2.value;

public class MtsBoolean extends MtsValue
{
    
    public static MtsBoolean parse( MtsString s )
    {
        boolean b = Boolean.parseBoolean( s.toJava() );
        return valueOf( b );
    }
    
    public static MtsBoolean parse( MtsNumber n )
    {
        int i = (int) n.toJava();
        return valueOf( i != 0 );
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
    
    MtsBoolean( boolean value )
    {
        _value = value;
        _obj = Boolean.valueOf( value );
        
        _string = Boolean.toString( value );
        _stringValue = valueOf( _string );
    }
    
    // ========================================
    
    public static MtsBoolean or( MtsBoolean x, MtsBoolean y )
    {
        return valueOf( x._value || y._value );
    }
    
    public static MtsBoolean and( MtsBoolean x, MtsBoolean y )
    {
        return valueOf( x._value && y._value );
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
    public MtsString toStringMts()
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
    public MtsBoolean equalsMts( MtsValue x )
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
