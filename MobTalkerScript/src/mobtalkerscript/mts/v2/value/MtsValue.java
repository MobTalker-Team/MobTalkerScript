package mobtalkerscript.mts.v2.value;

import java.util.*;

import mobtalkerscript.mts.v2.*;

import com.google.common.base.*;

public abstract class MtsValue implements Comparable<MtsValue>
{
    public static final MtsNil NIL = new MtsNil();
    
    public static final MtsNumber ZERO = MtsNumber.of( 0 );
    public static final MtsNumber ONE = MtsNumber.of( 1 );
    
    public static final MtsBoolean TRUE = new MtsBoolean( true );
    public static final MtsBoolean FALSE = new MtsBoolean( false );
    
    public static final MtsString EMPTY_STRING = new MtsString( "" );
    
    public static final MtsVarArgs EMPTY_VARARGS = new MtsVarArgs( Collections.<MtsValue> emptyList() );
    
    // ========================================
    
    public static MtsBoolean valueOf( boolean value )
    {
        return value ? TRUE : FALSE;
    }
    
    public static MtsNumber valueOf( int value )
    {
        return MtsNumber.of( value );
    }
    
    public static MtsNumber valueOf( double value )
    {
        return MtsNumber.of( value );
    }
    
    public static MtsString valueOf( String value )
    {
        return MtsString.of( value );
    }
    
    // ========================================
    
    /**
     * Checks if this value is a {@link MtsBoolean}.
     */
    public boolean isBoolean()
    {
        return false;
    }
    
    /**
     * Checks if this value is a {@link MtsNumber}.
     */
    public boolean isNumber()
    {
        return false;
    }
    
    /**
     * Checks if this value is a {@link MtsNumber} and an integer.
     */
    public boolean isInteger()
    {
        return false;
    }
    
    /**
     * Checks if this value is a {@link MtsNumber} and a decimal.
     */
    public boolean isDecimal()
    {
        return false;
    }
    
    /**
     * Checks if this value is a {@link MtsString}.
     */
    public boolean isString()
    {
        return false;
    }
    
    /**
     * Checks if this value is a {@link MtsNil}.
     */
    public boolean isNil()
    {
        return false;
    }
    
    /**
     * Checks if this value is a {@link MtsObject}.
     */
    public boolean isObject()
    {
        return false;
    }
    
    /**
     * Checks if this value is a {@link MtsClosure}.
     */
    public boolean isClosure()
    {
        return false;
    }
    
    /**
     * Checks if this value is a {@link MtsFunction}.
     */
    public boolean isFunction()
    {
        return false;
    }
    
    /**
     * Checks if this value is a {@link MtsTable}.
     */
    public boolean isTable()
    {
        return false;
    }
    
    /**
     * Checks if this value is a {@link MtsVarArgs}.
     */
    public boolean isVarArgs()
    {
        return false;
    }
    
    // ========================================
    
