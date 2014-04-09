package mobtalkerscript.mts.v2.value;

import static com.google.common.base.Preconditions.*;

import java.util.*;

import com.google.common.collect.*;

public class MtsString extends MtsValue
{
    private static final HashMap<String, MtsString> CACHE = Maps.newHashMapWithExpectedSize( 2 << 10 );
    
    public static MtsString of( String s )
    {
        checkNotNull( s );
        
        if ( s.length() == 0 )
            return EMPTY_STRING;
        
        MtsString result = CACHE.get( s );
        
        if ( ( result == null ) )
        {
            if ( s.length() <= 32 )
            {
                result = new MtsString( s );
                CACHE.put( s, result );
            }
            else
            {
                return new MtsString( s );
            }
        }
        
        return result;
    }
    
    // ========================================
    
    public static MtsString concat( MtsValue... values )
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
            for ( MtsValue value : values )
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
    
    public static MtsString concat( List<MtsValue> values )
    {
        if ( ( values == null ) || values.isEmpty() )
            return EMPTY_STRING;
        
        int elements = values.size();
        if ( elements == 1 )
        {
            return values.get( 0 ).toStringMts();
        }
        else if ( elements > 2 )
        {
            StringBuilder sb = new StringBuilder();
            for ( MtsValue value : values )
            {
                sb.append( value.toStringMts().toJava() );
            }
            return valueOf( sb.toString() );
        }
        else
        {
            return values.get( 0 ).toStringMts().concat( values.get( 1 ) );
        }
    }
    
    // ========================================
    
    private final String _value;
    
    // ========================================
    
    /* package */MtsString( String value )
    {
        _value = value;
    }
    
    // ========================================
    
    public MtsString concat( MtsValue x )
    {
        return valueOf( _value.concat( x.toStringMts().toJava() ) );
    }
    
    public MtsNumber getLength()
    {
        return valueOf( _value.length() );
    }
    
    public MtsString intern()
    {
        if ( !CACHE.containsKey( _value ) )
        {
            CACHE.put( _value, this );
            return this;
        }
        else
        {
            return this;
        }
    }
    
    // ========================================
    
    @Override
    public boolean isString()
    {
        return true;
    }
    
    @Override
    public MtsString asString()
    {
        return this;
    }
    
    @Override
    public MtsNumber asNumber()
    {
        return valueOf( Double.valueOf( _value ) );
    }
    
    @Override
    public MtsType getType()
    {
        return MtsType.STRING;
    }
    
    // ========================================
    
    public String toJava()
    {
        return _value;
    }
    
    @Override
    public MtsString toStringMts()
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
        if ( !( obj instanceof MtsString ) )
            return false;
        
        return ( (MtsString) obj ).toJava().equals( _value );
    }
    
    @Override
    public int compareTo( MtsValue o )
    {
        if ( !o.isString() )
            return 0;
        
        return _value.compareTo( o.asString().toJava() );
    }
    
}
