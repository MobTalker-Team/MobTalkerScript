/*
 * Copyright (C) 2013-2020 Chimaine, MobTalkerScript contributors
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
package net.mobtalker.mobtalkerscript.v3.lib;

import static net.mobtalker.mobtalkerscript.v3.MtsCheck.*;
import static net.mobtalker.mobtalkerscript.v3.value.MtsMetaMethods.*;
import static net.mobtalker.mobtalkerscript.v3.value.MtsValue.*;

import java.util.List;

import net.mobtalker.mobtalkerscript.v3.*;
import net.mobtalker.mobtalkerscript.v3.compiler.MtsCompiler;
import net.mobtalker.mobtalkerscript.v3.value.*;
import net.mobtalker.mobtalkerscript.v3.value.MtsTable.Entry;
import net.mobtalker.mobtalkerscript.v3.value.userdata.*;

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
    
    @MtsNativeFunction( "assert" )
    public static MtsVarargs assertMts( MtsVarargs args )
    {
        if ( !args.get( 0 ).isTrue() )
            throw new MtsRuntimeException( checkString( args, 1, "assertion failed!" ) );
        
        return args;
    }
    
    @MtsNativeFunction( "error" )
    public static void error( MtsVarargs args )
    {
        throw new MtsRuntimeException( checkIntegerWithMinimum( args.get(1), 1, 1, 1 ), args.get( 0 ).toMtsString().toJava() );
    }
    
    // ========================================
    // Iterators
    
    @MtsNativeField( "next" )
    public static final MtsFunction next = new MtsFunction()
    {
        @Override
        public MtsVarargs call( MtsVarargs args )
        {
            Entry next = checkTable( args, 0 ).getNext( args.get( 1 ) );
            return next == null ? MtsVarargs.Empty : MtsVarargs.of( next.getKey(), next.getValue() );
        }
    };
    
    @MtsNativeField( "inext" )
    public static final MtsFunction inext = new MtsFunction()
    {
        @Override
        public MtsVarargs call( MtsVarargs args )
        {
            Entry next = checkTable( args, 0 ).getINext( checkInteger( args, 1, 0 ) - 1 );
            return next == null ? MtsVarargs.Empty : MtsVarargs.of( next.getKey(), next.getValue() );
        }
    };
    
    private static final MtsString __pairs = MtsString.of( "__pairs" );
    private static final MtsString __ipairs = MtsString.of( "__ipairs" );
    
    @MtsNativeFunction( "pairs" )
    public static MtsValue pairs( MtsValue arg )
    {
        MtsValue mm = arg.getMetaTag( __pairs );
        if ( !mm.isNil() )
            return mm.call( arg );
        else
            return MtsVarargs.of( next, arg );
    }
    
    @MtsNativeFunction( "ipairs" )
    public static MtsValue ipairs( MtsValue arg )
    {
        MtsValue mm = arg.getMetaTag( __ipairs );
        if ( !mm.isNil() )
            return mm.call( arg );
        else
            return MtsVarargs.of( inext, arg );
    }
    
    // ========================================
    
    @MtsNativeFunction( "print" )
    public void print( MtsVarargs args )
    {
        if ( args.count() > 0 )
        {
            MtsString str = MtsString.concat( args );
            _G.out.println( str.toJava() );
        }
        else
        {
            _G.out.println();
        }
    }
    
    // ========================================
    
    @MtsNativeFunction( "rawget" )
    public static MtsValue rawGet( MtsValue argTable, MtsValue argKey )
    {
        return checkTable( argTable, 0 ).get( argKey );
    }
    
    @MtsNativeFunction( "rawset" )
    public static void rawSet( MtsValue argTable, MtsValue argKey, MtsValue arg )
    {
        checkTable( argTable, 0 ).set( argKey, arg );
    }
    
    // ========================================
    
    @MtsNativeFunction( "getmetatable" )
    public static MtsTable getMetaTable( MtsValue argTable )
    {
        MtsTable t = checkTable( argTable, 0 );
        
        MtsValue mm = t.getMetaTag( __metatable );
        if ( !mm.isNil() )
            throw new MtsRuntimeException( "cannot retrieve a protected metatable" );
        
        return t.getMetaTable();
    }
    
    @MtsNativeFunction( "setmetatable" )
    public static MtsTable setMetaTable( MtsValue argTable, MtsValue argMetatable )
    {
        MtsTable t = checkTable( argTable, 0 );
        
        MtsValue mm = t.getMetaTag( __metatable );
        if ( !mm.isNil() )
            throw new MtsRuntimeException( "cannot change a protected metatable" );
        
        t.setMetaTable( argMetatable );
        return t;
    }
    
    // ========================================
    
    @MtsNativeFunction( "toNumber" )
    public static MtsValue toNumber( MtsValue arg )
    {
        MtsNumber n = arg.toMtsNumber();
        return n.isNaN() ? Nil : n;
    }
    
    @MtsNativeFunction( "toString" )
    public static MtsString toString( MtsValue arg )
    {
        return arg.toMtsString();
    }
    
    // ========================================
    
    @MtsNativeFunction( "typeof" )
    public static MtsString typeOf( MtsValue arg )
    {
        return MtsString.of( arg.getType().getName() );
    }
    
    // ========================================
    
    /**
     * TODO Does not follow the Lua signature of load. Currently intended.
     */
    @MtsNativeFunction( "load" )
    public MtsValue loadString( MtsVarargs args )
    {
        String chunk = checkString( args, 0, "" );
        MtsValue env = args.get( 1 );
        String sourceName = checkString( args, 2, "<load>" );
        
        MtsFunctionPrototype p;
        try
        {
            p = MtsCompiler.loadChunk( chunk, sourceName );
        }
        catch ( Exception ex )
        {
            return MtsVarargs.of( Nil, MtsString.of( "Unable to load string: " + ex.getMessage() ) );
        }
        
        if ( env.isNil() )
        {
            env = _G;
        }
        
        return new MtsClosure( p, env );
    }
    
    // ========================================
    
    @MtsNativeFunction( "pcall" )
    public static MtsValue pCall( MtsVarargs args )
    {
        MtsValue f = args.get( 0 );
        MtsVarargs callArgs = args.subArgs( 1 );
        
        MtsValue result;
        try
        {
            MtsValue callResults = f.call( callArgs );
            result = MtsVarargs.of( MtsBoolean.True, callResults.isVarArgs() ? callResults.asVarArgs() : callResults );
        }
        catch ( MtsRuntimeException ex )
        {
            List<MtsStackTraceElement> elements = ex.getStackTraceElements();
            MtsTable t = new MtsTable( elements.size(), 0 );
            for ( MtsStackTraceElement element : elements )
            {
                t.list().add( MtsString.of( element.toString() ) );
            }
            
            result = MtsVarargs.of( MtsBoolean.False, MtsString.of( ex.getMessage() ), t );
        }
        
        return result;
    }
    
    // ========================================
    
    @MtsNativeFunction( "select" )
    public static MtsValue select( MtsVarargs args )
    {
        MtsValue arg1 = checkNotNil( args, 0 );
        if ( arg1.isString() && arg1.asString().toJava().equals( "#" ) )
            return MtsNumber.of( args.count() - 1 );
        
        int i = checkInteger( arg1, 1 );
        if ( i > 0 )
            return args.subArgs( i );
        
        if ( i < 0 )
        {
            i = args.count() + i;
            if ( i > 0 )
                return args.subArgs( i );
        }
        
        throw new MtsArgumentException( 1, "index out of range" );
    }
}
