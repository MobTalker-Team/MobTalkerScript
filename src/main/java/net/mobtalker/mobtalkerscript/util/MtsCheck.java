package mobtalkerscript.util;

import mobtalkerscript.v2.*;
import mobtalkerscript.v2.value.*;

public class MtsCheck
{
    public static MtsValue checkNotNil( MtsValue value )
    {
        if ( value.isNil() )
            throw new BadArgumentException( "value cannot be nil" );
        
        return value;
    }
    
    public static MtsValue checkNotNil( MtsValue value, String msg )
    {
        if ( value.isNil() )
            throw new BadArgumentException( msg );
        
        return value;
    }
    
    public static MtsValue checkNotNil( MtsValue value, int i )
    {
        if ( value.isNil() )
            throw new ScriptRuntimeException( "bad argument #%s (value expected, got %s)", i, value.getType() );
        
        return value;
    }
    
    public static MtsValue checkNotNil( MtsVarArgs args, int i )
    {
        return checkNotNil( args.get( i ), i );
    }
    
    // ========================================
    
    public static MtsValue checkType( MtsValue value, int i, MtsType expected )
    {
        if ( !value.is( expected ) )
            throw new BadArgumentException( i, expected, value.getType() );
        
        return value;
    }
    
    // ========================================
    
    public static MtsTable checkTable( MtsValue value, int i )
    {
        if ( value.isTable() )
            return value.asTable();
        
        throw new BadArgumentException( i, MtsType.TABLE, value.getType() );
    }
    
    public static MtsTable checkTable( MtsVarArgs args, int i )
    {
        return checkTable( args.get( i ), i );
    }
    
    // ========================================
    
    public static String checkString( MtsValue value, int i )
    {
        if ( value.isString() )
            return value.asString().asJavaString();
        
        throw new BadArgumentException( i, MtsType.STRING, value.getType() );
    }
    
    public static String checkString( MtsValue value, int i, String fallback )
    {
        if ( value.isNil() )
            return fallback;
        if ( value.isString() )
            return value.asString().asJavaString();
        
        throw new BadArgumentException( i, "%s or %s expected, got %s", MtsType.STRING, MtsType.NIL, value.getType() );
    }
    
    public static String checkString( MtsVarArgs args, int i )
    {
        return checkString( args.get( i ), i );
    }
    
    public static String checkString( MtsVarArgs args, int i, String fallback )
    {
        return checkString( args.get( i ), i );
    }
    
    // ========================================
    
    public static double checkNumber( MtsValue value, int i )
    {
        if ( value.isNumber() )
            return value.asNumber().asJavaDouble();
        
        throw new BadArgumentException( i, MtsType.NUMBER, value.getType() );
    }
    
    public static double checkNumber( MtsVarArgs args, int i )
    {
        return checkNumber( args.get( i ), i );
    }
    
    // ========================================
    
    public static int checkInteger( MtsValue value, int i )
    {
        if ( value.isNumber() )
            return value.asNumber().asJavaInt();
        
        throw new BadArgumentException( i, MtsType.NUMBER, value.getType() );
    }
    
    public static int checkInteger( MtsValue value, int i, int fallback )
    {
        if ( value.isNil() )
            return fallback;
        if ( value.isNumber() )
            return value.asNumber().asJavaInt();
        
        throw new BadArgumentException( i, "%s or %s expected, got %s", MtsType.NUMBER, MtsType.NIL, value.getType() );
    }
    
    public static int checkInteger( MtsVarArgs args, int i )
    {
        return checkInteger( args.get( i ), i );
    }
    
    public static int checkInteger( MtsVarArgs args, int i, int fallback )
    {
        return checkInteger( args.get( i ), i, fallback );
    }
    
    public static int checkIntegerWithMinimum( MtsValue value, int i, int min )
    {
        int v = checkInteger( value, i );
        if ( v < min )
            throw new BadArgumentException( i, "number must be equal to or greater than %s, was %s", min, value );
        
        return v;
    }
    
    // ========================================
    
}
