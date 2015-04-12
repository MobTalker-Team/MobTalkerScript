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
package net.mobtalker.mobtalkerscript.api;

public class ItemInfo
{
    public final String Name;
    public final int Meta;

    public ItemInfo( String name, int meta )
    {
        Name = name;
        Meta = meta;
    }

    // ========================================

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = ( prime * result ) + Meta;
        result = ( prime * result ) + Name.hashCode();
        return result;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
            return true;
        if ( ( obj == null ) || !( obj instanceof ItemInfo ) )
            return false;

        return equals( (ItemInfo) obj );
    }

    public boolean equals( ItemInfo other )
    {
        if ( !Name.equals( other.Name ) )
            return false;
        if ( Meta != other.Meta )
            return false;
        return true;
    }
}
