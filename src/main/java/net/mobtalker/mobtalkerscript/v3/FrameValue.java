/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3;

import static net.mobtalker.mobtalkerscript.v3.value.MtsValue.*;
import net.mobtalker.mobtalkerscript.v3.value.MtsValue;

public final class FrameValue
{
    private MtsValue _value;
    
    // ========================================
    
    public FrameValue()
    {
        this( Nil );
    }
    
    public FrameValue( MtsValue value )
    {
        _value = value;
    }
    
    // ========================================
    
    public MtsValue get()
    {
        return _value;
    }
    
    public void set( MtsValue value )
    {
        _value = value;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return _value.toString();
    }
}
