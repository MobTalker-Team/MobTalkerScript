package mobtalkerscript.v2.value;

import mobtalkerscript.v2.*;

import com.google.common.math.*;

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
    
    public static final MtsNumber NaN = of( Double.NaN );
    
    // ========================================
    
    public static MtsNumber of( double value )
    {
        return new MtsNumber( value );
    }
    
    public static MtsNumber of( int value )
    {
        return new MtsNumber( value );
    }
    
    public static MtsValue parse( MtsString str )
    {
        String s = str.asJavaString();
        
        MtsValue n;
        try
        {
            double d = Double.parseDouble( s );
            n = of( d );
        }
        catch ( NumberFormatException ex )
        {
            n = NIL;
        }
        
        return n;
    }
    
    public static MtsNumber parse( MtsBoolean b )
    {
        return b.toJavaValue() ? ONE : ZERO;
    }
    
    // ========================================
    
    public static final double MAX_VALUE = Double.MAX_VALUE;
    public static final double MIN_VALUE = Double.MIN_VALUE;
    
    // ========================================
    
    private final double _value;
    
    // ========================================
    
    /* package */MtsNumber( double value )
    {
        _value = value;
    }
    
    /* package */MtsNumber( int value )
    {
        _value = value;
    }
    
    // ========================================
    
    public MtsNumber add( MtsNumber other )
    {
        return of( this._value + other._value );
    }
    
    public MtsNumber sub( MtsNumber other )
    {
        return of( this._value - other._value );
    }
    
    public MtsNumber mul( MtsNumber other )
    {
        return of( this._value * other._value );
    }
    
    public MtsNumber div( MtsNumber other )
    {
        return of( this._value / other._value );
    }
    
    public MtsNumber mod( MtsNumber other )
    {
        return of( this._value % other._value );
    }
    
    public MtsNumber pow( MtsNumber other )
    {
        return of( Math.pow( this._value, other._value ) );
    }
    
    public MtsNumber neg()
    {
        return of( -_value );
    }
    
    public MtsNumber incr()
    {
        return of( _value + 1.0D );
    }
    
    public MtsNumber decr()
    {
        return of( _value - 1.0D );
    }
    
    // ========================================
    
    public MtsNumber floor()
    {
        return of( Math.floor( _value ) );
    }
    
    public MtsNumber ceil()
    {
        return of( Math.ceil( _value ) );
    }
    
    public MtsNumber round()
    {
        return of( Math.round( _value ) );
    }
    
    // ========================================
    
    public boolean isNaN()
    {
        return Double.isNaN( _value );
    }
    
    public boolean isInfinite()
    {
        return Double.isInfinite( _value );
    }
    
    /**
     * Determines if this number is greater than zero.
     */
    public boolean isPositive()
    {
        return _value > 0.0D;
    }
    
    // ========================================
    
    @Override
    protected MtsBoolean doIsLess( MtsValue other )
    {
        if ( !other.isNumber() )
            throw new ScriptRuntimeException( "attempt to compare %s with %s", getType(), other.getType() );
        
        return valueOf( _value < other.asNumber().asJavaDouble() );
    }
    
    @Override
    protected MtsBoolean doIsLessOrEqual( MtsValue other )
    {
        if ( !other.isNumber() )
            throw new ScriptRuntimeException( "attempt to compare %s with %s", getType(), other.getType() );
        
        return valueOf( _value <= other.asNumber().asJavaDouble() );
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
        return DoubleMath.isMathematicalInteger( _value );
    }
    
    @Override
    public MtsNumber asNumber()
    {
        return this;
    }
    
    public double asJavaDouble()
    {
        return _value;
    }
    
    public int asJavaInt()
    {
        return (int) _value;
    }
    
    // ========================================
    
    @Override
    public MtsString toMtsString()
    {
        return valueOf( toString() );
    }
    
    // ========================================
    
    @Override
    public MtsType getType()
    {
        return MtsType.NUMBER;
    }
    
    // ========================================
    
    @Override
    public int compareTo( MtsValue o )
    {
        if ( o.isNumber() || o.isString() )
            return (int) Math.rint( _value - o.asNumber()._value );
        
        return 0;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        if ( isInteger() )
            return Integer.toString( (int) _value );
        else
            return Double.toString( _value );
    }
    
    @Override
    public int hashCode()
    {
        // This solution is taken from C# and at least avoids using the following
        // Double.valueOf( _value ).hashCode();
        // which is equivalent to
        // Long.valueOf( Double.doubleToLongBits( _value ) ).hashCode();
        
        long bits = Double.doubleToLongBits( _value );
        return (int) ( bits ^ ( bits >> 32 ) );
    }
    
    @Override
    public boolean equals( Object obj )
    {
        if ( obj == null )
            return false;
        if ( this == obj )
            return true;
        if ( !( obj instanceof MtsNumber ) )
            return false;
        
        return compareTo( (MtsNumber) obj ) == 0;
    }
}
