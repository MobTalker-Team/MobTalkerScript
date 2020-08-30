/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.compiler;

import static com.google.common.base.Preconditions.*;
import net.mobtalker.mobtalkerscript.v3.instruction.MtsJumpInstruction;

/**
 * Wrapper around a {@link MtsJumpInstruction} whose target instruction is not yet determined.
 */
public class PendingJump
{
    private final MtsJumpInstruction _instr;
    private final int _sourceIndex;
    
    // ========================================
    
    /**
     * @param instr The instruction to wrap.
     * @param sourceIndex The (absolute) index of <code>instr</code>.
     */
    public PendingJump( MtsJumpInstruction instr, int sourceIndex )
    {
        checkNotNull( instr );
        checkArgument( sourceIndex >= 0 );
        
        _instr = instr;
        _sourceIndex = sourceIndex;
    }
    
    // ========================================
    
    public MtsJumpInstruction getInstruction()
    {
        return _instr;
    }
    
    public int getSourceIndex()
    {
        return _sourceIndex;
    }
    
    // ========================================
    
    /**
     * Sets the absolute jump target.
     */
    public void setTarget( int targetIndex )
    {
        setDistance( targetIndex - _sourceIndex );
    }
    
    /**
     * Sets the relative jump target.
     */
    public void setDistance( int distance )
    {
        _instr.setDistance( distance );
    }
}
