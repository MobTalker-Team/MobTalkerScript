/*
 * Copyright (C) 2013-2014 Chimaine
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
package net.mobtalker.mobtalkerscript.v2.lib;

import static net.mobtalker.mobtalkerscript.v2.MtsCheck.*;
import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;
import net.mobtalker.mobtalkerscript.v2.*;
import net.mobtalker.mobtalkerscript.v2.compiler.MtsCompiler;
import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.MtsTable.Entry;
import net.mobtalker.mobtalkerscript.v2.value.userdata.*;

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
    public void assertMts( MtsValue arg1, MtsValue arg2 )
    {
        if ( !isTrue( arg1 ) )
        {
            String msg = arg2.isNil() ? "assertion failed!" : arg2.toMtsString().toJava();
            throw new ScriptRuntimeException( msg );
        }
    }
    
    @MtsNativeFunction( "error" )
    public void error( MtsValue arg1, MtsValue arg2 )
    {
        if ( arg2.isNil() )
            throw new ScriptRuntimeException( arg1.toMtsString().toJava() );
        else
            throw new ScriptRuntimeException( arg2.asNumber().toJavaInt(),
                                              arg1.toMtsString().toJava() );
    }
    
    // ========================================
    
    @MtsNativeFunction( "inext" )
    public MtsValue inext( MtsValue argTable, MtsValue argIndex )
    {
        MtsNumber prev;
        if ( argIndex.isNil() )
        {
            prev = ZERO;
        }
        else
        {
            checkNumber( argIndex, 1 );
            prev = argIndex.asNumber();
        }
        
        Entry next = checkTable( argTable, 0 ).getINext( prev );
        return next == null ? EMPTY_VARARGS : MtsVarArgs.of( next.getKey(), next.getValue() );
    }
    
    @MtsNativeFunction( "next" )
    public MtsValue next( MtsValue arg1, MtsValue arg2 )
    {
        Entry next = checkTable( arg1, 0 ).getNext( arg2 );
        return next == null ? EMPTY_VARARGS : MtsVarArgs.of( next.getKey(), next.getValue() );
    }
    
    // ========================================
    
    @MtsNativeFunction( "print" )
    public void print( MtsVarArgs args )
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
    public MtsValue rawGet( MtsValue argTable, MtsValue argKey )
    {
        return checkTable( argTable, 0 ).get( argKey );
    }
    
    @MtsNativeFunction( "rawset" )
    public void rawSet( MtsValue argTable, MtsValue argKey, MtsValue arg )
    {
        checkTable( argTable, 0 ).set( argKey, arg );
    }
    
    // ========================================
    
    @MtsNativeFunction( "GetMetatable" )
    public MtsTable getMetaTable( MtsValue argTable )
    {
        MtsTable t = checkTable( argTable, 0 );
        
        if ( t.hasMetaTag( __METATABLE ) )
            throw new ScriptRuntimeException( "cannot retrieve a protected metatable" );
        
        return t.getMetaTable();
    }
    
    @MtsNativeFunction( "SetMetatable" )
    public MtsTable setMetaTable( MtsValue argTable, MtsValue argMetatable )
    {
        MtsTable t = checkTable( argTable, 0 );
        
        if ( t.hasMetaTag( __METATABLE ) )
            throw new ScriptRuntimeException( "cannot change a protected metatable" );
        
        t.setMetaTable( argMetatable );
        return t;
    }
    
    // ========================================
    
    @MtsNativeFunction( "toNumber" )
    public MtsValue toNumber( MtsValue arg )
    {
        try
        {
            return arg.toMtsNumber();
        }
        catch ( NumberFormatException ex )
        {
            return NIL;
        }
    }
    
    @MtsNativeFunction( "toString" )
    public MtsString toString( MtsValue arg )
    {
        return arg.toMtsString();
    }
    
    // ========================================
    
    @MtsNativeFunction( "typeof" )
    public MtsString typeOf( MtsValue arg )
    {
        return valueOf( arg.getType().getName() );
    }
    
    // ========================================
    
    @MtsNativeFunction( "LoadString" )
    public MtsFunction loadString( MtsVarArgs args )
    {
        MtsFunctionPrototype p;
        try
        {
            p = MtsCompiler.loadString( checkString( args, 0 ), "string" );
        }
        catch ( Exception ex )
        {
            throw new ScriptRuntimeException( "Unable to load string: " + ex.getMessage() );
        }
        
        MtsValue env = args.get( 1 );
        if ( env.isNil() )
        {
            env = _G;
        }
        
        return new MtsClosure( p, env );
    }
    
    // ========================================
    
    @MtsNativeFunction( "pcall" )
    public MtsValue pCall( MtsVarArgs args )
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
