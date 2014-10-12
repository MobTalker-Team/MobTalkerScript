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

import static com.google.common.base.Preconditions.*;

import java.util.*;

import net.mobtalker.mobtalkerscript.v2.ScriptRuntimeException;

import com.google.common.collect.Maps;

public class MtsString extends MtsValue
{
    private static final HashMap<String, MtsString> CACHE = Maps.newHashMapWithExpectedSize( 2 << 10 );
    
    // ========================================
    
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
    
    public static MtsString concat( MtsValue value )
    {
        return value.toMtsString();
    }
    
    public static MtsString concat( MtsValue first, MtsValue second )
    {
        return of( first.toMtsString().asJavaString()
                   + second.toMtsString().asJavaString() );
    }
    
    public static MtsString concat( MtsValue first, MtsValue second, MtsValue third )
    {
        return of( first.toMtsString().asJavaString()
                   + second.toMtsString().asJavaString()
                   + third.toMtsString().asJavaString() );
    }
    
    public static MtsString concat( MtsValue first, MtsValue second, MtsValue third, MtsValue... others )
    {
        StringBuilder sb = new StringBuilder();
        sb.append( first );
        sb.append( second );
        sb.append( third );
        
        if ( ( others != null ) && ( others.length > 0 ) )
        {
            for ( MtsValue value : others )
            {
                sb.append( value.toMtsString().asJavaString() );
            }
        }
        
        return of( sb.toString() );
    }
    
    public static MtsString concat( MtsVarArgs values )
    {
        if ( ( values == null ) || values.isEmpty() )
            return EMPTY_STRING;
        
        int elements = values.count();
        if ( elements == 1 )
        {
            return values.get( 0 ).toMtsString();
        }
        else if ( elements > 2 )
        {
            StringBuilder sb = new StringBuilder();
            for ( int i = 0; i < values.count(); i++ )
            {
                MtsValue value = values.get( i );
                sb.append( value.toMtsString().asJavaString() );
            }
            
            return of( sb.toString() );
        }
        else
        {
            return concat( values.get( 0 ), values.get( 1 ) );
        }
    }
    
    public static MtsString concat( Iterable<MtsValue> values )
    {
        if ( ( values == null ) )
            return EMPTY_STRING;
        
        Iterator<MtsValue> iter = values.iterator();
        
        if ( !iter.hasNext() )
            return EMPTY_STRING;
        
        MtsValue value = iter.next();
        if ( !iter.hasNext() )
            return value.toMtsString();
        
        StringBuilder s = new StringBuilder( value.toMtsString().asJavaString() );
        while ( iter.hasNext() )
        {
            value = iter.next();
            s.append( value.toMtsString().asJavaString() );
        }
        
        return of( s.toString() );
    }
    
    // ========================================
    
    private final String _value;
    
    // ========================================
    
    /* package */MtsString( String value )
    {
        _value = value;
    }
    
    // ========================================
    
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
    protected MtsValue doGet( MtsValue key )
    {
        return NIL;
    }
    
    @Override
    protected MtsBoolean doIsLess( MtsValue other )
    {
        if ( !other.isString() )
            throw new ScriptRuntimeException( "attempt to compare %s with %s", getType(), other.getType() );
        
        return valueOf( _value.compareTo( other.asString().asJavaString() ) < 0 );
    }
    
    @Override
    protected MtsBoolean doIsLessOrEqual( MtsValue other )
    {
        if ( !other.isString() )
            throw new ScriptRuntimeException( "attempt to compare %s with %s", getType(), other.getType() );
        
        return valueOf( _value.compareTo( other.asString().asJavaString() ) <= 0 );
    }
    
    @Override
    protected MtsNumber doGetLength()
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
    public MtsString asString()
    {
        return this;
    }
    
    public String asJavaString()
    {
        return _value;
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
    
    @Override
    public String toString()
    {
        return '"' + _value + '"';
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
        
        return ( (MtsString) obj ).asJavaString().equals( _value );
    }
    
    @Override
    public int compareTo( MtsValue o )
    {
        if ( !o.isString() )
            return 0;
        
        return _value.compareTo( o.asString().asJavaString() );
    }
    
}
