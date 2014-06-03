package mobtalkerscript.v2.lib;

import static mobtalkerscript.util.MtsCheck.*;
import static mobtalkerscript.v2.value.MtsValue.*;
import mobtalkerscript.v2.*;
import mobtalkerscript.v2.compiler.*;
import mobtalkerscript.v2.value.*;
import mobtalkerscript.v2.value.userdata.*;

public final class MtsBaseLib
{
    public MtsBaseLib( MtsGlobals g )
    {
        _G = g;
    }
    
    // ========================================
    
    @MtsNativeField
    public final MtsGlobals _G;
    
    // ========================================
    
    @MtsNativeFunction( name = "assert" )
    public void assertMts( MtsValue arg1, MtsValue arg2 )
    {
        if ( !MtsBoolean.isTrue( arg1 ) )
        {
            String msg = arg2.isNil() ? "assertion failed!" : arg2.toMtsString().asJavaString();
            throw new ScriptRuntimeException( msg );
        }
    }
    
    @MtsNativeFunction( name = "error" )
    public void error( MtsValue arg1, MtsValue arg2 )
    {
        if ( arg2.isNil() )
            throw new ScriptRuntimeException( arg1.toMtsString().asJavaString() );
        else
            throw new ScriptRuntimeException( arg2.asNumber().asJavaInt(),
                                              arg1.toMtsString().asJavaString() );
    }
    
    // ========================================
    
    @MtsNativeFunction( name = "inext" )
    public MtsValue INext( MtsValue arg1, MtsValue arg2 )
    {
        checkTable( arg1, 1 );
        
        MtsTable.Entry next = arg1.asTable().getINext( arg2.asNumber() );
        return next == null ? EMPTY_VARARGS : MtsVarArgs.of( next.getKey(), next.getValue() );
    }
    
    @MtsNativeFunction( name = "next" )
    public MtsValue Next( MtsValue arg1, MtsValue arg2 )
    {
        checkTable( arg1, 1 );
        
        MtsTable.Entry next = arg1.asTable().getNext( arg2 );
        return next == null ? EMPTY_VARARGS : MtsVarArgs.of( next.getKey(), next.getValue() );
    }
    
    // ========================================
    
    @MtsNativeFunction( name = "print" )
    public void Print( MtsVarArgs args )
    {
        if ( args.count() > 0 )
        {
            MtsString str = MtsString.concat( args );
            _G.out.println( str.asJavaString() );
        }
        else
        {
            _G.out.println();
        }
    }
    
    // ========================================
    
    @MtsNativeFunction( name = "rawget" )
    public MtsValue RawGet( MtsValue arg1, MtsValue arg2 )
    {
        return checkTable( arg1, 0 ).get( arg2, false );
    }
    
    @MtsNativeFunction( name = "rawset" )
    public void RawSet( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
    {
        checkTable( arg1, 0 ).set( arg2, arg3, false );
    }
    
    // ========================================
    
    @MtsNativeFunction( name = "GetMetaTable" )
    public MtsTable GetMetaTable( MtsValue arg )
    {
        MtsTable t = checkTable( arg, 0 );
        
        if ( t.hasMetaTag( __METATABLE ) )
            throw new ScriptRuntimeException( "cannot retrieve a protected metatable" );
        
        return t.getMetaTable();
    }
    
    @MtsNativeFunction( name = "SetMetaTable" )
    public MtsTable SetMetaTable( MtsValue arg1, MtsValue arg2 )
    {
        MtsTable t = checkTable( arg1, 0 );
        
        if ( t.hasMetaTag( __METATABLE ) )
            throw new ScriptRuntimeException( "cannot change a protected metatable" );
        
        t.setMetaTable( arg2 );
        return t;
    }
    
    // ========================================
    
    @MtsNativeFunction( name = "toNumber" )
    public MtsValue ToNumber( MtsValue arg1 )
    {
        try
        {
            return arg1.toMtsNumber();
        }
        catch ( NumberFormatException ex )
        {
            return NIL;
        }
    }
    
    @MtsNativeFunction( name = "toString" )
    public MtsString ToString( MtsValue arg1 )
    {
        return arg1.toMtsString();
    }
    
    // ========================================
    
    @MtsNativeFunction( name = "typeof" )
    public MtsString TypeOf( MtsValue arg1 )
    {
        return valueOf( arg1.getType().getName() );
    }
    
    // ========================================
    
    @MtsNativeFunction( name = "LoadString" )
    public MtsFunction LoadString( MtsVarArgs args )
    {
        MtsFunctionPrototype p;
        try
        {
            p = MtsCompiler.loadString( checkString( args, 0 ), "string" );
        }
        catch ( Exception ex )
        {
            throw new ScriptRuntimeException( "Unable to load string: %s", ex.getMessage() );
        }
        
        MtsValue env = args.get( 1 );
        if ( env.isNil() )
        {
            env = _G;
        }
        
        return new MtsClosure( p, env );
    }
    
    // ========================================
    
    @MtsNativeFunction( name = "pcall" )
    public MtsValue PCall( MtsVarArgs args )
    {
        MtsValue f = args.get( 0 );
        MtsVarArgs callArgs = args.subArgs( 1 );
        
        MtsValue result;
        try
        {
            MtsValue callResults = f.call( callArgs );
            
            if ( callResults.isVarArgs() )
                result = MtsVarArgs.of( TRUE, callResults.asVarArgs() );
            else
                result = MtsVarArgs.of( TRUE, callResults );
        }
        catch ( ScriptRuntimeException ex )
        {
            result = MtsVarArgs.of( FALSE, valueOf( ex.getMessage() ) );
        }
        
        return result;
    }
    
}
