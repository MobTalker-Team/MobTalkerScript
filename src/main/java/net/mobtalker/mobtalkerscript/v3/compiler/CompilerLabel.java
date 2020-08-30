/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.compiler;

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
