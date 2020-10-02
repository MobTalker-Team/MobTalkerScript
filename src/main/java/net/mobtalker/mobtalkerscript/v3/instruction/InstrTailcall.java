/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.instruction;

import java.io.*;

import net.mobtalker.mobtalkerscript.v3.MtsFrame;
import net.mobtalker.mobtalkerscript.v3.value.*;

public final class InstrTailcall extends InstrCall
{
    /* package */InstrTailcall( int nArgs )
    {
        super( nArgs, -1 );
    }
    
    // ========================================
    
    @Override
    protected final MtsVarargs getResults( MtsValue target, MtsVarargs args )
    {
        return new MtsTailcall( target, args );
    }
    
    @Override
    protected final void pushResults( MtsFrame frame, MtsVarargs results )
    {
        frame.push( results );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return Instructions.TCALL_NAME + " " + getArgCount();
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.TCALL );
        stream.writeByte( getArgCount() );
    }
}
