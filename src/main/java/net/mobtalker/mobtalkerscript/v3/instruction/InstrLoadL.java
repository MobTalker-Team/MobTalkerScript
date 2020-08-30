/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.instruction;

import java.io.*;

import net.mobtalker.mobtalkerscript.v3.*;

public final class InstrLoadL extends MtsIndexedInstruction
{
    /* package */InstrLoadL( int i )
    {
        super( i );
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        frame.pushLocal( _index );
    }
    
    @Override
    public int stackSizeChange()
    {
        return 1;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return Instructions.LDL_NAME + " " + _index;
    }
    
    @Override
    public String toString( MtsFunctionPrototype proto )
    {
        return toString() + " (" + proto.getLocalDescription( _index ).getName() + ")";
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.LDL );
        stream.writeByte( _index );
    }
}
