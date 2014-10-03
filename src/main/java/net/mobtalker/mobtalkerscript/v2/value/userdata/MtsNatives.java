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
package net.mobtalker.mobtalkerscript.v2.value.userdata;

import net.mobtalker.mobtalkerscript.v2.value.MtsTable;

public class MtsNatives
{
    public static InstanceAdapter wrapInstance( Object instance )
    {
        ClassAdapter classAdapter = ClassAdapter.get( instance.getClass() );
        return new InstanceAdapter( classAdapter, instance );
    }
    
    // ========================================
    
    public static MtsTable asMetatable( Object instance )
    {
        return LibraryAdapter.bind( instance );
    }
    
    // ========================================
    
    public static MtsTable createLibrary( Class<?> library )
    {
        return LibraryAdapter.bind( library );
    }
    
    public static MtsTable createLibrary( Object library )
    {
        return LibraryAdapter.bind( library );
    }
    
    public static void createLibrary( Class<?> library, MtsTable table )
    {
        LibraryAdapter.bind( library, table );
    }
    
    public static void createLibrary( Object library, MtsTable table )
    {
        LibraryAdapter.bind( library, table );
    }
}
