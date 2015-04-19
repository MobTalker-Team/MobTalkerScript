/*
 * Copyright (C) 2013-2015 Chimaine
 * 
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mobtalker.mobtalkerscript.v3.value;

import static net.mobtalker.mobtalkerscript.v3.value.MtsMetaMethods.*;
import net.mobtalker.mobtalkerscript.v3.*;

import com.google.common.base.Strings;

/**
 * Base class for any value used by MobTalkerScript.
 */
public abstract class MtsValue implements IMtsCallable, Comparable<MtsValue>
{
    public static final MtsNil Nil = new MtsNil();
    
    // ========================================
    // Metatags and -tables
    
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
        new MtsRuntimeException( "attempt to set metatable of a %s value", getType() );
    }
    
    public final MtsValue getMetaTag( MtsString tag )
    {
        return hasMetaTable() ? getMetaTable().get( tag ) : Nil;
    }
    
    // ========================================
    // Table get
    
    public final MtsValue get( String key )
    {
        return get( MtsString.of( key ) );
    }
    
    public final MtsValue get( String key, boolean useMetaTag )
    {
        return get( MtsString.of( key ), useMetaTag );
    }
    
    public final MtsValue get( MtsValue key )
    {
        return get( key, false );
    }
    
    /**
     * Attempts to index this value using the given key, with optional meta tag processing.
     * <p>
     * Describes the indexing access operation <code>table[key]</code>.
     * <p>
     * If no value for <code>key</code> can be found and <code>useMetaTag</code> is <code>true</code>, the {@value #__index}
     * meta tag will be used to retrieve a value for <code>key</code>. If it is an
     *
     * @param key The key used to index this value.
     * @param useMetaTag Specifies if the {@value #__index} meta tag should be used.
     * @return The result of the lookup for a value for <code>key</code>.
     * @throws MtsRuntimeException If this value cannot be indexed.
     */
    public final MtsValue get( MtsValue key, boolean useMetaTag )
    {
        MtsValue result = getRaw( key );
        
        if ( result.isNil() && useMetaTag )
        {
            MtsValue tag = getMetaTag( __index );
            
            if ( tag.isFunction() )
                result = tag.call( this, key );
            else if ( !tag.isNil() )
                result = tag.get( key );
        }
        
        return result;
    }
    
    protected MtsValue getRaw( MtsValue key )
    {
        throw new MtsRuntimeException( "attempt to index a %s value", getType() );
    }
    
    /**
     * Differs from {@link #get(MtsValue)} in that it always returns <code>this</code> when <code>i</code> is <code>1</code> and
     * no meta method has been set.
     * <p>
     * 0 based.
     */
    public MtsValue get( int i )
    {
        return i == 0 ? this : Nil;
    }
    
    public final MtsValue get()
    {
        return get( 0 );
    }
    
    // ========================================
    // Table set
    
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
        set( MtsString.of( key ), value, useMetaTag );
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
     * If <code>key</code> does not exist already and <code>useMetaTag</code> is <code>true</code>, the {@value #__newindex}
     * meta tag is tried. If it is a {@link MtsFunction} invoke <code>call(this, key, value)</code> on it. Otherwise this
     * operation is repeated on it with the same arguments.
     *
     * @param key The key to set the value for.
     * @param value The value to set for key.
     * @param useMetaTag Specifies if the {@value #__newindex} meta tag should be used.
     */
    public final void set( MtsValue key, MtsValue value, boolean useMetaTag )
    {
        if ( useMetaTag )
        {
            MtsValue tag = getMetaTag( __newindex );
            
            if ( tag.isNil() )
                setRaw( key, value );
            else if ( tag.isFunction() )
                tag.call( this, key, value );
            else
                tag.set( key, value );
        }
        else
        {
            setRaw( key, value );
        }
    }
    
    protected void setRaw( MtsValue key, MtsValue value )
    {
        throw new MtsRuntimeException( "attempt to index a %s value", getType() );
    }
    
    // ========================================
    // Calling
    
    @Override
    public MtsVarargs call( MtsVarargs args )
    {
        MtsValue tag = getMetaTag( __call );
        if ( tag.isNil() )
            throw new MtsRuntimeException( "attempt to call a %s value", getType() );
        
        return tag.call( MtsVarargs.of( this, args ) );
    }
    
    // ========================================
    // Unary Operations
    
    public MtsNumber getLength()
    {
        MtsValue tag = getMetaTag( __len );
        if ( tag.isNil() )
            throw new MtsLengthException( getType() );
        
        return tag.call( this ).get().asNumber();
    }
    
    public MtsValue unaryMinus()
    {
        MtsValue tag = getMetaTag( __unm );
        if ( tag.isNil() )
            throw new MtsArithmeticException( getType() );
        
        return tag.call( this ).get();
    }
    
    // ========================================
    // Arithmetic Operations
    
    private static MtsValue performArithmetic( MtsValue a, MtsValue b, MtsString operation )
    {
        MtsValue tag = a.getMetaTag( operation );
        if ( tag.isNil() )
        {
            tag = b.getMetaTag( operation );
            if ( tag.isNil() )
                throw new MtsArithmeticException( a.getType() );
        }
        
        return tag.call( a, b ).get();
    }
    
    private MtsValue performArithmeticWith( MtsValue a, MtsString operation )
    {
        MtsValue tag = getMetaTag( operation );
        if ( tag.isNil() )
            throw new MtsArithmeticException( getType() );
        
        return tag.call( a, this ).get();
    }
    
    public MtsValue add( MtsValue b )
    {
        return performArithmetic( this, b, __add );
    }
    
    protected MtsValue addTo( MtsNumber a )
    {
        return performArithmeticWith( a, __add );
    }
    
    public MtsValue substract( MtsValue b )
    {
        return performArithmetic( this, b, __sub );
    }
    
    protected MtsValue substractFrom( MtsNumber a )
    {
        return performArithmeticWith( a, __sub );
    }
    
    public MtsValue multiplyBy( MtsValue b )
    {
        return performArithmetic( this, b, __mul );
    }
    
    protected MtsValue multiplyWith( MtsNumber a )
    {
        return performArithmeticWith( a, __mul );
    }
    
    public MtsValue divideBy( MtsValue b )
    {
        return performArithmetic( this, b, __div );
    }
    
    protected MtsValue divideFrom( MtsNumber a )
    {
        return performArithmeticWith( a, __div );
    }
    
    public MtsValue powerTo( MtsValue b )
    {
        return performArithmetic( this, b, __pow );
    }
    
    protected MtsValue powerOf( MtsNumber a )
    {
        return performArithmeticWith( a, __pow );
    }
    
    public MtsValue modulo( MtsValue b )
    {
        return performArithmetic( this, b, __mod );
    }
    
    protected MtsValue moduloOf( MtsNumber a )
    {
        return performArithmeticWith( a, __mod );
    }
    
    // ========================================
    // Concatenation
    
    private static MtsString performConcatenation( MtsValue a, MtsValue b )
    {
        MtsValue tag = a.getMetaTag( __concat );
        if ( tag.isNil() )
        {
            tag = b.getMetaTag( __concat );
            if ( tag.isNil() )
                throw new MtsConcatenationException( a.getType() );
        }
        
        return tag.call( a, b ).get().asString();
    }
    
    public MtsString concat( MtsValue b )
    {
        return performConcatenation( this, b );
    }
    
    protected MtsString concatTo( String a )
    {
        throw new MtsConcatenationException( getType() );
    }
    
    // ========================================
    // Comparison Operations
    
    /**
     * Performs the given comparison of the given values.
     * <p>
     * If the first value does not specify a meta tag for the operation, try the second value.<br>
     * If both do not specify tags, throw an exception.
     */
    private static MtsBoolean performComparison( MtsValue a, MtsValue b, MtsString op )
    {
        MtsValue tag = a.getMetaTag( op );
        if ( tag.isNil() )
        {
            tag = b.getMetaTag( op );
            if ( tag.isNil() )
                throw new MtsComparisonException( a.getType(), b.getType() );
        }
        
        return tag.call( a, b ).get().toMtsBoolean();
    }
    
    public MtsBoolean isLessThen( MtsValue other )
    {
        return performComparison( this, other, __lt );
    }
    
    protected MtsBoolean isGreaterThenOrEqual( MtsNumber other )
    {
        throw new MtsComparisonException( other.getType(), getType() );
    }
    
    protected MtsBoolean isGreaterThenOrEqual( MtsString other )
    {
        throw new MtsComparisonException( other.getType(), getType() );
    }
    
    public MtsBoolean isLessThenOrEqual( MtsValue other )
    {
        return performComparison( this, other, __lte );
    }
    
    protected MtsBoolean isGreaterThen( MtsNumber other )
    {
        throw new MtsComparisonException( other.getType(), getType() );
    }
    
    protected MtsBoolean isGreaterThen( MtsString other )
    {
        throw new MtsComparisonException( other.getType(), getType() );
    }
    
    /**
     * Tests equality with the given value.
     * <p>
     * First the {@link #equals(Object)} method is tried, if it returns true, return {@link #True}.<br>
     * Otherwise, if both {@link #__eq} meta methods are equal, call it and return the result.<br>
     * Otherwise, return {@link #False}.
     */
    public final MtsBoolean isEqual( MtsValue b )
    {
        if ( equals( b ) )
            return MtsBoolean.True;
        
        MtsValue tagA = getMetaTag( __eq );
        if ( tagA.isNil() || !tagA.equals( getMetaTag( __eq ) ) )
            return MtsBoolean.False;
        
        return tagA.call( this, b ).get().toMtsBoolean();
    }
    
    // ========================================
    // Type checks
    
    /**
     * Returns the type of this value.
     */
    public abstract MtsType getType();
    
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
     * Checks if this value is a {@link EvaluatedVarargs}.
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
    // Casting
    
    /**
     * Equivalent to a Java typecast to {@link MtsBoolean}.
     */
    public MtsBoolean asBoolean()
    {
        throw new MtsTypeException( MtsType.BOOLEAN, getType() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MtsNumber}.
     * <p>
     * If this value is a string an automatic coercion into a number is attempted.
     */
    public MtsNumber asNumber()
    {
        throw new MtsTypeException( MtsType.NUMBER, getType() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MtsString}.
     * <p>
     * If this value is a number it is automatically coerced to a string.
     */
    public MtsString asString()
    {
        throw new MtsTypeException( MtsType.STRING, getType() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MtsClosure}.
     */
    public MtsClosure asClosure()
    {
        throw new MtsTypeException( MtsType.FUNCTION, getType() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MtsFunction}.
     */
    public MtsFunction asFunction()
    {
        throw new MtsTypeException( MtsType.FUNCTION, getType() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link MtsTable}.
     */
    public MtsTable asTable()
    {
        throw new MtsTypeException( MtsType.TABLE, getType() );
    }
    
    /**
     * Equivalent to a Java typecast to {@link EvaluatedVarargs}.
     */
    public MtsVarargs asVarArgs()
    {
        throw new MtsTypeException( MtsType.VARARGS, getType() );
    }
    
    // ========================================
    // Conversion
    
    /**
     * Converts this value to an {@link MtsBoolean}.
     * <p>
     * This should always return {@link #True} unless this object is {@link #Nil} or {@link #False} itself.
     */
    public MtsBoolean toMtsBoolean()
    {
        return MtsBoolean.True;
    }
    
    /**
     * Converts this value to an {@link MtsNumber}.
     *
     * @throws NumberFormatException If this value cannot be converted.
     */
    public MtsNumber toMtsNumber() throws NumberFormatException
    {
        throw new NumberFormatException( "attempt to convert " + getType() + " to " + MtsType.NUMBER );
    }
    
    /**
     * Converts this value to an {@link MtsString}.
     * <p>
     * Equivalent to Java's toString().
     */
    public MtsString toMtsString()
    {
        return MtsString.of( toString() );
    }
    
    // ========================================
    // Java interface
    
    /**
     * Returns <code>true</code> unless {@link MtsBoolean#False} or {@link #Nil}.
     */
    public boolean isTrue()
    {
        return true;
    }
    
    public Object toJava()
    {
        return toString();
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return getType().toString() + ':' + Strings.padStart( Integer.toHexString( hashCode() ), 8, '0' );
    }
    
    public String toString( boolean debug )
    {
        return toString();
    }
    
    // ========================================
    // Comparable
    
    @Override
    public int compareTo( MtsValue o )
    {
        return 0;
    }
}