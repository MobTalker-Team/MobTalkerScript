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
package net.mobtalker.mobtalkerscript.v3.lib;

import static net.mobtalker.mobtalkerscript.v3.MtsCheck.*;
import static net.mobtalker.mobtalkerscript.v3.value.MtsValue.*;

import java.util.*;

import net.mobtalker.mobtalkerscript.util.PrettyPrinter;
import net.mobtalker.mobtalkerscript.v3.value.*;
import net.mobtalker.mobtalkerscript.v3.value.MtsTable.Entry;
import net.mobtalker.mobtalkerscript.v3.value.userdata.MtsNativeFunction;

public final class MtsTableLib
{
    private static final Random _rnd = new Random();

    // ========================================

    @MtsNativeFunction
    public static MtsString concat( MtsValue argTable, MtsValue argSep, MtsValue arg3, MtsValue arg4 )
    {
        MtsTableList list = checkTable( argTable, 0 ).list();
        String sep = checkString( argSep, 1, "" );

        if ( arg3.isNil() )
            return MtsString.of( list.concat( sep ) );

        int from = checkInteger( arg3, 2 ) - 1;

        if ( arg4.isNil() )
            return MtsString.of( list.concat( sep, from ) );

        int to = checkInteger( arg4, 3 ) - 1;

        return MtsString.of( list.concat( sep, from, to ) );
    }

//    @MtsNativeFunction
//    public static MtsValue copy( MtsValue argTable, MtsValue argDeep )
//    {
//        MtsTable t = checkTable( argTable, 0 );
//
//        return argDeep.isTrue() ? new TableCloner().clone( t ) : new MtsTable( t );
//    }

    @MtsNativeFunction
    public static MtsNumber count( MtsValue argTable )
    {
        return MtsNumber.of( checkTable( argTable, 0 ).size() );
    }

    @MtsNativeFunction
    public static MtsValue dump( MtsValue argValue, MtsValue argIndent )
    {
        return MtsString.of( new PrettyPrinter( checkString( argIndent, 1, "    " ) ).print( argValue, null ) );
    }

    @MtsNativeFunction
    public static void insert( MtsValue argTable, MtsValue arg2, MtsValue arg3 )
    {
        MtsTableList list = checkTable( argTable, 0 ).list();

        if ( arg3.isNil() )
            list.add( arg2 );

        checkNumber( arg2, 1 );
        list.add( arg2.asNumber(), arg3 );
    }

    // ========================================

    @MtsNativeFunction
    public static MtsValue pack( MtsVarargs args )
    {
        int count = args.count();
        MtsTable result = new MtsTable( count, 0 );

        for ( int i = 0; i < count; i++ )
        {
            result.list().add( args.get( i ) );
        }

        return result;
    }

    @MtsNativeFunction
    public static MtsVarargs unpack( MtsValue argTable, MtsValue argFrom, MtsValue argTo )
    {
        MtsTableList list = checkTable( argTable, 0 ).list();
        int from = checkInteger( argFrom, 1, 1 ) - 1;
        int to = checkInteger( argTo, 2, list.size() );

        if ( ( from > to ) || ( to < from ) )
            return MtsVarargs.Empty;

        // TODO Implement a custom toArray with support for specifying the range

        List<MtsValue> copy = new ArrayList<>( list.subList( from, Math.min( to, list.size() ) ) );
        return MtsVarargs.of( copy );
    }

    // ========================================

    @MtsNativeFunction
    public static MtsValue random( MtsVarargs args )
    {
        if ( args.isEmpty() )
            return Nil;

        if ( args.count() > 1 )
            return args.get( _rnd.nextInt( args.count() ) );

        MtsValue arg1 = args.get( 0 );
        if ( arg1.isTable() )
        {
            MtsTable t = arg1.asTable();

            if ( t.list().isEmpty() )
                return Nil;

            return t.get( _rnd.nextInt( t.list().size() ) );
        }

        return arg1;
    }

    @MtsNativeFunction
    public static MtsValue remove( MtsValue argTable, MtsValue argIndex )
    {
        MtsTableList table = checkTable( argTable, 0 ).list();

        if ( argIndex.isNil() )
            return table.removeLast();

        return table.remove( checkInteger( argIndex, 1 ) );
    }

    @MtsNativeFunction
    public static void sort( MtsValue argTable )
    {
        checkTable( argTable, 0 ).list().sort();
    }

    @MtsNativeFunction
    public static MtsValue keys( MtsValue argTable )
    {
        MtsTable table = checkTable( argTable, 0 );
        MtsTable result = new MtsTable( table.size(), 0 );

        for ( Entry entry : table )
        {
            result.list().add( entry.getKey() );
        }

        return result;
    }

    @MtsNativeFunction
    public static MtsValue values( MtsValue argTable )
    {
        MtsTable t = checkTable( argTable, 0 );
        MtsTable result = new MtsTable( t.size(), 0 );

        for ( Entry entry : t )
        {
            result.list().add( entry.getValue() );
        }

        return result;
    }
}
