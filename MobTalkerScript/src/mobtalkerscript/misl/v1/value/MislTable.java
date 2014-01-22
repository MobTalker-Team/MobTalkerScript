package mobtalkerscript.misl.v1.value;

import static com.google.common.base.Preconditions.*;

/**
 * A table is at its core an associative array. Values are stored and retrieved by keys, that are either {@link MislNumber
 * numbers} or {@link MislString strings}.
 * <p>
 * Keys are always ordered, numbers naturally and before strings, also naturally.
 * 
 * @author Chimaine
 * 
 */
public class MislTable extends MislValue
{
    /**
     * The maximum capacity, used if a higher value is implicitly specified
     * by either of the constructors with arguments.
     * MUST be a power of two <= 1<<30.
     */
    public static final int MAXIMUM_CAPACITY = 1 << 30;
    
    // ========================================
    
    private final TableListPart _listPart;
    private final TableHashPart _hashPart;
    
    /**
     * Metatables in this version represent a simple lookup table when accessing a missing field.
     */
    private MislTable _metaTable;
    
    // ========================================
    
    public MislTable()
    {
        this( 16, 16 );
    }
    
    public MislTable( int initialListCapacity, int initialHashCapacity )
    {
        _listPart = new TableListPart( initialListCapacity );
        _hashPart = new TableHashPart( initialHashCapacity );
    }
    
    // ========================================
    
    public int count()
    {
        return _listPart.length() + _hashPart.count();
    }
    
    public boolean isEmpty()
    {
        return count() == 0;
    }
    
    public boolean containsKey( MislValue key )
    {
        return !isEmpty() && ( _listPart.contains( key ) || ( _hashPart.get( key ) != null ) );
    }
    
    /**
     * Finds the first entry in this table and returns it, or null if this table is empty.
     */
    public Entry getFirstEntry()
    {
        if ( _listPart.length() > 0 )
            return new Entry( MislNumber.ONE, _listPart.get( 1 ) );
        
        return _hashPart.getFirst();
    }
    
    /**
     * Stateless iterator function.
     * <p>
     * Returns <code>null</code> if there are no entries after the given key.
     */
    public Entry getEntryAfter( MislValue key )
    {
        checkNotNull( key );
        
        if ( isEmpty() )
            return null;
        
        if ( key.isNil() )
            return getFirstEntry();
        
        if ( TableListPart.isValidKey( key ) )
        {
            int i = (int) key.asNumber().toJava();
            
            if ( _listPart.contains( i++ ) )
            {
                if ( _listPart.contains( i ) )
                    return new Entry( valueOf( i ), _listPart.get( i ) );
                else
                    return _hashPart.getFirst();
            }
        }
        
        return _hashPart.getEntryAfter( key );
    }
    
    /**
     * Returns the value associated with the specified key in this table.
     * If no such mapping exists, a lookup is performed in the metatable of this table instead if it exists. Otherwise
     * {@link MislNil nil} is returned.
     */
    @Override
    public MislValue get( MislValue key )
    {
        MislValue result = getRaw( key );
        return result.isNil() && ( _metaTable != null ) ? _metaTable.get( key ) : result;
    }
    
    /**
     * Returns the value associated with the specified key in this table, or {@link MislNil nil} if no such mapping exists.
     */
    public MislValue getRaw( MislValue key )
    {
        checkNotNull( key );
        
        if ( isEmpty() || key.isNil() )
            return NIL;
        
        if ( TableListPart.isValidKey( key ) )
        {
            int i = (int) key.asNumber().toJava();
            
            if ( _listPart.contains( i ) )
                return _listPart.get( i );
        }
        
        return _hashPart.get( key );
    }
    
    // ========================================
    
    /**
     * Currently nothing else than calling {@link #setRaw(MislValue, MislValue) setRaw}.
     */
    @Override
    public MislValue set( MislValue key, MislValue value )
    {
        // TODO Metatable
        return setRaw( key, value );
    }
    
    public MislValue setRaw( String key, MislValue value )
    {
        return setRaw( valueOf( key ), value );
    }
    
