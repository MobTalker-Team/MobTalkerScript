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
package net.mobtalker.mobtalkerscript.v3.value;

public final class MtsNil extends MtsValue
{
    private final MtsString stringValue;
    
    // ========================================
    
    /* package */MtsNil()
    {
        stringValue = MtsString.of( "nil" );
    }
    
    // ========================================
    
    @Override
    public boolean isNil()
    {
        return true;
    }
    
    @Override
    public MtsType getType()
    {
        return MtsType.NIL;
    }
    
    // ========================================
    
    @Override
    public MtsString toMtsString()
    {
        return stringValue;
    }
    
    @Override
    public MtsBoolean toMtsBoolean()
    {
        return MtsBoolean.False;
    }
    
    // ========================================
    
    @Override
    public boolean isTrue()
    {
        return false;
    }
    
    @Override
    public Object toJava()
    {
        return null;
    }
    
    @Override
    public String toString()
    {
        return "nil";
    }
    
    @Override
    public int hashCode()
    {
        return 0;
    }
    
    @Override
    public boolean equals( Object obj )
    {
        return obj == this;
    }
    
}
