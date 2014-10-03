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
package net.mobtalker.mobtalkerscript.api;

public class EffectInfo
{
    public final String Name;
    public final int Amplifier;
    public final int Duration;
    
    public EffectInfo( String name, int amplifier, int duration )
    {
        Name = name;
        Amplifier = amplifier;
        Duration = duration;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = ( prime * result ) + Amplifier;
        result = ( prime * result ) + Duration;
        result = ( prime * result ) + Name.hashCode();
        return result;
    }
    
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( !( obj instanceof EffectInfo ) )
            return false;
        
        return equals( (EffectInfo) obj );
    }
    
    public boolean equals( EffectInfo other )
    {
        if ( Amplifier != other.Amplifier )
            return false;
        if ( Duration != other.Duration )
            return false;
        if ( !Name.equals( other.Name ) )
            return false;
        return true;
    }
}
