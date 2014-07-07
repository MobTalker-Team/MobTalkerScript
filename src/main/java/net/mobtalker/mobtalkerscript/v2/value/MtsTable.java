package net.mobtalker.mobtalkerscript.v2.value;

import static com.google.common.base.Preconditions.*;
import static net.mobtalker.mobtalkerscript.util.MtsCheck.*;

/**
 * A table is at its core an associative array. Values are stored and retrieved by keys, that are either {@link MtsNumber
 * numbers} or {@link MtsString strings}.
 * <p>
 * Keys are always ordered, numbers naturally and before strings, also naturally.
 * 
 * @author Chimaine
 * 
 */
public class MtsTable extends MtsMetaTableValue
{
    private final TableListPart _listPart;
    private final TableHashPart _hashPart;
    
    // ========================================
    
    public MtsTable()
    {
        this( 16, 16 );
    }
    
    public MtsTable( int initialListCapacity, int initialHashCapacity )
    {
        _listPart = new TableListPart( initialListCapacity );
        _hashPart = new TableHashPart( initialHashCapacity );
    }
    
    // ========================================
    
    /**
     * Returns the total number of elements in this table.
     */
    public int count()
    {
        return _listPart.length() + _hashPart.count();
    }
    
    public boolean isEmpty()
    {
        return count() == 0;
    }
    
    public void ensureHashCapacity( int capacity )
    {
        _hashPart.ensureCapacity( capacity );
    }
    
    public void ensureListCapacity( int capacity )
    {
        _listPart.ensureCapacity( capacity );
    }
    
    // ========================================
    
    public boolean containsKey( MtsValue key )
    {
        return !isEmpty() && ( _listPart.contains( key ) || !_hashPart.get( key ).isNil() );
    }
    
    /**
     * Finds the first entry in this table and returns it, or null if this table is empty.
     */
    public Entry getFirstEntry()
    {
        if ( _listPart.length() > 0 )
            return new Entry( MtsValue.ONE, _listPart.get( 0 ) );
        
        return _hashPart.getFirst();
    }
    
    /**
     * Stateless iterator function.
     * <p>
     * Returns <code>null</code> if there are no entries after the given key.
     */
    public Entry getNext( MtsValue key )
    {
        checkNotNull( key );
        
        if ( isEmpty() )
            return null;
        
        if ( key.isNil() )
            return getFirstEntry();
        
        if ( TableListPart.isValidKey( key ) )
        {
            // Adjust MTS to Java index
            int i = key.asNumber().asJavaInt() - 1;
            
            if ( _listPart.contains( i++ ) )
            {
                if ( _listPart.contains( i ) )
                    return new Entry( valueOf( i + 1 ), _listPart.get( i ) );
                else
                    return _hashPart.getFirst();
            }
        }
        
        return _hashPart.getNext( key );
    }
    
    public Entry getINext( MtsNumber key )
    {
        int i = key.asNumber().asJavaInt();
        
        if ( _listPart.contains( i ) )
            return new Entry( valueOf( i ), _listPart.get( i ) );
        else
            return null;
    }
    
    // ========================================
    // List Operations
    
    /**
     * Returns the number of consecutive elements starting from index 1.
     */
    public int listSize()
    {
        return _listPart.length();
    }
    
    /**
     * Adds a value to the end of the sequence of this table.
     */
    public void add( MtsValue value )
    {
        _listPart.add( value );
    }
    
    public void add( Iterable<? extends MtsValue> values )
    {
        for ( MtsValue value : values )
        {
            add( value );
        }
    }
    
    /**
     * Inserts a value into this table at the given location (1 based) and shifts subsequent entries to the left if necessary.
     * <p>
     * If <tt>key</tt> is not part of the sequence of this table a simple {@link #rawset(MtsValue, MtsValue) setRaw} is
     * performed.
     */
    public void insert( MtsNumber index, MtsValue value )
    {
        if ( _listPart.contains( index ) )
        {
            // Adjust MTS to Java index
            int i = index.asJavaInt() - 1;
            
            _listPart.insert( i, value );
            _listPart.collectFrom( _hashPart );
        }
        else
        {
            set( index, value );
        }
    }
    
    /**
     * Removes a mapping from this table and shifts subsequent entries down.
     */
    public MtsValue remove( MtsValue key )
    {
        checkNotNull( key );
        
        if ( _listPart.contains( key ) )
        {
            int i = key.asNumber().asJavaInt();
            return _listPart.remove( i );
        }
        
        return _hashPart.remove( key );
    }
    
    public MtsValue remove( int i )
    {
        if ( !_listPart.contains( i ) )
            return NIL;
        
        return _listPart.remove( i );
    }
    
    public MtsValue removeLast()
    {
        return _listPart.removeLast();
    }
    
    public void clear()
    {
        _listPart.clear();
        _hashPart.clear();
    }
    
    public MtsString concatList( String sep, int from, int to )
    {
        return valueOf( _listPart.concat( sep, from, to ) );
    }
    
    public MtsString concatList( String sep, int from )
    {
        return valueOf( _listPart.concat( sep, from ) );
    }
    
    public MtsString concatList( String sep )
    {
        return valueOf( _listPart.concat( sep ) );
    }
    
    // ========================================
    
    @Override
    public MtsValue doGet( MtsValue key )
    {
        return rawget( key );
    }
    
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
    private MtsValue rawget( MtsValue key )
    {
        assert key != null : "key cannot be null";
        
        if ( isEmpty() || key.isNil() )
            return NIL;
        
        if ( TableListPart.isValidKey( key ) )
        {
            // Adjust MTS to Java index
            int i = key.asNumber().asJavaInt() - 1;
            
            if ( _listPart.contains( i ) )
                return _listPart.get( i );
        }
        
        return _hashPart.get( key );
    }
    
    // ========================================
    
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
        rawset( key, value );
    }
    
    private void rawset( MtsValue key, MtsValue value )
    {
        assert key != null : "key was null";
        checkNotNil( key, "table index is nil" );
        
        if ( TableListPart.isValidKey( key ) )
        {
            // Adjust MTS to Java index
            int i = key.asNumber().asJavaInt() - 1;
            
            if ( _listPart.contains( i ) )
            {
                _listPart.set( i, value );
                
                if ( value.isNil() )
                {
                    _listPart.transferOrphansTo( _hashPart );
                }
            }
            
            if ( _listPart.contains( i - 1 ) || ( i == 0 ) )
            {
                _listPart.add( value );
                _listPart.collectFrom( _hashPart );
                return;
            }
        }
        
        _hashPart.set( key, value );
    }
    
    // ========================================
    
    @Override
    protected MtsNumber doGetLength()
    {
        return valueOf( listSize() );
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
    
    // ========================================
    
    public Iterable<MtsValue> listView()
    {
        return _listPart;
    }
    
    public Iterable<Entry> entryView()
    {
        return _hashPart;
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
}
