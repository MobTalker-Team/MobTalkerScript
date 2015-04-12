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

import static net.mobtalker.mobtalkerscript.v3.value.MtsValue.*;

import java.util.*;

/**
 * An Array-backed list specifically tailored for MobTalkerScript.
 */
public final class MtsTableList extends AbstractList<MtsValue> implements RandomAccess
{
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    
    // ========================================
    
    private MtsValue[] _entries;
    private int _limit;
    
    // ========================================
    
    /* package */MtsTableList( int initialCapacity )
    {
        int capacity = 1;
        while ( capacity < initialCapacity )
        {
            capacity <<= 1;
        }
        
        _entries = new MtsValue[capacity];
        _limit = 0;
    }
    
    // ========================================
    
    private int findCapacity( int target )
    {
        if ( target <= _entries.length )
            return _entries.length;
        
        if ( target > MAXIMUM_CAPACITY )
        {
            target = MAXIMUM_CAPACITY;
        }
        
        int actual = _entries.length;
        while ( actual < target )
        {
            actual <<= 1;
        }
        
        return actual;
    }
    
    private void resize( int newCapacity )
    {
        MtsValue[] newList = new MtsValue[newCapacity];
        System.arraycopy( _entries, 0, newList, 0, _entries.length );
        _entries = newList;
    }
    
    /**
     * Ensures that this list can hold at least <tt>minCapacity</tt> entries without further resizing.
     */
    public void ensureCapacity( int minCapacity )
    {
        if ( minCapacity > _entries.length )
        {
            resize( findCapacity( minCapacity ) );
        }
    }
    
    public void ensureSpace( int space )
    {
        ensureCapacity( _limit + space );
    }
    
    // ========================================
    // Size
    
    @Override
    public int size()
    {
        return _limit;
    }
    
    public boolean canGetOrRemoveAt( int i )
    {
        return ( 0 <= i ) && ( i < _limit );
    }
    
    public boolean canGetOrRemoveAt( MtsValue key )
    {
        return key.isInteger() && canGetOrRemoveAt( key.asNumber().toJavaInt() - 1 );
    }
    
    public boolean canAddOrSetAt( int i )
    {
        return ( 0 <= i ) && ( i <= _limit );
    }
    
    public boolean canAddOrSetAt( MtsValue key )
    {
        return key.isInteger() && canAddOrSetAt( key.asNumber().toJavaInt() - 1 );
    }
    
    // ========================================
    // Adding
    
    @Override
    public boolean add( MtsValue value )
    {
        ensureCapacity( _limit + 1 );
        _entries[_limit++] = value;
        return true;
    }
    
    @Override
    public void add( int i, MtsValue value )
    {
        if ( ( i < 0 ) || ( _limit < i ) )
            throw new ArrayIndexOutOfBoundsException( i );
        
        if ( i == _limit )
        {
            add( value );
            return;
        }
        
        // Perform a special split copy if needed
        // Avoids copying the whole array and than subsequent entries again.
        int newCapacity = findCapacity( _limit + 1 );
        if ( newCapacity > _entries.length )
        {
            MtsValue[] newList = new MtsValue[newCapacity];
            System.arraycopy( _entries, 0, newList, 0, i );
            System.arraycopy( _entries, i, newList, i + 1, _limit - i );
            _entries = newList;
        }
        else
        {
            System.arraycopy( _entries, i, _entries, i + 1, _limit - i );
        }
        
        if ( value.isNil() )
        {
            _limit = i;
        }
        else
        {
            _entries[i] = value;
            _limit++;
        }
    }
    
    /**
     * <b>NOTE:</b> Indices are 1 based.
     */
    public void add( MtsNumber key, MtsValue value )
    {
        add( key.toJavaInt() - 1, value );
    }
    
    @Override
    public boolean addAll( Collection<? extends MtsValue> c )
    {
        ensureCapacity( _limit + c.size() );
        return super.addAll( c );
    }
    
    public boolean addAll( MtsVarargs values )
    {
        int limit = values.count();
        ensureCapacity( _limit + limit );
        
        boolean modified = false;
        for ( int i = 0; i < limit; ++i )
            if ( add( values.get( i ) ) )
                modified = true;
        
        return modified;
    }
    
    @Override
    public boolean addAll( int index, Collection<? extends MtsValue> c )
    {
        ensureCapacity( _limit + c.size() );
        return super.addAll( index, c );
    }
    
    // ========================================
    // Setting
    
