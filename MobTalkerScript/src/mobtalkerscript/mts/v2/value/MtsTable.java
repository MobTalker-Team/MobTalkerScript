package mobtalkerscript.mts.v2.value;

import static com.google.common.base.Preconditions.*;

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
    
    public boolean containsKey( MtsValue key )
    {
        return !isEmpty() && ( _listPart.contains( key ) || ( _hashPart.get( key ) != null ) );
    }
    
    /**
     * Finds the first entry in this table and returns it, or null if this table is empty.
     */
    public Entry getFirstEntry()
    {
        if ( _listPart.length() > 0 )
            return new Entry( MtsValue.ONE, _listPart.get( 1 ) );
        
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
            int i = (int) key.asNumber().toJava();
            
            if ( _listPart.contains( i++ ) )
            {
                if ( _listPart.contains( i ) )
                    return new Entry( valueOf( i ), _listPart.get( i ) );
                else
                    return _hashPart.getFirst();
            }
        }
        
        return _hashPart.getNext( key );
    }
    
    public Entry getINext( MtsNumber key )
    {
        int i = (int) key.asNumber().toJava();
        
        if ( _listPart.contains( ++i ) )
            return new Entry( valueOf( i ), _listPart.get( i ) );
        else
            return null;
    }
    
    /**
     * First checks if <code>key</code> is present in this table.<br/>
     * If it is, call {@link #getRaw(MtsValue) getRaw}.<br/>
     * If it is not, look for a {@link MtsValue#METATAG_INDEX __index} meta tag.
     * <p>
     * If there is no meta tag, call {@link #getRaw(MtsValue) getRaw}.<br/>
     * If the tag is a table, repeat the operation on it.<br/>
     * Otherwise attempt to call it with <code>this</code> and <code>key</code> as the arguments.
     * 
     * @see #getRaw(MtsValue)
     * @see MtsValue#METATAG_INDEX
     */
    @Override
    public MtsValue get( MtsValue key )
    {
        MtsValue result = getRaw( key );
        
        if ( result.isNil() && hasMetaTag( METATAG_INDEX ) )
        {
            MtsValue tag = getMetaTag( METATAG_INDEX );
            
            return tag.isTable() //
                    ? tag.get( key )
                    : tag.call( this, key ).get( 1 );
        }
        
        return result;
    }
    
    /**
     * Returns the value associated with the specified key in this table, or {@link MtsNil nil} if no such mapping exists.
     */
    public MtsValue getRaw( MtsValue key )
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
     * First checks if <code>key</code> is already present in this table.<br/>
     * If it is, call {@link #setRaw(MtsValue, MtsValue) setRaw}.<br/>
     * If it is not, look for a {@link MtsValue#METATAG_NEWINDEX __newindex} meta tag.
     * <p>
     * If there is no meta tag, call {@link #setRaw(MtsValue, MtsValue) setRaw}.<br/>
     * If the tag is a table, repeat the operation on it.<br/>
     * Otherwise attempt to call it with <code>this</code>, <code>key</code> and <code>value</code> as the arguments.
     * 
     * @see #setRaw(MtsValue, MtsValue)
     * @see MtsValue#METATAG_NEWINDEX
     */
    @Override
    public void set( MtsValue key, MtsValue value )
    {
        if ( !containsKey( key ) && hasMetaTag( METATAG_NEWINDEX ) )
        {
            MtsValue tag = getMetaTag( METATAG_NEWINDEX );
            
            if ( tag.isTable() )
            {
                tag.set( key, value );
            }
            else
            {
                tag.call( this, key, value );
            }
        }
        else
        {
            setRaw( key, value );
        }
    }
    
    public MtsValue setRaw( String key, MtsValue value )
    {
        return setRaw( valueOf( key ), value );
    }
    
    /**
     * Associates the specified value with the specified key in this table.
     * If the table previously contained a mapping for the key, the old value is replaced.
     * If the value is set to {@link MtsNil nil} the entry for that mapping is removed.
     * 
     * @return The previous value associated with the key, or {@link MtsNil nil} if there was no such mapping.
     */
    public MtsValue setRaw( MtsValue key, MtsValue value )
    {
        checkNotNull( key );
        checkNotNil( key, "table index is nil" );
        
        if ( TableListPart.isValidKey( key ) )
        {
            int i = (int) key.asNumber().toJava();
            
            if ( _listPart.contains( i ) )
            {
                MtsValue old = _listPart.set( i, value );
                
                if ( value.isNil() )
                {
                    _listPart.transferOrphansTo( _hashPart );
                }
                
                return old;
            }
            
            if ( _listPart.contains( i - 1 ) || ( i == 1 ) )
            {
                _listPart.add( value );
                MtsValue old = _hashPart.remove( key );
                _listPart.collectFrom( _hashPart );
                
                return old;
            }
        }
        
        return _hashPart.set( key, value );
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
     * Inserts a value into this table at the given location and shifts subsequent entries to the left if necessary.
     * <p>
     * If <tt>key</tt> is not part of the sequence of this table a simple {@link #setRaw(MtsValue, MtsValue) setRaw} is
     * performed.
     */
    public void insert( MtsValue key, MtsValue value )
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
    public MtsValue remove( MtsValue key )
    {
        checkNotNull( key );
        
        if ( _listPart.contains( key ) )
        {
            int i = (int) key.asNumber().toJava();
            MtsValue removed = _listPart.remove( i );
            
            _listPart.transferOrphansTo( _hashPart );
            
            return removed;
        }
        
        return _hashPart.remove( key );
    }
    
    public MtsValue removeLast()
    {
        return _listPart.removeLast();
    }
    
    public MtsString concat( MtsString sep, MtsNumber from, MtsNumber to )
    {
        return _listPart.concat( sep.toJava(), (int) from.toJava(), (int) to.toJava() );
    }
    
    public MtsString concat( MtsString sep, MtsNumber from )
    {
        return _listPart.concat( sep.toJava(), (int) from.toJava() );
    }
    
    public MtsString concat( MtsString sep )
    {
        return _listPart.concat( sep.toJava() );
    }
    
    // ========================================
    
    @Override
    public MtsString toStringMts()
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
    public MtsTable asTable()
    {
        return this;
    }
    
    @Override
    public MtsType getType()
    {
        return MtsType.TABLE;
    }
    
    @Override
    public MtsBoolean equalsMts( MtsValue x )
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
        protected final MtsValue key;
        
        /**
         * The value that is associated with the key.
         */
        protected MtsValue value;
        
        Entry( MtsValue k, MtsValue v )
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
}
