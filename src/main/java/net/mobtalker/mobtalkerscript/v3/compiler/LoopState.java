/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.compiler;

/**
 * Stores information about a compiling loop block, e.g. references to different instructions.
 */
public class LoopState
{
    private final int _firstInstrIndex;
    private final PendingJumpCollection _breaks;
    
    // ========================================
    
    public LoopState( int firstInstrIndex )
    {
        _firstInstrIndex = firstInstrIndex;
        _breaks = new PendingJumpCollection();
    }
    
    // ========================================
    
    public int firstIndex()
    {
        return _firstInstrIndex;
    }
    
    public void addBreak( PendingJump jump )
    {
        _breaks.add( jump );
    }
    
    public void setBreakTarget( int target )
    {
        _breaks.setTarget( target );
    }
}
