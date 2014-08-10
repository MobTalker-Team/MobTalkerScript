/*
 * Copyright (C) 2013-2014 Chimaine
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mobtalker.mobtalkerscript.v2.compiler;

import static com.google.common.base.Preconditions.*;

public class CompilerLabel
{
    private int _target;
    private final PendingJumpCollection _gotos;
    
    // ========================================
    
    public CompilerLabel()
    {
        _target = -1;
        _gotos = new PendingJumpCollection();
    }
    
    // ========================================
    
    public int getTarget()
    {
        return _target;
    }
    
    public void setTarget( int index )
    {
        checkState( _target == -1, "Index already set" );
        _target = index;
        _gotos.setTarget( index );
    }
    
    // ========================================
    
    public void addGoto( PendingJump jump )
    {
        if ( _target > -1 )
        {
            jump.setTarget( _target );
        }
        else
        {
            _gotos.add( jump );
        }
    }
}