    /**
     * Equivalent to a Java typecast to {@link MtsBoolean}.
     */
    public MtsBoolean asBoolean()
    {
        throw new ScriptRuntimeException( "Expected " + MtsType.BOOLEAN + ", got " + getType() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MtsNumber}.
     * <p>
     * If this value is a string an automatic coercion into a number is attempted.
     */
    public MtsNumber asNumber()
    {
        throw new ScriptRuntimeException( "Expected " + MtsType.NUMBER + ", got " + getType() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MtsString}.
     * <p>
     * If this value is a number it is automatically coerced to a string.
     */
    public MtsString asString()
    {
        throw new ScriptRuntimeException( "Expected " + MtsType.STRING + ", got " + getType() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MtsObject}.
     */
    public MtsObject asObject()
    {
        throw new ScriptRuntimeException( "Expected " + MtsType.OBJECT + ", got " + getType() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MtsClosure}.
     */
    public MtsClosure asClosure()
    {
        throw new ScriptRuntimeException( "Expected " + MtsType.FUNCTION + ", got " + getType() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MtsFunction}.
     */
    public MtsFunction asFunction()
    {
        throw new ScriptRuntimeException( "Expected " + MtsType.FUNCTION + ", got " + getType() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MtsTable}.
     */
    public MtsTable asTable()
    {
        throw new ScriptRuntimeException( "Expected " + MtsType.TABLE + ", got " + getType() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MtsVarArgs}.
     */
    public MtsVarArgs asVarArgs()
    {
        throw new ScriptRuntimeException( "Expected " + MtsType.VARARGS + ", got " + getType() );
    }
    
    // ========================================
    
    public static final MtsString __INDEX = valueOf( "__index" );
    public static final MtsString __NEWINDEX = valueOf( "__newindex" );
    public static final MtsString __LEN = valueOf( "__len" );
    public static final MtsString __CALL = valueOf( "__call" );
    public static final MtsString __EQ = valueOf( "__eq" );
    public static final MtsString __LT = valueOf( "__lt" );
    public static final MtsString __LTE = valueOf( "__lte" );
    public static final MtsString __TOSTRING = valueOf( "__tostring" );
    
    /**
     * Determines if this value has a meta table set.
     * <p>
     * <b>Note:</b> If this method returns <code>true</code>, {@link #getMetaTable()} has to return a non-null value.
     */
    public boolean hasMetaTable()
    {
        return false;
    }
    
    public MtsTable getMetaTable()
    {
        return null;
    }
    
    public void setMetaTable( MtsTable table )
    {}
    
    public boolean hasMetaTag( MtsString tag )
    {
        return false;
    }
    
    public final MtsValue getMetaTag( MtsString tag )
    {
        return hasMetaTable() ? getMetaTable().get( tag ) : NIL;
    }
    
    // ========================================
    
    public final MtsValue get( String key )
    {
        return get( valueOf( key ) );
    }
    
    public final MtsValue get( MtsValue key )
    {
        return get( key, true );
    }
    
    /**
     * Attempts to index this value using the given key, with optional meta tag processing.
     * <p>
     * Describes the indexing access operation <code>table[key]</code>.
     * <p>
     * If no value for <code>key</code> can be found and <code>useMetaTag</code> is <code>true</code>, the {@value #__INDEX}
     * meta tag will be used to retrieve a value for <code>key</code>. If it is an
     * 
     * @param key The key used to index this value.
     * @param useMetaTag Specifies if the {@value #__INDEX} meta tag should be used.
     * @return The result of the lookup for a value for <code>key</code>.
     * @throws ScriptRuntimeException If this value cannot be indexed.
     */
    public MtsValue get( MtsValue key, boolean useMetaTag )
    {
        throw new ScriptRuntimeException( "attempt to index a %s value", getType() );
    }
    
    // ========================================
    
    /**
     * Differs from {@link #get(MtsValue)} in that it always returns <code>this</code> when <code>i</code> is <code>1</code> and
     * no meta method has been set.
     * <p>
     * 0 based.
     */
    public MtsValue get( int i )
    {
        return i == 0 ? this : NIL;
    }
    
    // ========================================
    
    public final void set( String key, MtsValue value )
    {
        set( key, value, true );
    }
    
    public final void set( String key, MtsValue value, boolean useMetaTag )
    {
        set( valueOf( key ), value, useMetaTag );
    }
    
    public final void set( MtsValue key, MtsValue value )
    {
        set( key, value, true );
    }
    
    /**
     * Attempts to index this value and set the value of a given key, with optional meta tag processing.
     * <p>
     * Describes the indexing assignment operation <code>table[key] = value</code>.
     * <p>
     * If <code>key</code> does not exist already and <code>useMetaTag</code> is <code>true</code>, the {@value #__NEWINDEX}
     * meta tag is tried. If it is a {@link MtsFunction} invoke <code>call(this, key, value)</code> on it. Otherwise this
     * operation is repeated on it with the same arguments.
     * 
     * @param key The key to set the value for.
     * @param value The value to set for key.
     * @param useMetaTag Specifies if the {@value #__NEWINDEX} meta tag should be used.
     */
    public void set( MtsValue key, MtsValue value, boolean useMetaTag )
    {
        throw new ScriptRuntimeException( "attempt to index a %s value", getType() );
    }
    
    // ========================================
    
    public final MtsVarArgs call()
    {
        return call( MtsValue.EMPTY_VARARGS );
    }
    
    public final MtsVarArgs call( MtsValue arg )
    {
        return call( new MtsVarArgs( arg ) );
    }
    
    public final MtsVarArgs call( MtsValue arg1, MtsValue arg2 )
    {
        return call( new MtsVarArgs( arg1, arg2 ) );
    }
    
    public final MtsVarArgs call( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
    {
        return call( new MtsVarArgs( arg1, arg2, arg3 ) );
    }
    
    public MtsVarArgs call( MtsVarArgs args )
    {
        throw new ScriptRuntimeException( "attempt to call a %s value", getType() );
    }
    
    // ========================================
    
    public MtsNumber getLength()
    {
        throw new ScriptRuntimeException( "attempt to get length of a %s value", getType() );
    }
    
    // ========================================
    
    /**
     * Converts this value to an {@link MtsString}.
     * <p>
     * Equivalent to Java's toString().
     * 
     * @return The MtsString representation of this value.
     */
    public MtsString toMtsString()
    {
        return valueOf( toString() );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return getType() + ": " + formatHashCode( hashCode() );
    }
    
    // ========================================
    
    /**
     * Returns the type of this value.
     */
    public abstract MtsType getType();
    
    /**
     * Equivalent to Java's equals(Object).
     * 
     * @param x The value to compare this value with.
     * @return {@link #TRUE} if both values are equal, {@link #FALSE} otherwise.
     */
    public MtsBoolean isMtsEqual( MtsValue x )
    {
        return valueOf( equals( x ) );
    }
    
    // ========================================
    
    @Override
    public int compareTo( MtsValue o )
    {
        return 0;
    }
    
    // ========================================
    
    /* package */static String formatHashCode( int hashCode )
    {
        return Strings.padStart( Integer.toHexString( hashCode ), 8, '0' );
    }
    
}