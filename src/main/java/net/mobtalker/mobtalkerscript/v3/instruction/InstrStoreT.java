/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.instruction;

import java.io.*;

import net.mobtalker.mobtalkerscript.v3.MtsFrame;
import net.mobtalker.mobtalkerscript.v3.value.MtsValue;

public class InstrStoreT extends MtsInstruction
{
    @Override
    public void execute( MtsFrame frame )
    {
        MtsValue key = frame.pop();
        MtsValue t = frame.pop();
        t.set( key, frame.pop(), true );
    }
    
    @Override
    public int stackSizeChange()
    {
        return -3;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return Instructions.STT_NAME;
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.STT );
    }
}
