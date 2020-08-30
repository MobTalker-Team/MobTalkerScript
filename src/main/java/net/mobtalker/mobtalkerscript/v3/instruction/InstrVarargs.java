/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.instruction;

import java.io.*;

import net.mobtalker.mobtalkerscript.v3.MtsFrame;

public class InstrVarargs extends MtsInstruction
{
    private final int _count;
    
    // ========================================
    
    /* package */InstrVarargs( int count )
    {
        _count = count;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        if ( _count == -1 )
            frame.pushVarargs();
        else
            frame.pushVarargs( _count );
    }
    
    @Override
    public int stackSizeChange()
    {
        return Math.max( _count, 1 );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return Instructions.VARARG_NAME + " " + _count;
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.VARARG );
        stream.writeByte( _count );
    }
}
