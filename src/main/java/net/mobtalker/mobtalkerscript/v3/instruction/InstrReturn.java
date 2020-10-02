/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.instruction;

import java.io.*;

import net.mobtalker.mobtalkerscript.v3.MtsFrame;
import net.mobtalker.mobtalkerscript.v3.value.MtsTailcall;

public final class InstrReturn extends MtsInstruction
{
    private final int _count;
    
    // ========================================
    
    /* package */InstrReturn( int count )
    {
        assert count >= 0;
        _count = count;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        assert frame.count() == _count;
        
        if ( ( _count > 0 ) && ( frame.peek() instanceof MtsTailcall ) )
            return;
        
        frame.pack();
    }
    
    @Override
    public boolean exits()
    {
        return true;
    }
    
    @Override
    public int stackSizeChange()
    {
        return 1 - _count;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return Instructions.RET_NAME + " " + _count;
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.RET );
        stream.writeByte( _count );
    }
}
