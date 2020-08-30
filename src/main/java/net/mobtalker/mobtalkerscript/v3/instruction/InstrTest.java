/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.instruction;

import java.io.*;

import net.mobtalker.mobtalkerscript.v3.MtsFrame;

/**
 * Jump instruction that only jumps if the top of the stack is not true.
 */
public final class InstrTest extends MtsJumpInstruction
{
    /* package */InstrTest()
    {
        super();
    }
    
    /* package */InstrTest( int offset )
    {
        super( offset );
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        if ( !frame.pop().isTrue() )
        {
            super.execute( frame );
        }
    }
    
    @Override
    public int stackSizeChange()
    {
        return -1;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return Instructions.TEST_NAME + " " + getDistance();
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.TEST );
        stream.writeShort( getDistance() );
    }
}
