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
