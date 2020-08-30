/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.instruction;

import net.mobtalker.mobtalkerscript.v3.MtsFrame;
import net.mobtalker.mobtalkerscript.v3.value.*;

/* package */abstract class MtsComparisonInstruction extends MtsInstruction
{
    @Override
    public final void execute( MtsFrame frame )
    {
        MtsValue b = frame.pop();
        frame.push( compare( frame.pop(), b ) );
    }
    
    protected abstract MtsBoolean compare( MtsValue a, MtsValue b );
    
    @Override
    public final int stackSizeChange()
    {
        return -1;
    }
}
