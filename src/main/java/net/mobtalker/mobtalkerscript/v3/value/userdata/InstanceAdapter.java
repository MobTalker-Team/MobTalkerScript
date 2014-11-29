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
package net.mobtalker.mobtalkerscript.v3.value.userdata;

import net.mobtalker.mobtalkerscript.v3.value.*;

public class InstanceAdapter extends MtsUserdata
{
    private final ClassAdapter _classAdapter;
    
    // ========================================
    
    /* package */InstanceAdapter( ClassAdapter classAdapter, Object value )
    {
        super( value );
        _classAdapter = classAdapter;
    }
    
    // ========================================
    
    @Override
    protected MtsValue doGet( MtsValue key )
    {
        MtsValue method = null;
        if ( key.isString() )
        {
            method = _classAdapter.getMethod( key.asString().toJava() );
        }
        
        return method == null ? NIL : method;
    }
    
    // ========================================
    
    @Override
    public MtsType getType()
    {
        return _classAdapter.getType();
    }
}