    @Override
    public MtsValue set( int i, MtsValue value )
    {
        if ( ( i < 0 ) || ( _limit <= i ) )
            throw new ArrayIndexOutOfBoundsException( i );
        
        MtsValue old = _entries[i];
        
        if ( value.isNil() )
        {
            _limit = i;
            _entries[i] = null;
        }
        else
        {
            _entries[i] = value;
        }
        
        return old;
    }
    
    /**
     * <b>NOTE:</b> Indices are 1 based.
     */
    public MtsValue set( MtsNumber key, MtsValue value )
    {
        return set( key.toJavaInt() - 1, value );
    }
    
    // ========================================
    // Removing
    
    @Override
    public MtsValue remove( int i )
    {
        if ( ( i < 0 ) || ( _limit <= i ) )
            throw new ArrayIndexOutOfBoundsException( i );
        
        MtsValue old = _entries[i];
        if ( i < _limit-- )
        {
            System.arraycopy( _entries, i + 1, _entries, i, _limit - i );
        }
        
        return old;
    }
    
    /**
     * <b>NOTE:</b> Indices are 1 based.
     */
    public MtsValue remove( MtsNumber key )
    {
        return remove( key.toJavaInt() - 1 );
    }
    
    /**
     * Removes the last entry in this list.
     */
    public MtsValue removeLast()
    {
        if ( _limit == 0 )
            return Nil;
        
        _limit--;
        return _entries[_limit];
    }
    
    /*
     * Removes every entry from this list.
     */
    @Override
    public void clear()
    {
        _limit = 0;
    }
    
    // ========================================
    // Retrieval
    
    @Override
    public MtsValue get( int i )
    {
        if ( ( i < 0 ) || ( _limit <= i ) )
            throw new ArrayIndexOutOfBoundsException( i );
        
        return _entries[i];
    }
    
    /**
     * <b>NOTE:</b> Indices are 1 based.
     */
    public MtsValue get( MtsNumber key )
    {
        return get( key.toJavaInt() - 1 );
    }
    
    public MtsValue get( MtsValue key )
    {
        if ( !key.isInteger() )
            return Nil;
        int i = key.asNumber().toJavaInt() - 1;
        if ( ( i < 0 ) || ( _limit <= i ) )
            return Nil;
        
        return _entries[i];
    }
    
    // ========================================
    // Searching
    
    @Override
    public boolean contains( Object o )
    {
        return indexOf( o ) >= 0;
    }
    
    @Override
    public int indexOf( Object o )
    {
        if ( !( o instanceof MtsValue ) )
            return -1;
        
        for ( int i = 0; i < _limit; i++ )
        {
            if ( o.equals( _entries[i] ) )
                return i;
        }
        
        return -1;
    }
    
    // ========================================
    // Sublist
    
    public List<MtsValue> subList( int fromIndex )
    {
        return subList( fromIndex, _limit );
    }
    
    // ========================================
    // Concatenation
    
    public String concat( String sep, int from, int to )
    {
        if ( ( _limit == 0 ) || ( from < 0 ) || ( to < from ) )
            return "";
        
        from = Math.max( 0, from );
        to = Math.min( _limit, to );
        
        StringBuilder s = new StringBuilder( _entries[from].toMtsString().toJava() );
        for ( int i = from + 1; i < to; i++ )
        {
            s.append( sep ).append( _entries[i].toMtsString().toJava() );
        }
        
        return s.toString();
    }
    
    public String concat( String sep, int from )
    {
        return concat( sep, from, _limit );
    }
    
    public String concat( String sep )
    {
        return concat( sep, 0, _limit );
    }
    
    // ========================================
    // Transfer operations
    
    /* package */void collectFrom( MtsTableMap map )
    {
        MtsValue value;
        int i = _limit;
        // ++i is needed for conversion between 1-based and 0-based indices.
        while ( !( value = map.remove( MtsNumber.of( ++i ) ) ).isNil() )
        {
            add( value );
        }
    }
    
    /* package */void transferOrphansTo( MtsTableMap map )
    {
        MtsValue[] t = _entries;
        MtsValue value;
        int i = _limit + 1;
        while ( ( i < t.length ) && ( ( value = t[i] ) != null ) )
        {
            t[i] = null;
            // ++i is needed for conversion between 1-based and 0-based indices.
            map.put( MtsNumber.of( ++i ), value );
        }
    }
    
    // ========================================
    
    public void sort()
    {
        Arrays.sort( _entries );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder( "[" );
        for ( Iterator<MtsValue> iterator = iterator(); iterator.hasNext(); )
        {
            MtsValue value = iterator.next();
            s.append( value.toString( false ) );
        }
        return s.append( "]" ).toString();
    }
}