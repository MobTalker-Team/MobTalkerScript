/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.instruction;

import java.io.*;

import net.mobtalker.mobtalkerscript.v3.*;
import net.mobtalker.mobtalkerscript.v3.value.MtsValue;

public final class InstrLoadM extends MtsIndexedInstruction
{
    /* package */InstrLoadM( int index )
    {
        super( index );
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsValue table = frame.pop();
        MtsValue key = frame.getConstant( _index );
        MtsValue method = table.get( key, true );
        
        frame.push( method );
        frame.push( table );
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
        return Instructions.LDM_NAME + " " + _index;
    }
    
    @Override
    public String toString( MtsFunctionPrototype proto )
    {
        return toString() + " (" + proto.getConstant( _index ).asString().toJava() + ")";
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.LDM );
        stream.writeShort( _index );
    }
}
