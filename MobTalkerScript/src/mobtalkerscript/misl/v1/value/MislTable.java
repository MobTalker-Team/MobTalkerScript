package mobtalkerscript.misl.v1.value;

import static com.google.common.base.Preconditions.*;

import com.google.common.base.*;

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
    
    private static MislValue[] EMPTY_LISTPART = new MislValue[0];
    private static HashEntry[] EMPTY_HASHPART = new HashEntry[0];
    
    /**
     * The maximum capacity, used if a higher value is implicitly specified
     * by either of the constructors with arguments.
     * MUST be a power of two <= 1<<30.
     */
    public static final int MAXIMUM_CAPACITY = 1 << 30;
    
    /**
     * The load factor used when none specified in constructor.
     */
    public static final float LOAD_FACTOR = 0.75f;
    
    // ========================================
    
    private MislValue[] _listPart;
    private int _listLimit;
    
    private HashEntry[] _hashPart;
    private int _hashThreshold;
    private int _hashSize;
    
    private int _size;
    
    private MislTable _metaTable;
    
    // ========================================
    
    public MislTable( int initialCapacity )
    {
        _listPart = new MislValue[10];
        _listLimit = 0;
        
        int capacity = 1;
        while ( capacity < initialCapacity )
        {
            capacity <<= 1;
        }
        
        _hashPart = new HashEntry[capacity];
        _hashSize = 0;
        _hashThreshold = (int) ( capacity * LOAD_FACTOR );
        
        _size = 0;
    }
    
    // ========================================
    
    /**
     * Determines if the given key is valid for the list part of a table.
     */
    public static boolean isValidArrayKey( MislValue key )
    {
        if ( !key.isNumber() )
        {
            return false;
        }
        
        return isValidArrayKey( key.asNumber() );
    }
    
    public static boolean isValidArrayKey( MislNumber key )
    {
        return key.isPositive() && key.isInteger();
    }
    
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
     * Returns 0 for <code>null</code> or {@link MislNil nil} values.
     */
    private final static int getHashFor( MislValue o )
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
     * Determines if the given key resides inside the list part of this table.
     */
    private final boolean isEntryInList( MislValue key )
    {
        return isValidArrayKey( key ) && ( key.asNumber().toJava() < _listLimit );
    }
    
    private final boolean isEntryInList( MislNumber key )
    {
        return isValidArrayKey( key ) && ( key.toJava() < _listLimit );
    }
    
    // ========================================
    // List Operations
    
    private MislValue removeListEntry( int i )
    {
        MislValue oldValue = _listPart[i];
        _listPart[i] = null;
        
        presizeHashPart( ( _listLimit - i ) + _hashSize );
        transferListToHash( i + 1, _listLimit );
        _listLimit = i;
        
        return oldValue;
    }
    
    private void resizeListPart( int newCapacity )
    {
        MislValue[] newList = new MislValue[newCapacity];
        System.arraycopy( _listPart, 0, newList, 0, _listPart.length );
        _listPart = newList;
    }
    
    /**
     * Transfers all list entries starting from the given list index to the hash part.
     */
    private void transferListToHash( int from, int to )
    {
        for ( int i = from; i < to; i++ )
        {
            MislValue key = valueOf( i );
            addHashEntry( key,
                          getHashFor( key ),
                          indexFor( getHashFor( key ), _hashPart.length ),
                          _listPart[i] );
        }
    }
    
    private void addListEntry( int i, MislValue value )
    {
        if ( _listPart.length <= i )
        {
            resizeListPart( 2 * _listPart.length );
        }
        
        _listPart[i] = value;
        _listLimit++;
    }
    
    // ========================================
    // Hash Operations
    
    /**
     * Removes and returns the entry associated with the specified key
     * in the HashMap. Returns null if the HashMap contains no mapping
     * for this key.
     */
    private MislValue removeHashEntry( MislValue key )
    {
        int hash = getHashFor( key );
        int i = indexFor( hash, _hashPart.length );
        
        HashEntry prev = _hashPart[i];
        HashEntry e = prev;
        
        while ( e != null )
        {
            HashEntry next = e.next;
            if ( ( e.hash == hash ) && Objects.equal( key, e.key ) )
            {
                _size--;
                _hashSize--;
                
                if ( prev == e )
                {
                    _hashPart[i] = next;
                }
                else
                {
                    prev.next = next;
                }
                
                return e.value;
            }
            
            prev = e;
            e = next;
        }
        
        return null;
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
    private void presizeHashPart( int minCapacity )
    {
        if ( minCapacity > _hashThreshold )
        {
            int targetCapacity = (int) ( ( minCapacity / LOAD_FACTOR ) + 1 );
            if ( targetCapacity > MAXIMUM_CAPACITY )
            {
                targetCapacity = MAXIMUM_CAPACITY;
            }
            int newCapacity = _hashPart.length;
            while ( newCapacity < targetCapacity )
            {
                newCapacity <<= 1;
            }
            if ( newCapacity > _hashPart.length )
            {
                resizeHashPart( newCapacity );
            }
        }
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
    private void resizeHashPart( int newCapacity )
    {
        HashEntry[] oldTable = _hashPart;
        int oldCapacity = oldTable.length;
        if ( oldCapacity == MAXIMUM_CAPACITY )
        {
            _hashThreshold = Integer.MAX_VALUE;
            return;
        }
        
        HashEntry[] newTable = new HashEntry[newCapacity];
        transferHashPart( _hashPart, newTable );
        _hashPart = newTable;
        _hashThreshold = (int) ( newCapacity * LOAD_FACTOR );
    }
    
    /**
     * Transfers all entries from current table to newTable (rehash the table).
     */
    private void transferHashPart( HashEntry[] oldTable, HashEntry[] newTable )
    {
        int newCapacity = newTable.length;
        
        for ( int j = 0; j < oldTable.length; j++ )
        {
            HashEntry entry = oldTable[j];
            if ( entry != null )
            {
                oldTable[j] = null;
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
    
//    /**
//     * Finds and transfers every hash entry whose key is an integer and within [0, listLimit) to the list part of this table.
//     */
//    private void transferHashToList()
//    {
//        for ( int i = 0; i < _hashPart.length; i++ )
//        {
//            HashEntry prev = _hashPart[i];
//            HashEntry e = prev;
//            
//            while ( e != null )
//            {
//                HashEntry next = e.next;
//                
//                if ( e.key.isNumber() )
//                {
//                    MislNumber key = e.key.asNumber();
//                    if ( isEntryInList( key ) )
//                    {
//                        _hashSize--;
//                        
//                        if ( prev == e )
//                        {
//                            _hashPart[i] = next;
//                        }
//                        else
//                        {
//                            prev.next = next;
//                        }
//                        
//                        _listPart[(int) key.toJava()] = e.value;
//                    }
//                }
//                
//                prev = e;
//                e = next;
//            }
//        }
//    }
    
    private void addHashEntry( MislValue key, int hash, int i, MislValue value )
    {
        HashEntry e = _hashPart[i];
        _hashPart[i] = new HashEntry( key, hash, value, e );
        
        _size++;
        if ( _hashSize++ >= _hashThreshold )
        {
            resizeHashPart( 2 * _hashPart.length );
        }
    }
    
    // ========================================
    
    public int count()
    {
        return _listLimit + _hashSize;
    }
    
    public int listSize()
    {
        return _listLimit;
    }
    
    public boolean isEmpty()
    {
        return _size == 0;
    }
    
    public boolean containsKey( MislValue key )
    {
        return !isEmpty() && ( getEntry( key ) != null );
    }
    
    /**
     * Returns the entry associated with the specified key in the
     * HashMap. Returns null if the HashMap contains no mapping
     * for the key.
     */
    private HashEntry getEntry( MislValue key )
    {
        int hash = getHashFor( key );
        
        for ( HashEntry e = _hashPart[indexFor( hash, _hashPart.length )]; e != null; e = e.next )
        {
            if ( ( e.hash == hash ) && Objects.equal( key, e.key ) )
            {
                return e;
            }
        }
        
        return null;
    }
    
    /**
     * Finds the first key in this table and returns it, or {@link MislNil nil} if this table is empty.
     */
    public Entry getFirstEntry()
    {
        if ( isEmpty() )
        {
            return null;
        }
        
        MislValue[] list = _listPart;
        for ( int i = 0; i < list.length; i++ )
        {
            if ( list[i] != null )
            {
                return new Entry( valueOf( i ), list[i] );
            }
        }
        
        HashEntry[] table = _hashPart;
        for ( int i = 0; i < table.length; i++ )
        {
            HashEntry e = table[i];
            if ( e != null )
            {
                return e;
            }
        }
        
        throw new IllegalStateException( "Did not find any entry in a non-empty table!" );
    }
    
    /**
     * Stateless iterator function.
     */
    public Entry getEntryAfter( MislValue key )
    {
        checkNotNull( key );
        
        if ( isEmpty() )
        {
            return null;
        }
        
        // Get first entry if key is nil
        if ( key.isNil() )
        {
            return getFirstEntry();
        }
        
        // Array part
        if ( isEntryInList( key ) )
        {
            MislValue[] list = _listPart;
            int i = (int) key.asNumber().toJava();
            
            while ( ++i < list.length )
            {
                if ( list[i] != null )
                {
                    return new Entry( valueOf( i ), list[i] );
                }
            }
        }
        
        // Hash part
        HashEntry next;
        int hash = getHashFor( key );
        HashEntry[] table = _hashPart;
        
        int i = indexFor( hash, _hashPart.length );
        for ( next = _hashPart[i]; next != null; next = next.next )
        {
            if ( ( next.hash == hash ) && Objects.equal( key, next.key ) )
            {
                next = next.next;
                break;
            }
        }
        
        if ( next != null )
        {
            return next;
        }
        
        while ( ( i < table.length ) && ( next == null ) )
        {
            next = table[i++];
        }
        
        return next;
    }
    
    public MislValue rawget( MislValue key )
    {
        checkNotNull( key );
        
        if ( isEmpty() || key.isNil() )
        {
            return NIL;
        }
        
        // Look in list part first
        if ( isEntryInList( key ) )
        {
            int i = (int) key.asNumber().toJava();
            return _listPart[i];
        }
        
        // Lookup the value in the hash part
        return getEntry( key ).value;
    }
    
    // ========================================
    
    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     * If the value is set to <code>null</code> or {@link MislNil nil} the entry for that mapping is removed.
     * 
     * @return The previous value associated with the key, or {@link MislNil nil} if there was no mapping for <tt>key</tt>.
     */
    public MislValue rawset( MislValue key, MislValue value )
    {
        checkNotNull( key );
        
        if ( ( value == null ) || value.isNil() )
        {
            return remove( key );
        }
        
        if ( isEntryInList( key ) )
        {
            int i = (int) key.asNumber().toJava();
            
            MislValue oldValue = _listPart[i];
            _listPart[i] = value;
            return oldValue;
        }
        
        int hash = getHashFor( key );
        int hashIndex = indexFor( hash, _hashPart.length );
        
        for ( HashEntry entry = _hashPart[hashIndex]; entry != null; entry = entry.next )
        {
            if ( ( entry.hash == hash ) && Objects.equal( key, entry.key ) )
            {
                MislValue oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
        }
        
        // No entry exists yet, create a new one
        if ( isValidArrayKey( key ) )
        {
            int i = (int) key.asNumber().toJava();
            
            if ( i == _listLimit )
            {
                removeHashEntry( key );
                addListEntry( i, value );
                return NIL;
            }
        }
        
        addHashEntry( key, hash, hashIndex, value );
        return NIL;
    }
    
    /**
     * Removes a mapping from this table.
     * <p>
     * If this causes a gap in the list part, it will be resized.
     */
    public MislValue remove( MislValue key )
    {
        checkNotNull( key );
        
        return isEntryInList( key )
                ? removeListEntry( (int) key.asNumber().toJava() )
                : removeHashEntry( key );
    }
    
    // ========================================
    
    @Override
    public MislTable asTable()
    {
        return this;
    }
    
    // ========================================
    
    @Override
    public MislString toMtsString()
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
    public String getTypeName()
    {
        return "table";
    }
    
    @Override
    public MislBoolean equal( MislValue x )
    {
        return valueOf( this == x );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "table#" + Strings.padStart( Integer.toHexString( hashCode() ), 8, '0' );
    }
    
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
        final MislValue key;
        
        /**
         * The value that is associated with the key.
         */
        MislValue value;
        
        Entry( MislValue k, MislValue v )
        {
            key = k;
            value = v;
        }
    }
    
    /**
     * Each entry is a Key-Value mapping as well as a bucket.
     * Values with the same hash are appended to each other in single-linked list style.
     */
    private static class HashEntry extends Entry
    {
        
        /**
         * Hash of the key when this entry was created.
         */
        final int hash;
        
        /**
         * The next entry in the bucket list or <code>null</code> if this entry is not a bucket.
         */
        HashEntry next;
        
        HashEntry( MislValue key, int hash, MislValue value, HashEntry next )
        {
            super( key, value );
            this.hash = hash;
            this.next = next;
        }
    }
    
}
