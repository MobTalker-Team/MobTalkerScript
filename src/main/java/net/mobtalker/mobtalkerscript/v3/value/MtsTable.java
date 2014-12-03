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

import static com.google.common.base.Preconditions.*;
import static net.mobtalker.mobtalkerscript.v3.MtsCheck.*;

import java.util.*;

/**
 * An associative array used to store values.
 *
 * It uses two storage formats; a list for storing consecutive indices and a hash map for any other value.
 */
public class MtsTable extends MtsMetaTableValue implements Iterable<MtsTable.Entry>
{
    private final MtsTableList _list;
    private final MtsTableMap _map;
    
    // ========================================
    
    public MtsTable()
    {
        this( 16, 16 );
    }
    
    public MtsTable( int initialListCapacity, int initialHashCapacity )
    {
        _list = new MtsTableList( initialListCapacity );
        _map = new MtsTableMap( initialHashCapacity );
    }
    
    public MtsTable( MtsTable table )
    {
        this( table.list().size(), table.map().size() );
        _list.addAll( table.list() );
        _map.putAll( table.map() );
    }
    
    // ========================================
    
    /**
     * Returns the total number of elements in this table.
     */
    public int size()
    {
        return _list.size() + _map.size();
    }
    
    public boolean isEmpty()
    {
        return size() == 0;
    }
    
    public void ensureListCapacity( int capacity )
    {
        _list.ensureCapacity( capacity );
    }
    
    public void ensureMapCapacity( int capacity )
    {
        _map.ensureCapacity( capacity );
    }
    
    public void ensureListSpace( int space )
    {
        _list.ensureCapacity( space );
    }
    
    public void ensureMapSpace( int space )
    {
        _map.ensureCapacity( space );
    }
    
    // ========================================
    
    public boolean containsKey( MtsValue key )
    {
        return !isEmpty() && ( _list.canGetOrRemoveAt( key ) || !_map.get( key ).isNil() );
    }
    
    /**
     * Finds the first entry in this table and returns it, or <code>null</code> if this table is empty.
     */
    public MtsTable.Entry getFirstEntry()
    {
        if ( isEmpty() )
            return null;
        
        if ( _list.size() > 0 )
            return new MtsTable.Entry( MtsValue.ONE, _list.get( 0 ) );
        
        return (MtsTable.Entry) _map.getFirst();
    }
    
    /**
     * Stateless iterator function.
     * <p>
     * Returns <code>null</code> if there are no entries after the given key.
     */
    public MtsTable.Entry getNext( MtsValue key )
    {
        checkNotNull( key );
        
        if ( isEmpty() )
            return null;
        
        if ( key.isNil() )
            return getFirstEntry();
        
        if ( !_list.canAddOrSetAt( key ) )
            return (MtsTable.Entry) _map.getNext( key );
        
        int next = key.asNumber().toJavaInt();
        return new MtsTable.Entry( valueOf( next ), _list.get( next ) );
    }
    
    public MtsTable.Entry getINext( MtsNumber key )
    {
        checkNotNull( key );
        
        if ( isEmpty() )
            return null;
        
        if ( !_list.canAddOrSetAt( key ) )
            return null;
        
        int next = key.asNumber().toJavaInt();
        return new MtsTable.Entry( valueOf( next ), _list.get( next ) );
    }
    
    // ========================================
    
    public void clear()
    {
        _list.clear();
        _map.clear();
    }
    
    // ========================================
    
    /**
     * Returns the value associated with the specified key in this table, or {@link MtsNil nil} if no such mapping exists.
     * <p>
     * First checks if <code>key</code> is present in this table.<br/>
     * If it is, call {@link #rawget(MtsValue) getRaw}.<br/>
     * If it is not, look for a {@value MtsValue#__INDEX} meta tag.
     * <p>
     * If there is no meta tag, call {@link #rawget(MtsValue) getRaw}.<br/>
     * If the tag is a table, repeat the operation on it.<br/>
     * Otherwise attempt to call it with <code>this</code> and <code>key</code> as the arguments.
     *
     * @see #rawget(MtsValue)
     * @see MtsValue#__INDEX
     */
    @Override
    public MtsValue doGet( MtsValue key )
    {
        assert key != null : "key cannot be null";
        
        if ( isEmpty() || key.isNil() )
            return NIL;
        
        MtsValue result = _list.get( key );
        return result.isNil() ? _map.get( key ) : result;
    }
    
    // ========================================
    
    public void set( int i, MtsValue value )
    {
        if ( _list.canAddOrSetAt( i ) )
        {
            addOrSetList( i, value );
        }
        else
        {
            setEntry( valueOf( i + 1 ), value );
        }
    }
    
