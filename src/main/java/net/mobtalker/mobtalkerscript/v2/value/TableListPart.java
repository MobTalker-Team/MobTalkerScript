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

import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;

import java.util.*;

/**
 * An Array-backed list specifically tailored for MobTalkerScript.
 */
/* package */final class TableListPart implements Iterable<MtsValue>
{
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    
    // ========================================
    
    private MtsValue[] _entries;
    private int _limit;
    
    // ========================================
    
    public TableListPart( int initialCapacity )
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
    
    /**
     * Determines if the given key is valid for the list part of a table.
     */
    public static boolean isValidKey( MtsValue key )
    {
        if ( !key.isInteger() )
            return false;
        
        return key.asNumber().isPositive();
    }
    
    /**
     * Determines if the given key is valid for the list part of a table.
     * <p>
     * NOTE: The index is 1 based.
     */
    public static boolean isValidKey( MtsNumber key )
    {
        return key.isInteger() && key.isPositive();
    }
    
    /**
     * Length of this list.
     */
    public int length()
    {
        return _limit;
    }
    
    /**
     * Determines if the given key resides inside the list part of this table.
     * <p>
     * <b>NOTE:</b> The index is 1 based.
     */
    public boolean contains( MtsValue key )
    {
        return isValidKey( key ) && ( key.asNumber().asJavaInt() <= _limit );
    }
    
    /**
     * Determines if the given key resides inside the list part of this table.
     * <p>
     * <b>NOTE:</b> The index is 1 based.
     */
    public boolean contains( MtsNumber key )
    {
        return isValidKey( key ) && ( key.asJavaInt() <= _limit );
    }
    
    /**
     * Determines if the given index resides inside the list part of this table.
     */
    public boolean contains( int i )
    {
        return ( 0 <= i ) && ( i < _limit );
    }
    
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
    
    // ========================================
    // Adding
    
    /**
     * Adds an entry to the end of this list.
     */
    public void add( MtsValue value )
    {
        if ( value.isNil() )
            return;
        
        ensureCapacity( _limit + 1 );
        _entries[_limit++] = value;
    }
    
    /**
     * Inserts a value at the given index and shifts subsequent entries up.
     */
    public void insert( int i, MtsValue value )
    {
        if ( _limit < i )
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
     * Sets the value of a specified index
     */
    public MtsValue set( int i, MtsValue value )
    {
        if ( _limit <= i )
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
    
    // ========================================
    // Removing
    
    /**
     * Removes an entry from this list, shifting subsequent entries down.
     * <p>
     * <b>NOTE:</b> Indices are 1 based.
     */
    public MtsValue remove( MtsNumber key )
    {
        // Adjust MTS to Java index
        int i = key.asJavaInt() - 1;
        
        if ( !contains( i ) )
            throw new IllegalArgumentException( "key is not part of this list" );
        
        return doRemove( i );
    }
    
    /**
     * Removes an entry from this list, shifting subsequent entries down.
     */
    public MtsValue remove( int i )
    {
        if ( _limit <= i )
            throw new ArrayIndexOutOfBoundsException( i );
        
        return doRemove( i );
    }
    
    private MtsValue doRemove( int i )
    {
        MtsValue old = _entries[i];
        
        System.arraycopy( _entries, i + 1, _entries, i, _limit - i - 1 );
        
        --_limit;
        return old;
    }
    
    /**
     * Removes the last entry in this list.
     */
    public MtsValue removeLast()
    {
        if ( _limit == 0 )
            return NIL;
        
        _limit--;
        return _entries[_limit];
    }
    
    /*
     * Removes every entry from this list.
     */
    public void clear()
    {
        _limit = 0;
    }
    
    // ========================================
    // Retrieval
    
    /**
     * <b>NOTE:</b> Indices are 1 based.
     */
    public MtsValue get( MtsNumber key )
    {
        return get( key.asJavaInt() - 1 );
    }
    
    public MtsValue get( int i )
    {
        if ( ( i < 0 ) || ( _limit <= i ) )
            throw new ArrayIndexOutOfBoundsException( i );
        
        return _entries[i];
    }
    
    public String concat( String sep, int from, int to )
    {
        if ( ( _limit == 0 ) || ( from < 0 ) || ( _limit <= to ) || ( to < from ) )
            return "";
        
        StringBuilder s = new StringBuilder( _entries[from].toMtsString().asJavaString() );
        for ( int i = from + 1; i <= to; i++ )
        {
            s.append( sep ).append( _entries[i].toMtsString().asJavaString() );
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
    
    /* package */void collectFrom( TableHashPart hashPart )
    {
        MtsValue value;
        int i = _limit;
        // ++i is needed for conversion between 1-based and 0-based indices.
        while ( !( value = hashPart.remove( valueOf( ++i ) ) ).isNil() )
        {
            add( value );
        }
    }
    
    /* package */void transferOrphansTo( TableHashPart hashPart )
    {
        MtsValue[] t = _entries;
        MtsValue value;
        int i = _limit + 1;
        while ( ( i < t.length ) && ( ( value = t[i] ) != null ) )
        {
            t[i] = null;
            // ++i is needed for conversion between 1-based and 0-based indices.
            hashPart.set( valueOf( ++i ), value );
        }
    }
    
    // ========================================
    
    @Override
    public Iterator<MtsValue> iterator()
    {
        return new ListIterator( this );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return Arrays.toString( _entries );
    }
    
    // ========================================
    
    private static class ListIterator implements Iterator<MtsValue>
    {
        private final TableListPart _listPart;
        private int _next;
        
        public ListIterator( TableListPart listPart )
        {
            _listPart = listPart;
            _next = 0;
        }
        
        @Override
        public boolean hasNext()
        {
            return _next < _listPart.length();
        }
        
        @Override
        public MtsValue next()
        {
            if ( !hasNext() )
                throw new NoSuchElementException();
            
            MtsValue result = _listPart.get( _next++ );
            return result;
        }
        
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}