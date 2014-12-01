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
package net.mobtalker.mobtalkerscript.v3.value;

import static net.mobtalker.mobtalkerscript.v3.value.MtsValue.*;

import java.util.*;

import net.mobtalker.mobtalkerscript.v3.ScriptRuntimeException;

/**
 * A HashMap specifically tailored for MobTalkerScript.
 */
public final class MtsTableMap implements Map<MtsValue, MtsValue>
{
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    private static final float LOAD_FACTOR = 0.75f;
    
    // ========================================
    
    private HashEntry[] _entries;
    private int _threshold;
    private int _count;
    
    private EntrySet _entrySet;
    private KeysSet _keysSet;
    private ValuesCollection _valuesCollection;
    
    // ========================================
    
    /* package */MtsTableMap( int initialCapacity )
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
    
    public void ensureSpace( int space )
    {
        ensureCapacity( _count + space );
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
    
    // ========================================
    
    @Override
    public int size()
    {
        return _count;
    }
    
    @Override
    public boolean isEmpty()
    {
        return _count == 0;
    }
    
    @Override
    public boolean containsKey( Object key )
    {
        return getEntry( key ) != null;
    }
    
    @Override
    public boolean containsValue( Object value )
    {
        if ( !( value instanceof MtsValue ) || isEmpty() )
            return false;
        
        HashEntry[] entries = _entries;
        for ( int i = 0; i < entries.length; ++i )
            for ( HashEntry e = entries[i]; e != null; e = e.next )
                if ( value.equals( e.value ) )
                    return true;
        
        return false;
    }
    
    // ========================================
    
    @Override
    public MtsValue get( Object key )
    {
        Map.Entry<MtsValue, MtsValue> result = getEntry( key );
        return result == null ? NIL : result.getValue();
    }
    
    public Entry<MtsValue, MtsValue> getEntry( Object key )
    {
        if ( !( key instanceof MtsValue ) || isEmpty() )
            return null;
        
        int hash = getHashFor( (MtsValue) key );
        for ( HashEntry e = _entries[indexFor( hash, _entries.length )]; e != null; e = e.next )
            if ( ( e.hash == hash ) && Objects.equals( key, e.key ) )
                return e;
        
        return null;
    }
    
    // ========================================
    
    @Override
    public MtsValue put( MtsValue key, MtsValue value )
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
        
        HashEntry e = _entries[i];
        _entries[i] = new HashEntry( key, hash, value, e );
        _count++;
        
        ensureCapacity( _count );
        
        return NIL;
    }
    
    @Override
    public void putAll( Map<? extends MtsValue, ? extends MtsValue> map )
    {
        int nEntries = map.size();
        if ( nEntries == 0 )
            return;
        
        ensureCapacity( _count + nEntries );
        
        for ( Map.Entry<? extends MtsValue, ? extends MtsValue> e : map.entrySet() )
            put( e.getKey(), e.getValue() );
    }
    
    // ========================================
    
