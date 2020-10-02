/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.instruction;

import java.io.*;

public class InstrJump extends MtsJumpInstruction
{
    /* package */InstrJump()
    {
        super();
    }
    
    /* package */InstrJump( int distance )
    {
        super( distance );
    }
    
    // ========================================
    
    @Override
    public int stackSizeChange()
    {
        return 0;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return Instructions.JMP_NAME + " " + getDistance();
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.JMP );
        stream.writeShort( getDistance() );
    }
}
