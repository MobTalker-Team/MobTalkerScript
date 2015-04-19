/*
 * Copyright (C) 2013-2015 Chimaine
 * 
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mobtalker.mobtalkerscript.v3.value;

import net.mobtalker.mobtalkerscript.v3.MtsLengthException;

public final class MtsNumber extends MtsValue
{
    public static final MtsNumber Zero;
    public static final MtsNumber One;
    
    public static final MtsNumber NaN = new MtsNumber( Double.NaN );
    public static final MtsNumber PositiveInfinity = new MtsNumber( Double.POSITIVE_INFINITY );
    public static final MtsNumber NegativeInfinity = new MtsNumber( Double.NEGATIVE_INFINITY );
    
    // ========================================
    
    private static final MtsNumber[] CACHE;
    
    static
    {
        CACHE = new MtsNumber[256];
        for ( int i = 0; i < 256; i++ )
            CACHE[i] = new MtsNumber( i - 127 );
        
        Zero = CACHE[127];
        One = CACHE[128];
    }
    
    // ========================================
    
    public static MtsNumber of( double value )
    {
        if ( Double.isNaN( value ) )
            return NaN;
        
        int id = (int) value;
        return value == id ? of( id ) : new MtsNumber( value );
    }
    
    public static MtsNumber of( int value )
    {
        if ( ( -127 <= value ) && ( value <= 128 ) )
            return CACHE[value + 127];
        
        return new MtsNumber( value );
    }
    
    // ========================================
    
    public static MtsNumber parse( MtsString s )
    {
        return parse( s.toJava() );
    }
    
    public static MtsNumber parse( MtsBoolean b )
    {
        return b.isTrue() ? One : Zero;
    }
    
    /**
     * Does not throw an exception if <code>s</code> cannot be parsed, but returns {@link #NaN} instead.
     */
    public static MtsNumber parse( String s )
    {
        try
        {
            return parseStrict( s );
        }
        catch ( NumberFormatException ex )
        {
            return NaN;
        }
    }
    
    public static MtsNumber parseStrict( String s ) throws NumberFormatException
    {
        if ( s.indexOf( '.' ) < 0 )
            return of( parseJavaInteger( s ) );
        else
            return of( parseJavaDouble( s ) );
    }
    
    /**
     * Parses an integer according to the MTS rules for integers.
     * <p>
     * Does not allow leading or trailing whitespace.
     */
    public static long parseJavaInteger( String s ) throws NumberFormatException
    {
        int result = 0;
        boolean negative = false;
        int i = 0, len = s.length();
        int limit = -Integer.MAX_VALUE;
        int multmin;
        int digit;
        int radix = 10;
        
        if ( len <= 0 )
            throw new NumberFormatException( s );
        
        char firstChar = s.charAt( 0 );
        if ( firstChar < '0' )
        { // Possible leading "+" or "-"
            if ( firstChar == '-' )
            {
                negative = true;
                limit = Integer.MIN_VALUE;
            }
            else if ( firstChar != '+' )
                throw new NumberFormatException( s );
            
            if ( len == 1 ) // Cannot have lone "+" or "-"
                throw new NumberFormatException( s );
            
            i++;
        }
        
        // 0x or 0X prefix?
        if ( len > 2 )
        {
            if ( ( s.charAt( i ) == '0' ) )
            {
                char x = s.charAt( i + 1 );
                if ( ( x == 'x' ) || ( x == 'X' ) )
                {
                    radix = 16;
                    i += 2;
                }
            }
        }
        
        multmin = limit / radix;
        while ( i < len )
        {
            // Accumulating negatively avoids surprises near MAX_VALUE
            digit = Character.digit( s.charAt( i++ ), radix );
            if ( digit < 0 )
                throw new NumberFormatException( s );
            if ( result < multmin )
                throw new NumberFormatException( s );
            result *= radix;
            if ( result < ( limit + digit ) )
                throw new NumberFormatException( s );
            result -= digit;
        }
        
        return negative ? result : -result;
    }
    
    /**
     * Parses a decimal according to the MTS rules for decimals.
     * <p>
     * Does not allow leading or trailing whitespace.
     */
    public static double parseJavaDouble( String s ) throws NumberFormatException
    {
        int i = 0, len = s.length();
        
        char firstChar = s.charAt( 0 );
        if ( ( firstChar == '-' ) || ( firstChar == '+' ) )
            i++;
        
        // 0x or 0X prefix?
        if ( len > ( 2 + i ) )
        {
            if ( ( s.charAt( i ) == '0' ) )
            {
                char x = s.charAt( i + 1 );
                
                // parseDouble requires the exponent to be present. Append it if it isn't.
                if ( ( ( x == 'x' ) || ( ( x == 'X' ) ) ) && ( ( s.lastIndexOf( 'p' ) < 0 ) && ( s.lastIndexOf( 'P' ) < 0 ) ) )
                    s += "p0";
            }
        }
        
        return Double.parseDouble( s );
    }
    
    // ========================================
    
    public static final double MaxValue = Double.MAX_VALUE;
    public static final double MinValue = Double.MIN_VALUE;
    
    // ========================================
    
    private final double _value;
    private Double _doubleValue;
    
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
    
    @Override
    public MtsNumber getLength()
    {
        throw new MtsLengthException( getType() );
    }
    
    @Override
    public MtsValue unaryMinus()
    {
        return of( -_value );
    }
    
    // ========================================
    
    @Override
    public MtsValue add( MtsValue b )
    {
        return b.addTo( this );
    }
    
    @Override
    protected MtsValue addTo( MtsNumber a )
    {
        return of( a.toJavaDouble() + _value );
    }
    
    @Override
    public MtsValue substract( MtsValue b )
    {
        return b.substractFrom( this );
    }
    
    @Override
    protected MtsNumber substractFrom( MtsNumber a )
    {
        return of( a.toJavaDouble() - _value );
    }
    
    @Override
    public MtsValue multiplyBy( MtsValue b )
    {
        return b.multiplyWith( this );
    }
    
    @Override
    protected MtsValue multiplyWith( MtsNumber a )
    {
        return of( a.toJavaDouble() * _value );
    }
    
    @Override
    public MtsValue divideBy( MtsValue b )
    {
        return b.divideFrom( this );
    }
    
    @Override
    protected MtsValue divideFrom( MtsNumber a )
    {
        return of( a.toJavaDouble() / _value );
    }
    
    @Override
    public MtsValue powerTo( MtsValue b )
    {
        return b.powerOf( this );
    }
    
    @Override
    protected MtsValue powerOf( MtsNumber a )
    {
        return of( Math.pow( a.toJavaDouble(), _value ) );
    }
    
    @Override
    public MtsValue modulo( MtsValue b )
    {
        return b.moduloOf( this );
    }
    
    @Override
    protected MtsValue moduloOf( MtsNumber a )
    {
        return of( a.toJavaDouble() % _value );
    }
    
    // ========================================
    
    @Override
    public MtsString concat( MtsValue b )
    {
        return b.concatTo( toString() );
    }
    
    @Override
    protected MtsString concatTo( String a )
    {
        return MtsString.of( a + toString() );
    }
    
    // ========================================
    
    @Override
    public MtsBoolean isLessThen( MtsValue other )
    {
        return other.isGreaterThen( this );
    }
    
    @Override
    protected MtsBoolean isGreaterThen( MtsNumber other )
    {
        return MtsBoolean.of( _value > other.toJavaDouble() );
    }
    
    @Override
    public MtsBoolean isLessThenOrEqual( MtsValue other )
    {
        return other.isGreaterThenOrEqual( this );
    }
    
    @Override
    protected MtsBoolean isGreaterThenOrEqual( MtsNumber other )
    {
        return MtsBoolean.of( _value >= other.toJavaDouble() );
    }
    
    // ========================================
    
    public boolean isNaN()
    {
        return this == NaN;
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
    public boolean isNumber()
    {
        return true;
    }
    
    @Override
    public boolean isInteger()
    {
        return ( _value == Math.rint( _value ) ) && !Double.isInfinite( _value );
    }
    
    @Override
    public MtsNumber asNumber()
    {
        return this;
    }
    
    // ========================================
    
    @Override
    public MtsString toMtsString()
    {
        return MtsString.of( toString() );
    }
    
    @Override
    public MtsNumber toMtsNumber() throws NumberFormatException
    {
        return this;
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
            return (int) Math.signum( _value - o.asNumber()._value );
        
        return 0;
    }
    
    // ========================================
    
    @Override
    public Double toJava()
    {
        if ( _doubleValue == null )
        {
            _doubleValue = Double.valueOf( _value );
        }
        
        return _doubleValue;
    }
    
    public double toJavaDouble()
    {
        return _value;
    }
    
    public int toJavaInt()
    {
        return (int) _value;
    }
    
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
