/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.compiler;

import java.util.ArrayList;

public class PendingJumpCollection
{
    private final ArrayList<PendingJump> _jumps;
    
    // ========================================
    
    public PendingJumpCollection()
    {
        _jumps = new ArrayList<>( 4 );
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
