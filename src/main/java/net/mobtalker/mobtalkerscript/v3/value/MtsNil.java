/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.value;

public final class MtsNil extends MtsValue
{
    /* package */MtsNil()
    {}
    
    // ========================================
    
    @Override
    public boolean isNil()
    {
        return true;
    }
    
    @Override
    public MtsType getType()
    {
        return MtsType.NIL;
    }
    
    // ========================================
    
    @Override
    public MtsString toMtsString()
    {
        return MtsString.of( "nil" );
    }
    
    @Override
    public MtsBoolean toMtsBoolean()
    {
        return MtsBoolean.False;
    }
    
    // ========================================
    
    @Override
    public boolean isTrue()
    {
        return false;
    }
    
    @Override
    public Object toJava()
    {
        return null;
    }
    
    @Override
    public String toString()
    {
        return "nil";
    }
    
    @Override
    public int hashCode()
    {
        return 0;
    }
    
    @Override
    public boolean equals( Object obj )
    {
        return obj == this;
    }
    
}
