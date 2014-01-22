package mobtalkerscript.misl.v1.value;

import java.util.*;

import mobtalkerscript.misl.v1.*;

import com.google.common.base.*;
import com.google.common.collect.*;

public abstract class MislValue implements MislConstants, Comparable<MislValue>
{
    
    private static final HashMap<String, MislString> _stringCache = Maps.newHashMapWithExpectedSize( 1000 );
    private static final MislNumber[] _integerCache;
    
    static
    {
        _integerCache = new MislNumber[256];
        
        _integerCache[0] = ZERO;
        _integerCache[1] = ONE;
        for ( int i = 2; i < 256; i++ )
        {
            _integerCache[i] = new MislNumber( i );
        }
    }
    
    // ========================================
    
    public static MislBoolean valueOf( boolean value )
    {
        return value ? TRUE : FALSE;
    }
    
    // ========================================
    
    public static MislNumber valueOf( int value )
    {
        if ( ( 0 <= value ) && ( value < _integerCache.length ) )
            return _integerCache[value];
        else
            return new MislNumber( value );
    }
    
    public static MislNumber valueOf( double value )
    {
        return new MislNumber( value );
    }
    
    // ========================================
    
    public static MislString valueOf( String value )
    {
        if ( value == null )
            throw new IllegalArgumentException( "value cannot be null" );
        
        if ( value.length() == 0 )
            return EMPTY_STRING;
        if ( value.length() > 255 )
            return new MislString( value );
        
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
     * Checks if this value is a {@link MislNumber} and an integer.
     */
    public boolean isInteger()
    {
        return false;
    }
    
    /**
     * Checks if this value is a {@link MislNumber} and a decimal.
     */
    public boolean isDecimal()
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
     * Checks if this value is a {@link MislClosure}.
     */
    public boolean isClosure()
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
     * Equivalent to a Java typecast to {@link MislBoolean}.
     */
    public MislBoolean asBoolean()
    {
        throw new ScriptRuntimeException( "Expected " + TYPENAME_BOOLEAN + ", got " + getTypeName() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MislNumber}.
     */
    public MislNumber asNumber()
    {
        throw new ScriptRuntimeException( "Expected " + TYPENAME_NUMBER + ", got " + getTypeName() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MislString}.
     */
    public MislString asString()
    {
        throw new ScriptRuntimeException( "Expected " + TYPENAME_STRING + ", got " + getTypeName() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MislObject}.
     */
    public MislObject<?> asObject() throws ClassCastException
    {
        throw new ScriptRuntimeException( "Expected " + TYPENAME_JOBJECT + ", got " + getTypeName() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MislClosure}.
     */
    public MislClosure asClosure()
    {
        throw new ScriptRuntimeException( "Expected " + TYPENAME_FUNCTION + ", got " + getTypeName() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MislFunction}.
     */
    public MislFunction asFunction()
    {
        throw new ScriptRuntimeException( "Expected " + TYPENAME_FUNCTION + ", got " + getTypeName() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MislTable}.
     */
    public MislTable asTable()
    {
        throw new ScriptRuntimeException( "Expected " + TYPENAME_TABLE + ", got " + getTypeName() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MislArray}.
     */
    public MislArray asArray()
    {
        throw new ScriptRuntimeException( "Expected " + TYPENAME_ARRAY + ", got " + getTypeName() );
    }
    
    // ========================================
    
    public static final MislString METATAG_INDEX = valueOf( "__index" );
    public static final MislString METATAG_NEWINDEX = valueOf( "__newindex" );
    public static final MislString METATAG_CALL = valueOf( "__call" );
    public static final MislString METATAG_EQUALS = valueOf( "__eq" );
    
    public MislTable getMetaTable()
    {
        throw new ScriptRuntimeException( "attempt to get metatable from a %s value", getTypeName() );
    }
    
    public MislValue setMetaTable( MislTable t )
    {
        throw new ScriptRuntimeException( "attempt to set metatable for a %s value", getTypeName() );
    }
    
    public boolean hasMetaTag( MislString tag )
    {
        return false;
    }
    
    // ========================================
    
    public MislValue get( MislValue key )
    {
        throw new ScriptRuntimeException( "attempt to index a %s value", getTypeName() );
    }
    
    public MislValue get( String key )
    {
        return get( valueOf( key ) );
    }
    
    /**
     * Differs from {@link #get(MislValue)} in that it always returns <code>this</code> when <code>i</code> is <code>1</code>
     * and no meta method has been set.
     */
    public MislValue get( int i )
    {
        return i == 1 ? this : NIL;
    }
    
    public MislValue set( MislValue key, MislValue value )
    {
        throw new ScriptRuntimeException( "attempt to index a %s value", getTypeName() );
    }
    
    public MislValue set( String key, MislValue value )
    {
        return set( valueOf( key ), value );
    }
    
    public MislValue call( MislValue... args )
    {
        throw new ScriptRuntimeException( "attempt to call a %s value", getTypeName() );
    }
    
    // ========================================
    
    /**
     * Converts this value to an {@link MislString}.
     * <p>
     * Equivalent to Java's toString().
     * 
     * @return The MtsString representation of this value.
     */
    public MislString toStringMts()
    {
        return valueOf( toString() );
    }
    
    @Override
    public String toString()
    {
        return getTypeName() + ": " + formatHashCode( hashCode() );
    }
    
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
    public MislBoolean equalsMts( MislValue x )
    {
        return valueOf( equals( x ) );
    }
    
    // ========================================
    
    @Override
    public int compareTo( MislValue o )
    {
        return 0;
    }
    
    // ========================================
    
    public static void checkNotNil( MislValue value, String msg, Object... args )
    {
        if ( value.isNil() )
            throw new ScriptRuntimeException( msg, args );
    }
    
    public static void checkMislArgument( String functionName,
                                          MislValue[] args,
                                          int i,
                                          Class<? extends MislValue> expected )
    {
        if ( args.length < i )
        { //
            throw new ScriptRuntimeException( "bad argument #%s to '%s' (expected %s, got no value)",
                                              i,
                                              functionName,
                                              typeOf( expected ) );
        }
        else if ( !expected.isInstance( args[i] ) )
        { //
            throw new ScriptRuntimeException( "bad argument #%s to '%s' (expected %s, got %s)",
                                              i,
                                              functionName,
                                              typeOf( expected ),
                                              args[i].getTypeName() );
        }
    }
    
    // ========================================
    
    public static String formatHashCode( int hashCode )
    {
        return Strings.padStart( Integer.toHexString( hashCode ), 8, '0' );
    }
    
    public static String typeOf( MislValue value )
    {
        return value.getTypeName();
    }
    
    public static String typeOf( Class<? extends MislValue> clazz )
    {
        if ( clazz == MislNil.class )
            return TYPENAME_NIL;
        if ( clazz == MislNumber.class )
            return TYPENAME_NUMBER;
        if ( clazz == MislBoolean.class )
            return TYPENAME_BOOLEAN;
        if ( clazz == MislString.class )
            return TYPENAME_STRING;
        if ( clazz == MislTable.class )
            return TYPENAME_TABLE;
        if ( clazz == MislFunction.class )
            return TYPENAME_FUNCTION;
        if ( clazz == MislObject.class )
            return TYPENAME_JOBJECT;
        if ( MislFunction.class.isAssignableFrom( clazz ) )
            return TYPENAME_FUNCTION;
        
        throw new ScriptEngineException( "Unknown type: " + clazz.getName() );
    }
    
}