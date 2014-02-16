package mobtalkerscript.mts.v2.value;

import static com.google.common.base.Preconditions.*;
import mobtalkerscript.mts.v2.*;

public final class MtsNumber extends MtsValue
{
    private static final MtsNumber[] CACHE;
    
    static
    {
        CACHE = new MtsNumber[256];
        
        for ( int i = 0; i < 256; i++ )
            CACHE[i] = new MtsNumber( i - 127 );
    }
    
    // ========================================
    
    private static final double _max = Integer.MAX_VALUE;
    private static final double _min = Integer.MIN_VALUE;
    
    public static final MtsNumber MAX_VALUE = valueOf( _max );
    public static final MtsNumber MIN_VALUE = valueOf( _min );
    
    // ========================================
    
    public static MtsNumber of( double value )
    {
        if ( !isInteger( value ) )
            return new MtsNumber( value );
        
        return of( (int) value );
    }
    
    public static MtsNumber of( int value )
    {
        if ( ( -128 < value ) && ( value <= 128 ) )
            return CACHE[value + 127];
        else
            return new MtsNumber( value );
    }
    
    public static MtsValue parse( MtsString mtsStr )
    {
        String s = mtsStr.toJava();
        
        try
        {
            double d = Double.parseDouble( s );
            return of( d );
        }
        catch ( NumberFormatException ex )
        {
            return NIL;
        }
    }
    
    public static MtsValue parse( MtsBoolean mtsBool )
    {
        boolean b = mtsBool.toJava();
        return b ? ONE : ZERO;
    }
    
    public static boolean isInteger( double value )
    {
        return value == (long) value;
    }
    
    // ========================================
    
    private final double _value;
    
    // ========================================
    
    /* package */MtsNumber( double value )
    {
        checkArgument( !Double.isNaN( value ), "NaN" );
        checkArgument( !Double.isInfinite( value ), "Value is infinite" );
        
        if ( ( value < _min ) || ( _max < value ) )
            throw new ScriptRuntimeException( "Number is out of 2^31 range: %s", value );
        
        _value = value;
    }
    
    // ========================================
    
    public static MtsNumber add( MtsNumber x, MtsNumber y )
    {
        return valueOf( x._value + y._value );
    }
    
    public static MtsNumber sub( MtsNumber x, MtsNumber y )
    {
        return valueOf( x._value - y._value );
    }
    
    public static MtsNumber mul( MtsNumber x, MtsNumber y )
    {
        return valueOf( x._value * y._value );
    }
    
    public static MtsNumber div( MtsNumber x, MtsNumber y )
    {
        return valueOf( x._value / y._value );
    }
    
    public static MtsNumber mod( MtsNumber x, MtsNumber y )
    {
        return valueOf( x._value % y._value );
    }
    
    public static MtsNumber pow( MtsNumber x, MtsNumber y )
    {
        return valueOf( Math.pow( x._value, y._value ) );
    }
    
    public MtsNumber neg()
    {
        return valueOf( -_value );
    }
    
    public MtsNumber incr()
    {
        return valueOf( _value + 1.0D );
    }
    
    public MtsNumber decr()
    {
        return valueOf( _value - 1.0D );
    }
    
    // ========================================
    
    @Override
    public boolean isNumber()
    {
        return true;
    }
    
    @Override
    public boolean isInteger()
    {
        return isInteger( _value );
    }
    
    @Override
    public boolean isDecimal()
    {
        return !isInteger( _value );
    }
    
    public boolean isPositive()
    {
        return 0.0D < _value;
    }
    
    @Override
    public MtsNumber asNumber()
    {
        return this;
    }
    
    /**
     * Equivalent to a Java typecast to {@link MtsNumber}, where the value is floored.
     */
    public MtsNumber asInteger()
    {
        if ( isInteger() )
        {
            return this;
        }
        else
        {
            return new MtsNumber( Math.floor( _value ) );
        }
    }
    
    @Override
    public MtsString asString()
    {
        return valueOf( isInteger() ? Integer.toString( (int) _value ) : Double.toString( _value ) );
    }
    
    @Override
    public MtsType getType()
    {
        return MtsType.NUMBER;
    }
    
    // ========================================
    
    @Override
    public MtsString toStringMts()
    {
        return valueOf( toString() );
    }
    
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
        // This solution is taken from C# and at least avoids using the following
        // Double.valueOf( _value ).hashCode();
        // which is equivalent to
        // Long.valueOf( Double.doubleToLongBits( _value ) ).hashCode();
        
        long bits = Double.doubleToLongBits( _value );
        return ( (int) bits ) ^ ( (int) ( bits >> 32 ) );
    }
    
    @Override
    public boolean equals( Object obj )
    {
        if ( obj == this )
            return true;
        
        if ( !( obj instanceof MtsNumber ) )
            return false;
        
        return ( (MtsNumber) obj ).toJava() == _value;
    }
    
    @Override
    public MtsBoolean equalsMts( MtsValue x )
    {
        if ( x.isNumber() )
        {
            MtsNumber other = x.asNumber();
            return valueOf( _value == other._value );
        }
        else
        {
            return FALSE;
        }
    }
    
    // ========================================
    // Comparable
    
    @Override
    public int compareTo( MtsValue o )
    {
        if ( !o.isNumber() )
            return 0;
        
        return (int) Math.signum( _value - o.asNumber().toJava() );
    }
    
}
