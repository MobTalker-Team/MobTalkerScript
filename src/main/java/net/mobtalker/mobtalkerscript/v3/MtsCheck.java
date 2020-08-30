/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3;

import net.mobtalker.mobtalkerscript.v3.value.*;

/**
 * Utility class for library functions. Use static imports.
 */
public class MtsCheck
{
    public static MtsValue checkNotNil( MtsValue value )
    {
        if ( value.isNil() )
            throw new MtsArgumentException( "value cannot be nil" );
        
        return value;
    }
    
    public static MtsValue checkNotNil( MtsValue value, String msg )
    {
        if ( value.isNil() )
            throw new MtsArgumentException( msg );
        
        return value;
    }
    
    public static MtsValue checkNotNil( MtsValue value, int i )
    {
        if ( value.isNil() )
            throw new MtsRuntimeException( i, "bad argument #%s (value expected, got %s)", value.getType() );
        
        return value;
    }
    
    public static MtsValue checkNotNil( MtsVarargs args, int i )
    {
        return checkNotNil( args.get( i ), i );
    }
    
    // ========================================
    
    public static MtsValue checkType( MtsValue value, int i, MtsType expected )
    {
        if ( !value.is( expected ) )
            throw new MtsArgumentException( i, expected, value.getType() );
        
        return value;
    }
    
    // ========================================
    
    public static MtsTable checkTable( MtsValue value )
    {
        if ( value.isTable() )
            return value.asTable();
        
        throw new MtsArgumentException( MtsType.TABLE, value.getType() );
    }
    
    public static MtsTable checkTable( MtsValue value, int i )
    {
        if ( value.isTable() )
            return value.asTable();
        
        throw new MtsArgumentException( i, MtsType.TABLE, value.getType() );
    }
    
    public static MtsTable checkTable( MtsVarargs args, int i )
    {
        return checkTable( args.get( i ), i );
    }
    
    // ========================================
    
    public static String checkString( MtsValue value )
    {
        if ( value.isString() )
            return value.asString().toJava();
        
        throw new MtsArgumentException( MtsType.STRING, value.getType() );
    }
    
    public static String checkString( MtsValue value, int i )
    {
        if ( value.isString() )
            return value.asString().toJava();
        
        throw new MtsArgumentException( i, MtsType.STRING, value.getType() );
    }
    
    public static String checkString( MtsValue value, int i, String fallback )
    {
        if ( value.isNil() )
            return fallback;
        if ( value.isString() )
            return value.asString().toJava();
        
        throw new MtsArgumentException( i, "%s or %s expected, got %s", MtsType.STRING, MtsType.NIL, value.getType() );
    }
    
    public static String checkString( MtsVarargs args, int i )
    {
        return checkString( args.get( i ), i );
    }
    
    public static String checkString( MtsVarargs args, int i, String fallback )
    {
        return checkString( args.get( i ), i, fallback );
    }
    
    // ========================================
    
    public static double checkNumber( MtsValue value )
    {
        if ( value.isNumber() )
            return value.asNumber().toJavaDouble();
        
        throw new MtsArgumentException( MtsType.NUMBER, value.getType() );
    }
    
    public static double checkNumber( MtsValue value, int i )
    {
        if ( value.isNumber() )
            return value.asNumber().toJavaDouble();
        
        throw new MtsArgumentException( i, MtsType.NUMBER, value.getType() );
    }
    
    public static double checkNumber( MtsVarargs args, int i )
    {
        return checkNumber( args.get( i ), i );
    }
    
    public static double checkNumberWithCoercion( MtsValue value, int i )
    {
        if ( value.isNumber() )
            return value.asNumber().toJavaDouble();
        
        if ( value.isString() )
        {
            try
            {
                return value.toMtsNumber().toJavaDouble();
            }
            catch ( NumberFormatException ex )
            {}
        }
        
        throw new MtsArgumentException( i, MtsType.NUMBER, value.getType() );
    }
    
    public static double checkNumberWithCoercion( MtsValue value, int i, double fallback )
    {
        if ( value.isNil() )
            return fallback;
        if ( value.isNumber() )
            return value.asNumber().toJavaDouble();
        
        if ( value.isString() )
        {
            try
            {
                return value.toMtsNumber().toJavaDouble();
            }
            catch ( NumberFormatException ex )
            {}
        }
        
        throw new MtsArgumentException( i, MtsType.NUMBER, value.getType() );
    }
    
    // ========================================
    
    public static int checkInteger( MtsValue value )
    {
        if ( value.isNumber() )
            return value.asNumber().toJavaInt();
        
        throw new MtsArgumentException( MtsType.NUMBER, value.getType() );
    }
    
    public static int checkInteger( MtsValue value, int i )
    {
        if ( value.isNumber() )
            return value.asNumber().toJavaInt();
        
        throw new MtsArgumentException( i, MtsType.NUMBER, value.getType() );
    }
    
    public static int checkInteger( MtsValue value, int i, int fallback )
    {
        if ( value.isNil() )
            return fallback;
        if ( value.isNumber() )
            return value.asNumber().toJavaInt();
        
        throw new MtsArgumentException( i, "%s or %s expected, got %s", MtsType.NUMBER, MtsType.NIL, value.getType() );
    }
    
    public static int checkInteger( MtsVarargs args, int i )
    {
        return checkInteger( args.get( i ), i );
    }
    
    public static int checkInteger( MtsVarargs args, int i, int fallback )
    {
        return checkInteger( args.get( i ), i, fallback );
    }
    
    public static int checkIntegerWithMinimum( MtsValue value, int i, int min )
    {
        int v = checkInteger( value, i );
        if ( v < min )
            throw new MtsArgumentException( i, "number must be equal to or greater than %s, was %s", min, value );
        
        return v;
    }
    
    public static int checkIntegerWithMinimum( MtsValue value, int i, int min, int fallback )
    {
        if ( value.isNil() )
            return fallback;
        
        int v = checkInteger( value, i );
        if ( v < min )
            throw new MtsArgumentException( i, "number must be equal to or greater than %s, was %s", min, value );
        
        return v;
    }
    
    public static int checkIntegerWithCoercion( MtsValue value, int i )
    {
        if ( value.isNumber() )
            return value.asNumber().toJavaInt();
        
        if ( value.isString() )
        {
            try
            {
                return value.toMtsNumber().toJavaInt();
            }
            catch ( NumberFormatException ex )
            {}
        }
        
        throw new MtsArgumentException( i, MtsType.NUMBER, value.getType() );
    }
    
    public static int checkIntegerWithCoercion( MtsValue value, int i, int fallback )
    {
        if ( value.isNil() )
            return fallback;
        if ( value.isNumber() )
            return value.asNumber().toJavaInt();
        
        if ( value.isString() )
        {
            try
            {
                return value.toMtsNumber().toJavaInt();
            }
            catch ( NumberFormatException ex )
            {}
        }
        
        throw new MtsArgumentException( i, MtsType.NUMBER, value.getType() );
    }
    
    // ========================================
    
    private MtsCheck()
    {}
}
