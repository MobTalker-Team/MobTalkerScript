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
    
    public static final MtsVarArgs EMPTY_VARARGS = new MtsVarArgs();
    
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
    public MtsObject<?> asObject() throws ClassCastException
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
    
    public static final MtsString METATAG_INDEX = valueOf( "__index" );
    public static final MtsString METATAG_NEWINDEX = valueOf( "__newindex" );
    public static final MtsString METATAG_CALL = valueOf( "__call" );
    public static final MtsString METATAG_EQUALS = valueOf( "__eq" );
    
    public boolean hasMetaTable()
    {
        return false;
    }
    
    public MtsTable getMetaTable()
    {
        throw new ScriptRuntimeException( "attempt to get metatable from a %s value", getType() );
    }
    
    public void setMetaTable( MtsTable t )
    {
        throw new ScriptRuntimeException( "attempt to set metatable for a %s value", getType() );
    }
    
    // ========================================
    
    public MtsValue get( MtsValue key )
    {
        throw new ScriptRuntimeException( "attempt to index a %s value", getType() );
    }
    
    public MtsValue get( String key )
    {
        return get( valueOf( key ) );
    }
    
    /**
     * Differs from {@link #get(MtsValue)} in that it always returns <code>this</code> when <code>i</code> is <code>1</code> and
     * no meta method has been set.
     */
    public MtsValue get( int i )
    {
        return i == 1 ? this : NIL;
    }
    
    public void set( MtsValue key, MtsValue value )
    {
        throw new ScriptRuntimeException( "attempt to index a %s value", getType() );
    }
    
    public void set( String key, MtsValue value )
    {
        set( valueOf( key ), value );
    }
    
    // ========================================
    
    public final MtsValue call()
    {
        return call( MtsVarArgs.EMPTY_VARARGS );
    }
    
    public final MtsValue call( MtsValue arg1, MtsValue... args )
    {
        return call( new MtsVarArgs( arg1, args ) );
    }
    
    public MtsValue call( MtsVarArgs args )
    {
        throw new ScriptRuntimeException( "attempt to call a %s value", getType() );
    }
    
    public final MtsValue call( MtsValue arg1, List<MtsValue> args )
    {
        return call( new MtsVarArgs( arg1, args ) );
    }
    
    public final MtsValue call( MtsValue arg1, MtsVarArgs args )
    {
        return call( new MtsVarArgs( arg1, args ) );
    }
    
    // ========================================
    
    /**
     * Converts this value to an {@link MtsString}.
     * <p>
     * Equivalent to Java's toString().
     * 
     * @return The MtsString representation of this value.
     */
    public MtsString toStringMts()
    {
        return valueOf( toString() );
    }
    
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
    public MtsBoolean equalsMts( MtsValue x )
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
    
    public static void checkNotNil( MtsValue value, String msg, Object... args )
    {
        if ( value.isNil() )
            throw new ScriptRuntimeException( msg, args );
    }
    
    // ========================================
    
    public static String formatHashCode( int hashCode )
    {
        return Strings.padStart( Integer.toHexString( hashCode ), 8, '0' );
    }
    
}