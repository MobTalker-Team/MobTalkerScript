package mobtalkerscript.misl.v1.value;

import mobtalkerscript.misl.v1.*;

public class MislNumber extends MislValue
{
    
    private static final double _max = Integer.MAX_VALUE;
    private static final double _min = Integer.MIN_VALUE;
    
    public static final MislNumber MAX_VALUE = valueOf( _max );
    public static final MislNumber MIN_VALUE = valueOf( _min );
    
    public static final MislNumber ZERO = valueOf( 0 );
    public static final MislNumber ONE = valueOf( 1 );
    
    // ========================================
    
    public static MislValue parse( MislString mtsStr )
    {
        String s = mtsStr.toJava();
        
        try
        {
            double d = Double.parseDouble( s );
            return valueOf( d );
        }
        catch ( NumberFormatException ex )
        {
            return NIL;
        }
    }
    
    public static MislValue parse( MislBoolean mtsBool )
    {
        boolean b = mtsBool.toJava();
        return b ? ONE : ZERO;
    }
    
    // ========================================
    
    private final double _value;
    
    // ========================================
    
    MislNumber( double value )
    {
        if ( ( value < _min ) || ( _max < value ) )
        {
            throw new ScriptRuntimeException( "Number is out of 2^31 range: %s", value );
        }
        
        _value = value;
    }
    
    // ========================================
    
    public static MislNumber add( MislNumber x, MislNumber y )
    {
        return valueOf( x._value + y._value );
    }
    
    public static MislNumber sub( MislNumber x, MislNumber y )
    {
        return valueOf( x._value - y._value );
    }
    
    public static MislNumber mul( MislNumber x, MislNumber y )
    {
        return valueOf( x._value * y._value );
    }
    
    public static MislNumber div( MislNumber x, MislNumber y )
    {
        return valueOf( x._value / y._value );
    }
    
    public static MislNumber mod( MislNumber x, MislNumber y )
    {
        return valueOf( x._value % y._value );
    }
    
    public MislNumber neg()
    {
        return valueOf( -_value );
    }
    
    public MislNumber incr()
    {
        return valueOf( _value + 1.0D );
    }
    
    public MislNumber decr()
    {
        return valueOf( _value - 1.0D );
    }
    
    // ========================================
    // Casting
    
    @Override
    public MislNumber asNumber()
    {
        return this;
    }
    
    /**
     * Equivalent to a Java typecast to {@link MislNumber}, where the value is floored.
     */
    public MislNumber asIntegerValue()
    {
        if ( isInteger() )
        {
            return this;
        }
        else
        {
            return new MislNumber( Math.floor( _value ) );
        }
    }
    
    // ========================================
    // Conversion
    
    @Override
    public MislString toMtsString()
    {
        return valueOf( toString() );
    }
    
    // ========================================
    // Type checks and equality
    
    @Override
    public boolean isNumber()
    {
        return true;
    }
    
    /**
     * Checks if this number represents an integer.
     */
    public boolean isInteger()
    {
        return !Double.isInfinite( _value ) //
               && !Double.isNaN( _value )
               && ( _value == Math.floor( _value ) );
    }
    
    @Override
    public MislBoolean equal( MislValue x )
    {
        if ( x.isNumber() )
        {
            MislNumber other = x.asNumber();
            return valueOf( _value == other._value );
        }
        else
        {
            return FALSE;
        }
    }
    
    @Override
    public String getTypeName()
    {
        return "number";
    }
    
    // ========================================
    // Java Methods
    
    public double toJava()
    {
        return _value;
    }
    
    @Override
    public String toString()
    {
        if ( isInteger() )
        {
            return Integer.toString( (int) _value );
        }
        else
        {
            return Double.toString( _value );
        }
    }
    
    @Override
    public int hashCode()
    {
        return Double.valueOf( _value ).hashCode();
    }
    
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
        {
            return true;
        }
        
        if ( obj instanceof MislNumber )
        {
            MislNumber other = (MislNumber) obj;
            return _value == other._value;
        }
        else
        {
            return false;
        }
    }
    
    // ========================================
    // Comparable
    
    @Override
    public int compareTo( MislValue o )
    {
        if ( !o.isNumber() )
        {
            return 1;
        }
        
        MislNumber n = o.asNumber();
        return (int) Math.signum( _value - n._value );
    }
    
}
