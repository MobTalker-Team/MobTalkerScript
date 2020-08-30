/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.value;

public abstract class MtsFunction extends MtsValue
{
    @Override
    public abstract MtsVarargs call( MtsVarargs args );
    
    // ========================================
    
    @Override
    public boolean isFunction()
    {
        return true;
    }
    
    @Override
    public MtsFunction asFunction()
    {
        return this;
    }
    
    @Override
    public MtsType getType()
    {
        return MtsType.FUNCTION;
    }
}
