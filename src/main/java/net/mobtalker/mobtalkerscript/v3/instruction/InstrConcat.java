/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.instruction;

import java.io.*;

import net.mobtalker.mobtalkerscript.v3.MtsFrame;
import net.mobtalker.mobtalkerscript.v3.value.MtsString;

public class InstrConcat extends MtsInstruction
{
    private final int _count;
    
    // ========================================
    
    /* package */InstrConcat( int count )
    {
        _count = count;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        
        frame.push( MtsString.concat( frame.pop( _count ) ) );
    }
    
    @Override
    public int stackSizeChange()
    {
        return -_count + 1;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return Instructions.CONC_NAME + " " + _count;
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.CONC );
        stream.writeByte( _count );
    }
}
