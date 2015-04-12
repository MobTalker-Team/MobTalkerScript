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

public class WorldPosition
{
    public final int X;
    public final int Y;
    public final int Z;
    
    // ========================================
    
    public WorldPosition( int x, int y, int z )
    {
        X = x;
        Y = y;
        Z = z;
    }
    
    // ========================================
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = ( prime * result ) + X;
        result = ( prime * result ) + Y;
        result = ( prime * result ) + Z;
        return result;
    }
    
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( !( obj instanceof WorldPosition ) )
            return false;
        
        return equals( (WorldPosition) obj );
    }
    
    public boolean equals( WorldPosition other )
    {
        if ( X != other.X )
            return false;
        if ( Y != other.Y )
            return false;
        if ( Z != other.Z )
            return false;
        return true;
    }
}
