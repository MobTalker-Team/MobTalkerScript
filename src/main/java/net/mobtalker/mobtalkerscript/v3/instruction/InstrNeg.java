/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.instruction;

import java.io.*;

import net.mobtalker.mobtalkerscript.v3.MtsFrame;

public class InstrNeg extends MtsInstruction
{
    @Override
    public void execute( MtsFrame frame )
    {
        frame.push( frame.pop().unaryMinus() );
    }
    
    @Override
    public int stackSizeChange()
    {
        return 0;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return Instructions.NEG_NAME;
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.NEG );
    }
}
