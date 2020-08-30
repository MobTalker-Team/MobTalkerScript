/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.instruction;

import java.io.*;

import net.mobtalker.mobtalkerscript.v3.*;

public class InstrLoadTC extends MtsIndexedInstruction
{
    public InstrLoadTC( int i )
    {
        super( i );
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        frame.push( frame.pop().get( frame.getConstant( _index ), true ) );
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
        return Instructions.LDTC_NAME + " " + _index;
    }
    
    @Override
    public String toString( MtsFunctionPrototype proto )
    {
        return toString() + " (" + proto.getConstant( _index ) + ")";
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.LDTC );
        stream.writeShort( _index );
    }
}
