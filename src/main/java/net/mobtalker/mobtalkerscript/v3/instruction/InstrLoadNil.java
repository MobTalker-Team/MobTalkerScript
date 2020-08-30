/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.instruction;

import java.io.*;

import net.mobtalker.mobtalkerscript.v3.MtsFrame;

public final class InstrLoadNil extends MtsInstruction
{
    private final int _count;
    
    // ========================================
    
    public InstrLoadNil( int count )
    {
        _count = count;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        frame.pushNil( _count );
    }
    
    @Override
    public int stackSizeChange()
    {
        return _count;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return Instructions.LDNIL_NAME + " " + _count;
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.LDNIL );
        stream.writeByte( _count );
    }
}