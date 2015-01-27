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

import static com.google.common.base.Preconditions.*;

import java.util.*;

import net.mobtalker.mobtalkerscript.v3.MtsArithmeticException;

import org.apache.commons.lang3.StringEscapeUtils;

public class MtsString extends MtsValue
{
    private static final HashMap<String, MtsString> HEAP;
    
    static
    {
        HEAP = new HashMap<>( 1028 );
    }
    
    // ========================================
    
    public static final MtsString Empty = new MtsString( "" );
    
    // ========================================
    
    public static MtsString of( String s )
    {
        checkNotNull( s );
        
        int length = s.length();
        if ( length == 0 )
            return Empty;
        if ( length > 32 )
            return new MtsString( s );
        
        MtsString result = HEAP.get( s );
        if ( result == null )
        {
            result = new MtsString( s );
            HEAP.put( s, result );
        }
        
        return result;
    }
    
    // ========================================
    
    public static MtsString concat( MtsVarargs values )
    {
        if ( ( values == null ) || values.isEmpty() )
            return Empty;
        
        switch ( values.count() )
        {
            case 1:
                return values.get( 0 ).toMtsString();
            case 2:
                return values.get( 0 ).concat( values.get( 1 ) );
            default:
                StringBuilder sb = new StringBuilder();
                for ( int i = 0; i < values.count(); i++ )
                {
                    MtsValue value = values.get( i );
                    sb.append( value.toMtsString().toJava() );
                }
                
                return of( sb.toString() );
        }
    }
    
    public static MtsString concat( Iterable<MtsValue> values )
    {
        if ( ( values == null ) )
            return Empty;
        
        Iterator<MtsValue> iter = values.iterator();
        
        if ( !iter.hasNext() )
            return Empty;
        
        MtsValue value = iter.next();
        if ( !iter.hasNext() )
            return value.toMtsString();
        
        StringBuilder s = new StringBuilder( value.toMtsString().toJava() );
        while ( iter.hasNext() )
        {
            value = iter.next();
            s.append( value.toMtsString().toJava() );
        }
        
        return of( s.toString() );
    }
    
    // ========================================
    
    private final String _value;
    private MtsNumber _numberValue;
    
    // ========================================
    
    /* package */MtsString( String value )
    {
        _value = value;
    }
    
    // ========================================
    
    public MtsString intern()
    {
        if ( !HEAP.containsKey( _value ) )
        {
            HEAP.put( _value, this );
            return this;
        }
        else
        {
            return this;
        }
    }
    
    private MtsNumber coerceToNumber()
    {
        MtsNumber n = _numberValue;
        if ( n == null )
            _numberValue = n = MtsNumber.parse( _value );
        if ( n.isNaN() )
            throw new MtsArithmeticException( getType() );
        return n;
    }
    
    // ========================================
    
    @Override
    protected MtsValue getRaw( MtsValue key )
    {
        return Nil;
    }
    
    // ========================================
    
    @Override
    public MtsNumber getLength()
    {
        return MtsNumber.of( _value.length() );
    }
    
    @Override
    public MtsValue unaryMinus()
    {
        return coerceToNumber().unaryMinus();
    }
    
    // ========================================
    
    @Override
    public MtsValue add( MtsValue b )
    {
        return b.addTo( coerceToNumber() );
    }
    
    @Override
    protected MtsValue addTo( MtsNumber a )
    {
        return a.add( coerceToNumber() );
    }
    
    @Override
    public MtsValue substract( MtsValue b )
    {
        return b.substractFrom( coerceToNumber() );
    }
    
    @Override
    protected MtsValue substractFrom( MtsNumber a )
    {
        return a.substract( coerceToNumber() );
    }
    
    @Override
    public MtsValue multiplyBy( MtsValue b )
    {
        return b.multiplyWith( coerceToNumber() );
    }
    
    @Override
    protected MtsValue multiplyWith( MtsNumber a )
    {
        return a.multiplyBy( coerceToNumber() );
    }
    
    @Override
    public MtsValue divideBy( MtsValue b )
    {
        return b.divideFrom( coerceToNumber() );
    }
    
    @Override
    protected MtsValue divideFrom( MtsNumber a )
    {
        return a.divideBy( coerceToNumber() );
    }
    
    @Override
    public MtsValue powerTo( MtsValue b )
    {
        return b.powerOf( coerceToNumber() );
    }
    
    @Override
    protected MtsValue powerOf( MtsNumber a )
    {
        return a.powerTo( coerceToNumber() );
    }
    
    @Override
    public MtsValue modulo( MtsValue b )
    {
        return b.moduloOf( coerceToNumber() );
    }
    
    @Override
    protected MtsValue moduloOf( MtsNumber a )
    {
        return a.modulo( coerceToNumber() );
    }
    
    // ========================================
    
    @Override
    public MtsString concat( MtsValue b )
    {
        return b.concatTo( _value );
    }
    
    @Override
    protected MtsString concatTo( String a )
    {
        return MtsString.of( a.concat( _value ) );
    }
    
    // ========================================
    
    @Override
    public MtsBoolean isLessThen( MtsValue other )
    {
        return isGreaterThen( this );
    }
    
    @Override
    protected MtsBoolean isGreaterThen( MtsString other )
    {
        return MtsBoolean.of( _value.compareTo( other.toJava() ) >= 0 );
    }
    
    @Override
    public MtsBoolean isLessThenOrEqual( MtsValue other )
    {
        return isGreaterThenOrEqual( this );
    }
    
    @Override
    protected MtsBoolean isGreaterThenOrEqual( MtsString other )
    {
        return MtsBoolean.of( _value.compareTo( other.toJava() ) > 0 );
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
    public MtsType getType()
    {
        return MtsType.STRING;
    }
    
    // ========================================
    
    @Override
    public MtsString toMtsString()
    {
        return this;
    }
    
    @Override
    public MtsNumber toMtsNumber() throws NumberFormatException
    {
        return MtsNumber.parse( _value );
    }
    
    // ========================================
    
    @Override
    public String toJava()
    {
        return _value;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return '"' + StringEscapeUtils.escapeJava( _value ) + '"';
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
        if ( obj == null )
            return false;
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
