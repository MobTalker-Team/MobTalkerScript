package mobtalkerscript.misl.v1.value;

import java.util.*;

import mobtalkerscript.misl.v1.*;

import com.google.common.collect.*;

public abstract class MislValue implements Comparable<MislValue>
{
    
    private static final HashMap<String, MislString> _stringCache = Maps.newHashMapWithExpectedSize( 1000 );
    private static final MislNumber[] _integerCache;
    
    static
    {
        _integerCache = new MislNumber[256];
        
        for ( int i = 0; i < 256; i++ )
        {
            _integerCache[i] = new MislNumber( i );
        }
    }
    
    // ========================================
    
    public static final MislNil NIL = new MislNil();
    
    // ========================================
    
    public static final MislBoolean TRUE = new MislBoolean( true );
    public static final MislBoolean FALSE = new MislBoolean( false );
    
    public static MislBoolean valueOf( boolean value )
    {
        return value ? TRUE : FALSE;
    }
    
    // ========================================
    
    public static MislNumber valueOf( int value )
    {
        if ( ( 0 <= value ) && ( value < _integerCache.length ) )
        {
            return _integerCache[value];
        }
        else
        {
            return new MislNumber( value );
        }
    }
    
    public static MislNumber valueOf( double value )
    {
        return new MislNumber( value );
    }
    
    // ========================================
    
    public static MislString valueOf( String value )
    {
        if ( value == null )
        {
            throw new IllegalArgumentException( "value cannot be null" );
        }
        
        MislString result = _stringCache.get( value );
        
        if ( result == null )
        {
            result = new MislString( value );
            _stringCache.put( value, result );
        }
        
        return result;
    }
    
    // ========================================
    
    public static MislArray arrayOf( MislValue... values )
    {
        return new MislArray( values );
    }
    
    // ========================================
    
//    public static MtsObject valueOf(Object value) {
//        if (value == null) {
//            throw new IllegalArgumentException("value cannot be null");
//        }
//        
//        return new MtsObject(value);
//    }
    
    // ========================================
    
    protected MislValue()
    {}
    
    // ========================================
    
    /**
     * Checks if this value is a {@link MislBoolean}.
     */
    public boolean isBoolean()
    {
        return false;
    }
    
    /**
     * Checks if this value is a {@link MislNumber}.
     */
    public boolean isNumber()
    {
        return false;
    }
    
    /**
     * Checks if this value is a {@link MislString}.
     */
    public boolean isString()
    {
        return false;
    }
    
    /**
     * Checks if this value is a {@link MislNil}.
     */
    public boolean isNil()
    {
        return false;
    }
    
    /**
     * Checks if this value is a {@link MislObject}.
     */
    public boolean isObject()
    {
        return false;
    }
    
    /**
     * Checks if this value is a {@link MislVariable}.
     */
    public boolean isVariable()
    {
        return false;
    }
    
    /**
     * Checks if this value is a {@link MislFunction}.
     */
    public boolean isFunction()
    {
        return false;
    }
    
    /**
     * Checks if this value is a {@link MislNativeFunction}.
     */
    public boolean isNativeFunction()
    {
        return false;
    }
    
    /**
     * Checks if this value is a {@link MislTable}.
     */
    public boolean isTable()
    {
        return false;
    }
    
    public boolean isArray()
    {
        return false;
    }
    
    // ========================================
    
    /**
     * Simply returns this value if it is not nil, otherwise throws an exception with the given message
     */
    public MislValue checkNotNil( String msg, Object... args )
    {
        return this;
    }
    
    // ========================================
    
    /**
     * Equivalent to a Java typecast to {@link MislBoolean}.
     */
    public MislBoolean asBoolean()
    {
        throw new ScriptRuntimeException( "Expected boolean, got " + getTypeName() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MislNumber}.
     */
    public MislNumber asNumber()
    {
        throw new ScriptRuntimeException( "Expected number, got " + getTypeName() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MislString}.
     */
    public MislString asString()
    {
        throw new ScriptRuntimeException( "Expected string, got " + getTypeName() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MislObject}.
     */
    public MislObject asObject() throws ClassCastException
    {
        throw new ScriptRuntimeException( "Expected object, got " + getTypeName() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MislFunction}.
     */
    public MislFunction asFunction()
    {
        throw new ScriptRuntimeException( "Expected function, got " + getTypeName() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MislNativeFunction}.
     */
    public MislNativeFunction asNativeFunction()
    {
        throw new ScriptRuntimeException( "Expected native function, got " + getTypeName() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MislTable}.
     */
    public MislTable asTable()
    {
        throw new ScriptRuntimeException( "Expected table, got " + getTypeName() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MislArray}.
     */
    public MislArray asArray()
    {
        throw new ScriptRuntimeException( "Expected array, got " + getTypeName() );
    }
    
    // ========================================
    
    /**
     * Resolves this value, if it is necessary (e.g. if it is a variable), or returns the value itself if
     * no resolving is needed.
     * 
     * @param env The environment used to resolve the value.
     * @return The resolved value.
     */
    public MislValue resolve( IBindings env )
    {
        return this;
    }
    
    // ========================================
    
    /**
     * Converts this value to an {@link MislString}.
     * <p>
     * Equivalent to Java's toString().
     * 
     * @return The MtsString representation of this value.
     */
    public abstract MislString toMtsString();
    
    // ========================================
    
    /**
     * Returns the name of this value type.
     * 
     * @return The name of this value type.
     */
    public abstract String getTypeName();
    
    /**
     * Equivalent to Java's equals(Object).
     * 
     * @param x The value to compare this value with.
     * @return {@link #TRUE} if both values are equal, {@link #FALSE} otherwise.
     */
    public abstract MislBoolean equal( MislValue x );
    
    // ========================================
    
    /**
     * Numbers > Strings > ...
     */
    @Override
    public int compareTo( MislValue o )
    {
        return 0;
    }
    
}