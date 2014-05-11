package mobtalkerscript.mts.v2.value;

import static mobtalkerscript.mts.v2.value.MtsValue.*;

import java.util.*;

import com.google.common.collect.*;

/**
 * An Array-backed list specifically tailored for MobTalkerScript.
 * <p>
 * NOTE: The index is 1 based.
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
     * NOTE: The index is 1 based.
     */
    public boolean contains( MtsValue key )
    {
        return isValidKey( key ) && ( key.asNumber().asJavaInt() < _limit );
    }
    
    /**
     * Determines if the given key resides inside the list part of this table.
     * <p>
     * NOTE: The index is 1 based.
     */
    public boolean contains( MtsNumber key )
    {
        return isValidKey( key ) && ( key.asJavaInt() <= _limit );
    }
    
    /**
     * Determines if the given index resides inside the list part of this table.
     * <p>
     * NOTE: The index is 1 based.
     */
    public boolean contains( int i )
    {
        return ( 0 < i ) && ( i <= _limit );
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
     * <p>
     * NOTE: The index is 1 based.
     */
    public void insert( int i, MtsValue value )
    {
        i--; // Adjust Lua index to Java.
        
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
     * <p>
     * NOTE: The index is 1 based.
     */
    public MtsValue set( int i, MtsValue value )
    {
        i--; // Adjust Lua index to Java.
        
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
     * NOTE: The index is 1 based.
     */
    public MtsValue remove( MtsNumber key )
    {
        int i = key.asJavaInt();
        
        if ( !contains( i ) )
            throw new IllegalArgumentException( "key is not part of this list" );
        
        return doRemove( i );
    }
    
    /**
     * Removes an entry from this list, shifting subsequent entries down.
     * <p>
     * NOTE: The index is 1 based.
     */
    public MtsValue remove( int i )
    {
        i--; // Adjust Lua index to Java.
        
        if ( _limit <= i )
            throw new ArrayIndexOutOfBoundsException( i );
        
        return doRemove( i );
    }
    
    /**
     * NOTE: Index is 0 based.
     */
    private MtsValue doRemove( int i )
    {
        MtsValue old = _entries[i];
        
        System.arraycopy( _entries, i + 1, _entries, i, _limit - i - 1 );
        
        _limit--;
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
     * NOTE: The index is 1 based.
     */
    public MtsValue get( MtsNumber key )
    {
        return get( key.asJavaInt() );
    }
    
    /**
     * NOTE: The index is 1 based.
     */
    public MtsValue get( int i )
    {
        i--; // Adjust Lua index to Java.
        
        if ( ( i < 0 ) || ( _limit <= i ) )
            throw new ArrayIndexOutOfBoundsException( i + 1 );
        
        return _entries[i];
    }
    
    /**
     * NOTE: The indices are 1 based.
     */
    public MtsString concat( String sep, int from, int to )
    {
        from--; // Adjust Lua index to Java.
        to--; // Adjust Lua index to Java.
        
        if ( ( _limit == 0 ) || ( from < 0 ) || ( _limit <= to ) || ( to < from ) )
            return EMPTY_STRING;
        
        StringBuilder s = new StringBuilder( _entries[from].toMtsString().asJavaString() );
        for ( int i = from + 1; i <= to; i++ )
        {
            s.append( sep ).append( _entries[i].toMtsString().asJavaString() );
        }
        
        return valueOf( s.toString() );
    }
    
    /**
     * NOTE: The index is 1 based.
     */
    public MtsString concat( String sep, int from )
    {
        return concat( sep, from, _limit );
    }
    
    public MtsString concat( String sep )
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
        int i = _limit;
        while ( ( i < t.length ) && ( ( value = t[i] ) != null ) )
        {
            t[i] = null;
            // ++i is needed for conversion between 1-based and 0-based indices.
            hashPart.set( valueOf( ++i ), value );
        }
    }
    
    // ========================================
    
    public Iterable<MtsValue> iterableView()
    {
        return Iterables.unmodifiableIterable( Arrays.asList( _entries ) );
    }
    
    @Override
    public Iterator<MtsValue> iterator()
    {
        return Iterators.forArray( _entries );
    }
}