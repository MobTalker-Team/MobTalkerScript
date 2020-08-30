/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.value.userdata;

import net.mobtalker.mobtalkerscript.v3.value.MtsTable;

public class MtsNatives
{
    public static MtsTable createLibrary( Class<?> first, Class<?>... others )
    {
        MtsTable lib = LibraryAdapter.bind( first );
        for ( Class<?> c : others )
        {
            LibraryAdapter.bind( c, lib );
        }
        
        return lib;
    }
    
    public static MtsTable createLibrary( Object first, Object... others )
    {
        MtsTable lib = LibraryAdapter.bind( first );
        for ( Object o : others )
        {
            LibraryAdapter.bind( o, lib );
        }
        
        return lib;
    }
    
    public static void createLibrary( MtsTable table, Class<?> first, Class<?>... others )
    {
        LibraryAdapter.bind( first, table );
        for ( Class<?> c : others )
        {
            LibraryAdapter.bind( c, table );
        }
    }
    
    public static void createLibrary( MtsTable table, Object first, Object... others )
    {
        LibraryAdapter.bind( first, table );
        for ( Object o : others )
        {
            LibraryAdapter.bind( o, table );
        }
    }
}
