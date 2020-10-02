/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.instruction;

import java.io.*;

import net.mobtalker.mobtalkerscript.v3.*;
import net.mobtalker.mobtalkerscript.v3.value.MtsValue;

/**
 * Jump instruction that only jumps if the top of the stack is not true.
 */
public final class InstrNForPrep extends MtsIndexedInstruction
{
    /* package */InstrNForPrep( int i )
    {
        super( i );
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsValue stepVal = frame.pop();
        MtsValue limitVal = frame.pop();
        MtsValue loopVal = frame.pop();
        
        loopVal = loopVal.substract( stepVal );
        
        frame.getLocal( _index ).set( loopVal );
        frame.getLocal( _index + 1 ).set( limitVal );
        frame.getLocal( _index + 2 ).set( stepVal );
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
        return Instructions.NFP_NAME + " " + _index;
    }
    
    @Override
    public String toString( MtsFunctionPrototype proto )
    {
        return toString() + " (" + proto.getLocalDescription( _index ) + ")";
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.NFP );
        stream.writeByte( _index );
    }
}
