/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.instruction;

import java.io.*;

import net.mobtalker.mobtalkerscript.v3.value.MtsValue;

public final class InstrPow extends MtsBinaryArithmeticInstruction
{
    @Override
    protected MtsValue calculate( MtsValue a, MtsValue b )
    {
        return a.powerTo( b );
    }
    
    @Override
    public String toString()
    {
        return Instructions.POW_NAME;
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.POW );
    }
}