    /**
     * Contains-and-Remove
     * <p>
     * Removes and returns the entry associated with the specified key in this table. Returns {@link #NIL} if this table
     * contained no mapping for this key.
     */
    @Override
    public MtsValue remove( Object key )
    {
        if ( !( key instanceof MtsValue ) )
            return NIL;
        
        int hash = getHashFor( (MtsValue) key );
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
    
    // ========================================
    
    public Entry<MtsValue, MtsValue> getFirst()
    {
        if ( _count == 0 )
            return null;
        
        HashEntry e = null;
        HashEntry[] t = _entries;
        for ( int i = 0; i < t.length; i++ )
        {
            e = t[i];
            if ( e != null )
                return e;
        }
        
        return e;
    }
    
    public Entry<MtsValue, MtsValue> getNext( Entry<MtsValue, MtsValue> entry )
    {
        return getNext( entry.getKey() );
    }
    
    /**
     * Returns the entry that follows (in arbitrary order) the entry associated with the given key.
     * <p>
     * If <tt>key</tt> is {@link #NIL} the result is the same as {@link #getFirst()}. <br/>
     * If no entry for <tt>key</tt> exists in this table a {@link ScriptRuntimeException} is thrown.
     * <p>
     * If there are no entries after <tt>key</tt> the return value is <code>null</code>.
     */
    public Entry<MtsValue, MtsValue> getNext( MtsValue key )
    {
        if ( key.isNil() )
            return getFirst();
        
        HashEntry next = null;
        int hash = getHashFor( key );
        HashEntry[] t = _entries;
        
        int i = indexFor( hash, t.length );
        for ( next = t[i]; next != null; next = next.next )
            if ( ( next.hash == hash ) && Objects.equals( key, next.key ) )
                break;
        
        if ( next == null )
            throw new ScriptRuntimeException( "invalid key" );
        
        next = next.next;
        if ( next == null )
        {
            for ( i++; ( i < t.length ) && ( next == null ); next = t[i++] );
        }
        
        return next;
    }
    
    @Override
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
    
    @Override
    public Set<Map.Entry<MtsValue, MtsValue>> entrySet()
    {
        Set<Map.Entry<MtsValue, MtsValue>> result = _entrySet;
        return result != null ? result : ( _entrySet = new EntrySet() );
    }
    
    @Override
    public Set<MtsValue> keySet()
    {
        Set<MtsValue> result = _keysSet;
        return result != null ? result : ( _keysSet = new KeysSet() );
    }
    
    @Override
    public Collection<MtsValue> values()
    {
        Collection<MtsValue> result = _valuesCollection;
        return result != null ? result : ( _valuesCollection = new ValuesCollection() );
    }
    
    // ========================================
    
    /**
     * Each entry is a Key-Value mapping as well as a bucket.
     * Values with the same hash are appended to each other in single-linked list style.
     */
    private static final class HashEntry extends MtsTable.Entry implements Map.Entry<MtsValue, MtsValue>
    {
        /**
         * Hash of the key when this entry was created.
         */
        final int hash;
        
        /**
         * The next entry in the bucket list or <code>null</code> if this entry is the last in the list
         */
        HashEntry next;
        
        HashEntry( MtsValue k, int h, MtsValue v, HashEntry n )
        {
            super( k, v );
            hash = h;
            next = n;
        }
        
        @Override
        public MtsValue setValue( MtsValue value )
        {
            MtsValue result = this.value;
            this.value = value;
            return result;
        }
    }
    
    // ========================================
    
    private abstract class HashIterator<T> implements Iterator<T>
    {
        private Entry<MtsValue, MtsValue> _next;
        
        protected HashIterator()
        {
            _next = MtsTableMap.this.getFirst();
        }
        
        @Override
        public boolean hasNext()
        {
            return _next != null;
        }
        
        @Override
        public T next()
        {
            T result = getResult( _next );
            _next = MtsTableMap.this.getNext( _next );
            return result;
        }
        
        protected abstract T getResult( Entry<MtsValue, MtsValue> entry );
        
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
    
    /* package */final class KeysIterator extends HashIterator<MtsValue>
    {
        @Override
        protected MtsValue getResult( Entry<MtsValue, MtsValue> entry )
        {
            return entry.getKey();
        }
    }
    
    /* package */final class ValuesIterator extends HashIterator<MtsValue>
    {
        @Override
        protected MtsValue getResult( Entry<MtsValue, MtsValue> entry )
        {
            return entry.getValue();
        }
    }
    
    /* package */final class EntriesIterator extends HashIterator<Entry<MtsValue, MtsValue>>
    {
        @Override
        protected Entry<MtsValue, MtsValue> getResult( Entry<MtsValue, MtsValue> entry )
        {
            return entry;
        }
    }
    
    // ========================================
    
    private final class EntrySet extends AbstractSet<Entry<MtsValue, MtsValue>>
    {
        @Override
        public boolean contains( Object o )
        {
            if ( !( o instanceof Map.Entry ) )
                return false;
            
            @SuppressWarnings( "unchecked" )
            Entry<MtsValue, MtsValue> e = (Map.Entry<MtsValue, MtsValue>) o;
            Entry<MtsValue, MtsValue> candidate = getEntry( e.getKey() );
            return ( candidate != null ) && candidate.equals( e );
        }
        
        @Override
        public int size()
        {
            return MtsTableMap.this.size();
        }
        
        @Override
        public void clear()
        {
            MtsTableMap.this.clear();
        }
        
        @Override
        public boolean remove( Object o )
        {
            return MtsTableMap.this.remove( o ) != null;
        }
        
        @Override
        public Iterator<Entry<MtsValue, MtsValue>> iterator()
        {
            return new MtsTableMap.EntriesIterator();
        }
    }
    
    private final class KeysSet extends AbstractSet<MtsValue>
    {
        @Override
        public boolean contains( Object o )
        {
            return MtsTableMap.this.containsKey( o );
        }
        
        @Override
        public int size()
        {
            return MtsTableMap.this.size();
        }
        
        @Override
        public void clear()
        {
            MtsTableMap.this.clear();
        }
        
        @Override
        public Iterator<MtsValue> iterator()
        {
            return new MtsTableMap.KeysIterator();
        }
    }
    
    private final class ValuesCollection extends AbstractCollection<MtsValue>
    {
        @Override
        public boolean contains( Object o )
        {
            return MtsTableMap.this.containsValue( o );
        }
        
        @Override
        public int size()
        {
            return MtsTableMap.this.size();
        }
        
        @Override
        public void clear()
        {
            MtsTableMap.this.clear();
        }
        
        @Override
        public Iterator<MtsValue> iterator()
        {
            return new MtsTableMap.ValuesIterator();
        }
    }
}