package mobtalkerscript.misl.v1.value;

public class MislString extends MislValue
{
    
    public static MislString concat( MislValue... values )
    {
        if ( ( values == null ) || ( values.length == 0 ) )
        {
            return EMPTY_STRING;
        }
        else if ( values.length == 1 )
        {
            return values[0].toStringMts();
        }
        else if ( values.length > 2 )
        {
            StringBuilder sb = new StringBuilder();
            for ( MislValue value : values )
            {
                sb.append( value.toStringMts().toJava() );
            }
            return valueOf( sb.toString() );
        }
        else
        {
            return values[0].toStringMts().concat( values[1] );
        }
    }
    
    // ========================================
    
    private final String _value;
    
    // ========================================
    
    MislString( String value )
    {
        _value = value;
    }
    
    // ========================================
    
    public MislString concat( MislValue x )
    {
        return valueOf( _value.concat( x.toStringMts().toJava() ) );
    }
    
    public MislNumber getLength()
    {
        return valueOf( _value.length() );
    }
    
    // ========================================
    
    @Override
    public boolean isString()
    {
        return true;
    }
    
    @Override
    public MislString asString()
    {
        return this;
    }
    
    @Override
    public String getTypeName()
    {
        return TYPENAME_STRING;
    }
    
    // ========================================
    
    public String toJava()
    {
        return _value;
    }
    
    @Override
    public MislString toStringMts()
    {
        return this;
    }
    
    @Override
    public String toString()
    {
        return "\"" + _value + "\"";
    }
    
    @Override
    public int hashCode()
    {
        return _value.hashCode();
    }
    
    @Override
    public boolean equals( Object obj )
    {
        if ( obj == this )
            return true;
        if ( !( obj instanceof MislString ) )
            return false;
        
        return ( (MislString) obj ).toJava().equals( _value );
    }
    
    @Override
    public int compareTo( MislValue o )
    {
        if ( !o.isString() )
            return 0;
        
        return _value.compareTo( o.asString().toJava() );
    }
    
}
