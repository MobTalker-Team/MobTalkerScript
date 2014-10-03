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

import java.util.Random;

import net.mobtalker.mobtalkerscript.util.PrettyPrinter;
import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

public final class MtsTableLib
{
    @MtsNativeFunction
    public static MtsString concat( MtsValue argTable, MtsValue argSep, MtsValue arg3, MtsValue arg4 )
    {
        MtsTable table = checkTable( argTable, 0 );
        String sep = checkString( argSep, 1, "" );
        
        if ( arg3.isNil() )
            return table.concatList( sep );
        
        int from = checkInteger( arg3, 2 );
        
        if ( arg4.isNil() )
            return table.concatList( sep, from );
        
        int to = checkInteger( arg4, 3 );
        
        return table.concatList( sep, from, to );
    }
    
    @MtsNativeFunction
    public static MtsNumber count( MtsValue argTable )
    {
        return valueOf( checkTable( argTable, 0 ).count() );
    }
    
    @MtsNativeFunction
    public static void insert( MtsValue argTable, MtsValue arg2, MtsValue arg3 )
    {
        MtsTable table = checkTable( argTable, 0 );
        
        if ( arg3.isNil() )
            table.add( arg2 );
        
        checkNumber( arg2, 1 );
        
        table.insert( arg2.asNumber(), arg3 );
    }
    
    private static final Random _rnd = new Random();
    
    @MtsNativeFunction
    public static MtsValue random( MtsVarArgs args )
    {
        if ( args.isEmpty() )
            return NIL;
        
        if ( args.count() > 1 )
            return args.get( _rnd.nextInt( args.count() ) );
        
        MtsValue arg1 = args.get( 0 );
        if ( arg1.isTable() )
        {
            MtsTable t = arg1.asTable();
            
            if ( t.listSize() == 0 )
                return NIL;
            
            MtsNumber k = valueOf( _rnd.nextInt( t.listSize() ) + 1 );
            return t.get( k );
        }
        
        return arg1;
    }
    
    @MtsNativeFunction
    public static MtsValue remove( MtsValue argTable, MtsValue argIndex )
    {
        MtsTable table = checkTable( argTable, 0 );
        
        if ( argIndex.isNil() )
            return table.removeLast();
        
        return table.remove( checkInteger( argIndex, 1 ) );
    }
    
    @MtsNativeFunction
    public static MtsValue print( MtsValue argValue, MtsValue argIndent )
    {
        return valueOf( new PrettyPrinter( checkString( argIndent, 1, "    " ) ).print( argValue, null ) );
    }
}
