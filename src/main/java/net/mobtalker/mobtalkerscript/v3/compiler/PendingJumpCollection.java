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
package net.mobtalker.mobtalkerscript.v3.compiler;

import java.util.ArrayList;

import com.google.common.collect.Lists;

public class PendingJumpCollection
{
    private final ArrayList<PendingJump> _jumps;
    
    // ========================================
    
    public PendingJumpCollection()
    {
        _jumps = Lists.newArrayListWithExpectedSize( 4 );
    }
    
    // ========================================
    
    public void add( PendingJump jump )
    {
        _jumps.add( jump );
    }
    
    public void setTarget( int targetIndex )
    {
        for ( PendingJump jump : _jumps )
        {
            jump.setTarget( targetIndex );
        }
    }
    
    public void setDistance( int distance )
    {
        for ( PendingJump jump : _jumps )
        {
            jump.setDistance( distance );
        }
    }
}
