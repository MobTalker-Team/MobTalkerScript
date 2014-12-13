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

public class MtsUserdata extends MtsValue
{
    protected final Object _value;
    
    // ========================================
    
    protected MtsUserdata( Object value )
    {
        _value = value;
    }
    
    // ========================================
    
    @Override
    public boolean isUserdata()
    {
        return true;
    }
    
    @Override
    public MtsUserdata asUserdata()
    {
        return this;
    }
    
    @Override
    public MtsType getType()
    {
        return MtsType.USERDATA;
    }
    
    public Object asJavaObject()
    {
        return _value;
    }
    
    @SuppressWarnings( "unchecked" )
    @Override
    public <T> T asNative() throws ClassCastException
    {
        return (T) _value;
    }
    
    // ========================================
    
    @Override
    public int hashCode()
    {
        return _value.hashCode();
    }
    
    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof MtsUserdata )
        {
            Object other = ( (MtsUserdata) obj ).asJavaObject();
            
            return _value == other;
        }
        
        return false;
    }
    
}
