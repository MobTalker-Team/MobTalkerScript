/*
 * Copyright (C) 2013-2014 Chimaine
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
package net.mobtalker.mobtalkerscript.v2.value;

import java.util.regex.Pattern;

import net.mobtalker.mobtalkerscript.v2.ScriptRuntimeException;

import org.apache.commons.lang3.StringUtils;

import com.google.common.math.DoubleMath;

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
    
    public static MtsNumber parse( MtsString s ) throws NumberFormatException
    {
        return parse( s.toJava() );
    }
    
    public static MtsNumber parse( MtsBoolean b )
    {
        return b.toJavaValue() ? ONE : ZERO;
    }
    
    private static final Pattern _hexNumPattern = Pattern.compile( "^[+-]?0x", Pattern.CASE_INSENSITIVE );
    
    public static MtsNumber parse( String s ) throws NumberFormatException
    {
        String input = s.trim();
        if ( _hexNumPattern.matcher( input ).find()
             && ( StringUtils.lastIndexOfIgnoreCase( input, "p" ) < 0 ) )
        {
            input = input + "p0";
        }
        
        try
        {
            double d = Double.parseDouble( input );
            MtsNumber result = valueOf( d );
            
            System.out.println( d + ": " + result );
            
            return result;
        }
        catch ( NumberFormatException ex )
        {
            throw new NumberFormatException( s );
        }
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
        return of( _value + other._value );
    }
    
    public MtsNumber sub( MtsNumber other )
    {
        return of( _value - other._value );
    }
    
    public MtsNumber mul( MtsNumber other )
    {
        return of( _value * other._value );
    }
    
    public MtsNumber div( MtsNumber other )
    {
        return of( _value / other._value );
    }
    
    public MtsNumber mod( MtsNumber other )
    {
        return of( _value % other._value );
    }
    
    public MtsNumber pow( MtsNumber other )
    {
        return of( Math.pow( _value, other._value ) );
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
        
        return valueOf( _value <= other.asNumber()._value );
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
        return Double.valueOf( _value );
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
