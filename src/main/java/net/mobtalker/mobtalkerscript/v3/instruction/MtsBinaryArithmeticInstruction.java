/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.instruction;

import net.mobtalker.mobtalkerscript.v3.MtsFrame;
import net.mobtalker.mobtalkerscript.v3.value.MtsValue;

/* package */abstract class MtsBinaryArithmeticInstruction extends MtsInstruction
{
    @Override
    public final void execute( MtsFrame frame )
    {
        MtsValue b = frame.pop();
        frame.push( calculate( frame.pop(), b ) );
    }
    
    @Override
    public final int stackSizeChange()
    {
        return -1;
    }
    
    // ========================================
    
    protected abstract MtsValue calculate( MtsValue a, MtsValue b );
}
