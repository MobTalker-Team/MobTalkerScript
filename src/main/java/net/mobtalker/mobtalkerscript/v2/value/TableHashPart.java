/*
 * Copyright (C) 2013-2014 Chimaine
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mobtalker.mobtalkerscript.v2.value;

import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;

import java.util.*;

import net.mobtalker.mobtalkerscript.v2.ScriptRuntimeException;
import net.mobtalker.mobtalkerscript.v2.value.MtsTable.Entry;

/**
 * A HashMap specifically tailored for MobTalkerScript.
 */
/* package */final class TableHashPart implements Iterable<Entry>
{
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    private static final float LOAD_FACTOR = 0.75f;

    // ========================================

    private HashEntry[] _entries;
    private int _threshold;
    private int _count;

    // ========================================

    public TableHashPart( int initialCapacity )
    {
        int capacity = 1;
        while ( capacity < initialCapacity )
            capacity <<= 1;

        _entries = new HashEntry[capacity];
        _threshold = (int) ( capacity * LOAD_FACTOR );
        _count = 0;
    }

    // ========================================

    /**
     * Applies a supplemental hash function to a given hashCode, which
     * defends against poor quality hash functions. This is critical
     * because HashMap uses power-of-two length hash tables, that
     * otherwise encounter collisions for hashCodes that do not differ
     * in lower bits. Note: Null keys always map to hash 0, thus index 0.
     */
    private final static int hash( int h )
    {
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        return h ^ ( h >>> 20 ) ^ ( h >>> 12 ) ^ ( h >>> 7 ) ^ ( h >>> 4 );
    }

    /**
     * Returns 0 for <code>null</code> or {@link MtsNil nil} values.
     */
    private final static int getHashFor( MtsValue o )
    {
        return ( ( o == null ) || o.isNil() ) ? 0 : hash( o.hashCode() );
    }

    /**
     * Returns index for hash code h.
     */
    private final static int indexFor( int h, int length )
    {
        return h & ( length - 1 );
    }

    /**
     * Rehashes the contents of this table into a new array with a
     * larger capacity. This method is called automatically when the
     * number of keys in this map reaches its threshold.
     *
     * If current capacity is MAXIMUM_CAPACITY, this method does not
     * resize the map, but sets threshold to Integer.MAX_VALUE.
     * This has the effect of preventing future calls.
     *
     * @param newCapacity the new capacity, MUST be a power of two;
     *            must be greater than current capacity unless current
     *            capacity is MAXIMUM_CAPACITY (in which case value
     *            is irrelevant).
     */
    private void resize( int newCapacity )
    {
        HashEntry[] oldTable = _entries;
        int oldCapacity = oldTable.length;
        if ( oldCapacity == MAXIMUM_CAPACITY )
        {
            _threshold = Integer.MAX_VALUE;
            return;
        }

        HashEntry[] newTable = new HashEntry[newCapacity];
        transferEntries( newTable );
        _entries = newTable;
        _threshold = (int) ( newCapacity * LOAD_FACTOR );
    }

    /*
     * Expand the table if the number of mappings to be added
     * is greater than or equal to threshold. This is conservative; the
     * obvious condition is (m.size() + size) >= threshold, but this
     * condition could result in a map with twice the appropriate capacity,
     * if the keys to be added overlap with the keys already in this map.
     * By using the conservative calculation, we subject ourself
     * to at most one extra resize.
     */
    public void ensureCapacity( int minCapacity )
    {
        if ( minCapacity > _threshold )
        {
            int targetCapacity = (int) ( ( minCapacity / LOAD_FACTOR ) + 1 );
            if ( targetCapacity > MAXIMUM_CAPACITY )
            {
                targetCapacity = MAXIMUM_CAPACITY;
            }
            int newCapacity = _entries.length;
            while ( newCapacity < targetCapacity )
            {
                newCapacity <<= 1;
            }
            if ( newCapacity > _entries.length )
            {
                resize( newCapacity );
            }
        }
    }

    /**
     * Transfers all entries from current table to newTable (rehash the table).
     */
    private void transferEntries( HashEntry[] newTable )
    {
        int newCapacity = newTable.length;

        HashEntry[] t = _entries;
        for ( int j = 0; j < t.length; j++ )
        {
            HashEntry entry = t[j];
            if ( entry != null )
            {
                t[j] = null;
                do
                {
                    HashEntry next = entry.next;
                    int i = indexFor( entry.hash, newCapacity );
                    entry.next = newTable[i];
                    newTable[i] = entry;
                    entry = next;
                }
                while ( entry != null );
            }
        }
    }

    /**
     * Contains-and-Remove
     * <p>
     * Removes and returns the entry associated with the specified key in this table. Returns {@link #NIL} if this table
     * contained no mapping for this key.
     */
    public MtsValue remove( MtsValue key )
    {
        int hash = getHashFor( key );
        int i = indexFor( hash, _entries.length );

        HashEntry prev = _entries[i];
        HashEntry e = prev;

        while ( e != null )
        {
            HashEntry next = e.next;
            if ( ( e.hash == hash ) && Objects.equals( key, e.key ) )
            {
                _count--;

                if ( prev == e )
                    _entries[i] = next;
                else
                    prev.next = next;

                return e.value;
            }

            prev = e;
            e = next;
        }

        return NIL;
    }

    /**
     * Returns the value associated with the specified key in the
     * table. Returns {@link #NIL} if the table contains no mapping
     * for this key.
     */
    public MtsValue get( MtsValue key )
    {
        int hash = getHashFor( key );

        for ( HashEntry e = _entries[indexFor( hash, _entries.length )]; e != null; e = e.next )
        {
            if ( ( e.hash == hash ) && Objects.equals( key, e.key ) )
                return e.value;
        }

        return NIL;
    }

    private void add( MtsValue key, int hash, int i, MtsValue value )
    {
        HashEntry e = _entries[i];
        _entries[i] = new HashEntry( key, hash, value, e );
        _count++;

        if ( _count >= _threshold )
        {
            resize( 2 * _entries.length );
        }
    }

    public MtsValue set( MtsValue key, MtsValue value )
    {
        assert key != null : "key was null";

        if ( key.isNil() )
            throw new ScriptRuntimeException( "table index is nil" );

        if ( value.isNil() )
            return remove( key );

        int hash = getHashFor( key );
        int i = indexFor( hash, _entries.length );

        for ( HashEntry entry = _entries[i]; entry != null; entry = entry.next )
        {
            if ( ( entry.hash == hash ) && Objects.equals( key, entry.key ) )
            {
                MtsValue oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
        }

        add( key, hash, i, value );
        return NIL;
    }

    public Entry getFirst()
    {
        if ( _count == 0 )
            return null;

        Entry e = null;
        HashEntry[] t = _entries;
        for ( int i = 0; i < t.length; i++ )
        {
            e = t[i];
            if ( e != null )
                return e;
        }

        return e;
    }

    /**
     * Returns the entry that follows (in arbitrary order) the entry associated with the given key.
     * <p>
     * If <tt>key</tt> is {@link #NIL} the result is the same as {@link #getFirst()}. <br/>
     * If no entry for <tt>key</tt> exists in this table a {@link ScriptRuntimeException} is thrown.
     * <p>
     * If there are no entries after <tt>key</tt> the return value is <code>null</code>.
     */
    public Entry getNext( MtsValue key )
    {
        if ( key.isNil() )
            return getFirst();

        HashEntry next = null;
        int hash = getHashFor( key );
        HashEntry[] t = _entries;

        int i = indexFor( hash, t.length );
        for ( next = t[i]; next != null; next = next.next )
        {
            if ( ( next.hash == hash ) && Objects.equals( key, next.key ) )
                break;
        }

        if ( next == null )
            throw new ScriptRuntimeException( "invalid key" );

        next = next.next;
        if ( next == null )
        {
            for ( i++; ( i < t.length ) && ( next == null ); next = t[i++] );
        }

        return next;
    }

    public void clear()
    {
        HashEntry[] t = _entries;
        for ( int i = 0; i < t.length; i++ )
        {
            t[i] = null;
        }

        _count = 0;
    }

    // ========================================

    public int count()
    {
        return _count;
    }

    // ========================================

    @Override
    public String toString()
    {
        Iterator<Entry> iterator = iterator();
        if ( !iterator.hasNext() )
            return "[]";

        StringBuilder s = new StringBuilder();
        s.append( "[" ).append( iterator.next() );
        while ( iterator.hasNext() )
        {
            s.append( ", " ).append( iterator.next() );
        }
        s.append( "]" );

        return s.toString();
    }

    // ========================================

    /**
     * Each entry is a Key-Value mapping as well as a bucket.
     * Values with the same hash are appended to each other in single-linked list style.
     */
    private static final class HashEntry extends Entry
    {
        /**
         * Hash of the key when this entry was created.
         */
        final int hash;

        /**
         * The next entry in the bucket list or <code>null</code> if this entry is not a bucket.
         */
        HashEntry next;

        HashEntry( MtsValue k, int h, MtsValue v, HashEntry n )
        {
            super( k, v );
            hash = h;
            next = n;
        }
    }

    // ========================================

    @Override
    public Iterator<Entry> iterator()
    {
        return new HashIterator( this );
    }

    // ========================================

    private static class HashIterator implements Iterator<Entry>
    {
        private final TableHashPart _hashPart;
        private Entry _next;

        public HashIterator( TableHashPart hashPart )
        {
            _hashPart = hashPart;
            _next = _hashPart.getFirst();
        }

        @Override
        public boolean hasNext()
        {
            return _next != null;
        }

        @Override
        public Entry next()
        {
            if ( _next == null )
                throw new NoSuchElementException();

            Entry entry = _next;
            _next = _hashPart.getNext( entry.getKey() );

            return entry;
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}