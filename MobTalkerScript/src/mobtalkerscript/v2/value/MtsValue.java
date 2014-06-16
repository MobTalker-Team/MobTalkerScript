package mobtalkerscript.v2.value;

import java.util.*;

import mobtalkerscript.v2.*;

import com.google.common.base.*;

public abstract class MtsValue implements Comparable<MtsValue>
{
    public static final MtsNil NIL = new MtsNil();
    
    public static final MtsNumber ZERO = MtsNumber.of( 0 );
    public static final MtsNumber ONE = MtsNumber.of( 1 );
    
    public static final MtsBoolean TRUE = new MtsBoolean( true );
    public static final MtsBoolean FALSE = new MtsBoolean( false );
    
    public static final MtsString EMPTY_STRING = new MtsString( "" );
    
    public static final MtsVarArgs EMPTY_VARARGS = new EvaluatedVarArgs( Collections.<MtsValue> emptyList() );
    
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
    
    public static boolean isTrue( MtsValue v )
    {
        return ( v != FALSE ) && ( v != NIL );
    }
    
    // ========================================
    
    public static final MtsString __METATABLE = valueOf( "__metatable" );
    
    public static final MtsString __INDEX = valueOf( "__index" );
    public static final MtsString __NEWINDEX = valueOf( "__newindex" );
    public static final MtsString __LENGTH = valueOf( "__len" );
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
        return getType().hasMetaTable();
    }
    
    public MtsTable getMetaTable()
    {
        return getType().getMetaTable();
    }
    
    public void setMetaTable( MtsValue table )
    {
        new ScriptRuntimeException( "attempt to set metatable of a %s value", getType() );
    }
    
    public boolean hasMetaTag( MtsString tag )
    {
        return hasMetaTable() && getMetaTable().containsKey( tag );
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
    
    public final MtsValue get( String key, boolean useMetaTag )
    {
        return get( valueOf( key ), useMetaTag );
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
    public final MtsValue get( MtsValue key, boolean useMetaTag )
    {
        MtsValue result = doGet( key );
        
        if ( result.isNil() && useMetaTag )
        {
            MtsValue tag = getMetaTag( __INDEX );
            
            if ( tag.isFunction() )
                result = tag.call( this, key );
            else if ( !tag.isNil() )
                result = tag.get( key ).get();
        }
        
        return result;
    }
    
    protected MtsValue doGet( MtsValue key )
    {
        throw new ScriptRuntimeException( "attempt to index a %s value", getType() );
    }
    
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
    
    public final MtsValue get()
    {
        return get( 0 );
    }
    
    // ========================================
    
    /**
     * Same as calling {@link #set(String, MtsValue, boolean) set(key, value, false)}
     */
    public final void set( String key, MtsValue value )
    {
        set( key, value, false );
    }
    
    /**
     * Same as calling {@link #set(MtsValue, MtsValue, boolean) set(valueOf(key), value, useMetaTag)}
     */
    public final void set( String key, MtsValue value, boolean useMetaTag )
    {
        set( valueOf( key ), value, useMetaTag );
    }
    
    /**
     * Same as calling {@link #set(MtsValue, MtsValue, boolean) set(key, value, false)}
     */
    public final void set( MtsValue key, MtsValue value )
    {
        set( key, value, false );
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
    public final void set( MtsValue key, MtsValue value, boolean useMetaTag )
    {
        if ( useMetaTag )
        {
            MtsValue tag = getMetaTag( __NEWINDEX );
            
            if ( tag.isNil() )
                doSet( key, value );
            else if ( tag.isFunction() )
                tag.call( this, key, value );
            else
                tag.set( key, value );
        }
        else
        {
            doSet( key, value );
        }
    }
    
    protected void doSet( MtsValue key, MtsValue value )
    {
        throw new ScriptRuntimeException( "attempt to index a %s value", getType() );
    }
    
    // ========================================
    
    public final MtsValue call()
    {
        return call( MtsValue.EMPTY_VARARGS );
    }
    
    public final MtsValue call( MtsValue arg )
    {
        return call( MtsVarArgs.of( arg ) );
    }
    
    public final MtsValue call( MtsValue arg1, MtsValue arg2 )
    {
        return call( MtsVarArgs.of( arg1, arg2 ) );
    }
    
    public final MtsValue call( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
    {
        return call( MtsVarArgs.of( arg1, arg2, arg3 ) );
    }
    
    public MtsValue call( MtsVarArgs args )
    {
        MtsValue tag = getMetaTag( __CALL );
        
        if ( tag.isNil() )
            return doCall( args );
        
        return tag.call( this, args );
    }
    
    protected MtsValue doCall( MtsVarArgs args )
    {
        throw new ScriptRuntimeException( "attempt to call a %s value", getType() );
    }
    
    // ========================================
    
    public final MtsNumber getLength()
    {
        MtsValue tag = getMetaTag( __LENGTH );
        
        if ( tag.isNil() )
            return doGetLength();
        
        return tag.call( this ).get().asNumber();
    }
    
    protected MtsNumber doGetLength()
    {
        throw new ScriptRuntimeException( "attempt to get length of a %s value", getType() );
    }
    
    // ========================================
    
    public final MtsBoolean isEqual( MtsValue other )
    {
        MtsValue tag = getMetaTag( __EQ );
        
        if ( tag.isNil() )
            return doIsEqual( other );
        
        return tag.call( this ).get().toMtsBoolean();
    }
    
    protected MtsBoolean doIsEqual( MtsValue other )
    {
        return valueOf( equals( other ) );
    }
    
    public final MtsBoolean isLess( MtsValue other )
    {
        MtsValue tag = getMetaTag( __LT );
        
        if ( tag.isNil() )
            return doIsLess( other );
        
        return tag.call( this ).get().toMtsBoolean();
    }
    
    protected MtsBoolean doIsLess( MtsValue other )
    {
        throw new ScriptRuntimeException( "attempt to compare %s with %s", getType(), other.getType() );
    }
    
    public final MtsBoolean isLessOrEqual( MtsValue other )
    {
        MtsValue tag = getMetaTag( __LTE );
        
        if ( tag.isNil() )
            return doIsLessOrEqual( other );
        
        return tag.call( this ).get().toMtsBoolean();
    }
    
    protected MtsBoolean doIsLessOrEqual( MtsValue other )
    {
        throw new ScriptRuntimeException( "attempt to compare %s with %s", getType(), other.getType() );
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
     * Checks if this value is a {@link MtsUserdata}.
     */
    public boolean isUserdata()
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
     * Checks if this value is a {@link EvaluatedVarArgs}.
     */
    public boolean isVarArgs()
    {
        return false;
    }
    
    /**
     * Checks if this value is of the given type.
     * <p>
     * If this fails and this type is a table, this method tries to compare the Metatables of this value and the of the given
     * type, if any.
     */
    public boolean is( MtsType type )
    {
        if ( getType().equals( type ) )
            return true;
        
        // Try metatable comparison
        if ( isTable() && type.hasMetaTable() && hasMetaTable() )
            return type.getMetaTable().equals( getMetaTable() );
        
        return false;
    }
    
    // ========================================
    
    /**
     * Equivalent to a Java typecast to {@link MtsBoolean}.
     */
    public MtsBoolean asBoolean()
    {
        throw new WrongTypeException( MtsType.BOOLEAN, getType() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MtsNumber}.
     * <p>
     * If this value is a string an automatic coercion into a number is attempted.
     */
    public MtsNumber asNumber()
    {
        throw new WrongTypeException( MtsType.NUMBER, getType() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MtsString}.
     * <p>
     * If this value is a number it is automatically coerced to a string.
     */
    public MtsString asString()
    {
        throw new WrongTypeException( MtsType.STRING, getType() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MtsUserdata}.
     */
    public MtsUserdata asUserdata()
    {
        throw new WrongTypeException( MtsType.USERDATA, getType() );
    }
    
    public <T> T asNative()
    {
        throw new WrongTypeException( MtsType.USERDATA, getType() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MtsClosure}.
     */
    public MtsClosure asClosure()
    {
        throw new WrongTypeException( MtsType.FUNCTION, getType() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MtsFunction}.
     */
    public MtsFunction asFunction()
    {
        throw new WrongTypeException( MtsType.FUNCTION, getType() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MtsTable}.
     */
    public MtsTable asTable()
    {
        throw new WrongTypeException( MtsType.TABLE, getType() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link EvaluatedVarArgs}.
     */
    public MtsVarArgs asVarArgs()
    {
        throw new WrongTypeException( MtsType.VARARGS, getType() );
    }
    
    // ========================================
    
    public MtsBoolean toMtsBoolean()
    {
        return TRUE;
    }
    
    public MtsNumber toMtsNumber() throws NumberFormatException
    {
        throw new NumberFormatException( "attempt to convert " + getType() + " to " + MtsType.NUMBER );
    }
    
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