    /**
     * Associates the specified value with the specified key in this table.
     * If the table previously contained a mapping for the key, the old value is replaced.
     * If the value is set to {@link MtsNil nil} the entry for that mapping is removed.
     * <p>
     * First checks if <code>key</code> is already present in this table.<br/>
     * If it is, override it's associated value.<br/>
     * If it is not, look for a {@link MtsValue#METATAG_NEWINDEX __newindex} meta tag.
     * <p>
     * If there is no meta tag, call {@link #rawset(MtsValue, MtsValue) setRaw}.<br/>
     * If the tag is a table, repeat the operation on it.<br/>
     * Otherwise attempt to call it with <code>this</code>, <code>key</code> and <code>value</code> as the arguments.
     *
     * @see #rawset(MtsValue, MtsValue)
     * @see MtsValue#METATAG_NEWINDEX
     */
    @Override
    protected void doSet( MtsValue key, MtsValue value )
    {
        assert key != null : "key was null";
        checkNotNil( key, "table index is nil" );
        
        if ( _list.canAddOrSetAt( key ) )
        {
            addOrSetList( key.asNumber().toJavaInt() - 1, value );
        }
        else
        {
            setEntry( key, value );
        }
    }
    
    private void addOrSetList( int i, MtsValue value )
    {
        if ( i == _list.size() )
        {
            _list.add( i, value );
            
            if ( !_map.isEmpty() )
            {
                _list.collectFrom( _map );
            }
        }
        else
        {
            _list.set( i, value );
            
            if ( value.isNil() )
            {
                _list.transferOrphansTo( _map );
            }
        }
    }
    
    private void setEntry( MtsValue key, MtsValue value )
    {
        _map.put( key, value );
    }
    
    // ========================================
    
    @Override
    protected MtsNumber doGetLength()
    {
        return valueOf( _list.size() );
    }
    
    // ========================================
    
    @Override
    public boolean isTable()
    {
        return true;
    }
    
    @Override
    public MtsTable asTable()
    {
        return this;
    }
    
    @Override
    public MtsType getType()
    {
        return MtsType.TABLE;
    }
    
    // ========================================
    
    @Override
    public MtsString toMtsString()
    {
        return valueOf( toString() );
    }
    
    @Override
    public String toString( boolean debug )
    {
        if ( !debug )
            return super.toString();
        
        StringBuilder s = new StringBuilder( "{" );
        
        // List
        for ( Iterator<MtsValue> iterator = _list.iterator(); iterator.hasNext(); )
        {
            MtsValue value = iterator.next();
            
            s.append( this == value ? "<self>" : value );
            
            if ( iterator.hasNext() || ( _map.size() > 0 ) )
                s.append( ", " );
        }
        
        // Maps
        for ( Iterator<Map.Entry<MtsValue, MtsValue>> iterator = _map.entrySet().iterator(); iterator.hasNext(); )
        {
            Map.Entry<MtsValue, MtsValue> e = iterator.next();
            MtsValue key = e.getKey();
            MtsValue value = e.getValue();
            
            s.append( this == key ? "<self>" : key )
             .append( '=' )
             .append( this == value ? "<self>" : value );
            
            if ( iterator.hasNext() )
                s.append( ", " );
        }
        
        return s.append( '}' ).toString();
    }
    
    // ========================================
    
    public MtsTableList list()
    {
        return _list;
    }
    
    public MtsTableMap map()
    {
        return _map;
    }
    
    // ========================================
    
    @Override
    public Iterator<MtsTable.Entry> iterator()
    {
        return new TableIterator( this );
    }
    
    // ========================================
    
    public static class Entry
    {
        /**
         * Key of this entry.
         */
        protected final MtsValue key;
        
        /**
         * The value that is associated with the key.
         */
        protected MtsValue value;
        
        protected Entry( MtsValue k, MtsValue v )
        {
            key = k;
            value = v;
        }
        
        public MtsValue getKey()
        {
            return key;
        }
        
        public MtsValue getValue()
        {
            return value;
        }
        
        @Override
        public String toString()
        {
            return key.toString() + "=" + value.toString();
        }
    }
    
    // ========================================
    
    private static final class TableIterator implements Iterator<MtsTable.Entry>
    {
        private final MtsTable _table;
        private Entry _next;
        
        // ========================================
        
        public TableIterator( MtsTable table )
        {
            _table = table;
            _next = table.getNext( NIL );
        }
        
        // ========================================
        
        @Override
        public boolean hasNext()
        {
            return _next != null;
        }
        
        @Override
        public Entry next()
        {
            if ( !hasNext() )
                throw new NoSuchElementException();
            
            Entry next = _next;
            _next = _table.getNext( next.getKey() );
            
            return next;
        }
        
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
