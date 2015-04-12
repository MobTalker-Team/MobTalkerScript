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

public class ConditionalState
{
    private PendingJump _pendingNext;
    private final PendingJumpCollection _pendingLeaves;

    // ========================================

    public ConditionalState()
    {
        _pendingLeaves = new PendingJumpCollection();
    }

    // ========================================

    public void setPendingNext( PendingJump jump )
    {
        if ( _pendingNext != null )
            throw new IllegalStateException();

        _pendingNext = jump;
    }

    public void markBeginNext( int target )
    {
        if ( _pendingNext == null )
            throw new IllegalStateException();

        _pendingNext.setTarget( target );
        _pendingNext = null;
    }

    public void addPendingExit( PendingJump jump )
    {
        _pendingLeaves.add( jump );
    }

    public void setExitTarget( int target )
    {
        _pendingLeaves.setTarget( target );

        if ( _pendingNext != null )
        {
            markBeginNext( target );
        }
    }
}