    /**
     * Associates the specified value with the specified key in this table.
     * If the table previously contained a mapping for the key, the old value is replaced.
     * If the value is set to {@link MislNil nil} the entry for that mapping is removed.
     * 
     * @return The previous value associated with the key, or {@link MislNil nil} if there was no such mapping.
     */
    public MislValue setRaw( MislValue key, MislValue value )
    {
        checkNotNull( key );
        checkNotNil( key, "table index is nil" );
        
        if ( TableListPart.isValidKey( key ) )
        {
            int i = (int) key.asNumber().toJava();
            
            if ( _listPart.contains( i ) )
            {
                MislValue old = _listPart.set( i, value );
                
                if ( value.isNil() )
                {
                    _listPart.transferOrphansTo( _hashPart );
                }
                
                return old;
            }
            
            if ( _listPart.contains( i - 1 ) || ( i == 1 ) )
            {
                _listPart.add( value );
                MislValue old = _hashPart.remove( key );
                _listPart.collectFrom( _hashPart );
                
                return old;
            }
        }
        
        return _hashPart.set( key, value );
    }
    
    // ========================================
    // List Operations
    
    public int listSize()
    {
        return _listPart.length();
    }
    
    /**
     * Adds a value to the end of the sequence of this table.
     */
    public void add( MislValue value )
    {
        _listPart.add( value );
    }
    
    /**
     * Inserts a value into this table at the given location and shifts subsequent entries to the left if necessary.
     * <p>
     * If <tt>key</tt> is not part of the sequence of this table a simple {@link #setRaw(MislValue, MislValue) setRaw} is
     * performed.
     */
    public void insert( MislValue key, MislValue value )
    {
        checkNotNull( key );
        
        if ( _listPart.contains( key ) )
        {
            int i = (int) key.asNumber().toJava();
            _listPart.insert( i, value );
            _listPart.collectFrom( _hashPart );
        }
        
        setRaw( key, value );
    }
    
    /**
     * Removes a mapping from this table and shifts subsequent entries down.
     */
    public MislValue remove( MislValue key )
    {
        checkNotNull( key );
        
        if ( _listPart.contains( key ) )
        {
            int i = (int) key.asNumber().toJava();
            MislValue removed = _listPart.remove( i );
            
            _listPart.transferOrphansTo( _hashPart );
            
            return removed;
        }
        
        return _hashPart.remove( key );
    }
    
    public MislValue removeLast()
    {
        return _listPart.removeLast();
    }
    
    public MislString concat( MislString sep, MislNumber from, MislNumber to )
    {
        return _listPart.concat( sep.toJava(), (int) from.toJava(), (int) to.toJava() );
    }
    
    public MislString concat( MislString sep, MislNumber from )
    {
        return _listPart.concat( sep.toJava(), (int) from.toJava() );
    }
    
    public MislString concat( MislString sep )
    {
        return _listPart.concat( sep.toJava() );
    }
    
    // ========================================
    
    @Override
    public MislString toStringMts()
    {
        return valueOf( toString() );
    }
    
    // ========================================
    
    @Override
    public boolean isTable()
    {
        return true;
    }
    
    @Override
    public MislTable asTable()
    {
        return this;
    }
    
    @Override
    public String getTypeName()
    {
        return TYPENAME_TABLE;
    }
    
    @Override
    public MislBoolean equalsMts( MislValue x )
    {
        return valueOf( this == x );
    }
    
    // ========================================
    
    @Override
    public int hashCode()
    {
        return super.hashCode();
    }
    
    // ========================================
    
    public static class Entry
    {
        /**
         * Key of this entry.
         */
        protected final MislValue key;
        
        /**
         * The value that is associated with the key.
         */
        protected MislValue value;
        
        Entry( MislValue k, MislValue v )
        {
            key = k;
            value = v;
        }
        
        public MislValue getKey()
        {
            return key;
        }
        
        public MislValue getValue()
        {
            return value;
        }
        
        @Override
        public String toString()
        {
            return key.toString() + "=" + value.toString();
        }
    }
}
