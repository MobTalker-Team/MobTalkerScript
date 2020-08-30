/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.instruction;

import net.mobtalker.mobtalkerscript.v3.MtsFunctionPrototype;

/* package */abstract class MtsIndexedInstruction extends MtsInstruction
{
    protected int _index;
    
    // ========================================
    
    protected MtsIndexedInstruction( int i )
    {
        _index = i;
    }
    
    // ========================================
    
    @Override
    public abstract String toString( MtsFunctionPrototype proto );
}
