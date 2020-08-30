/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.instruction;

import java.io.*;

import net.mobtalker.mobtalkerscript.v3.*;
import net.mobtalker.mobtalkerscript.v3.value.MtsClosure;

public class InstrClosure extends MtsIndexedInstruction
{
    /* package */InstrClosure( int prototype )
    {
        super( prototype );
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsFunctionPrototype prototype = frame.getClosure().getPrototype().getNestedPrototype( _index );
        MtsClosure closure = new MtsClosure( prototype, createExternals( frame, prototype ) );
        
        frame.push( closure );
    }
    
    private FrameValue[] createExternals( MtsFrame frame, MtsFunctionPrototype prototype )
    {
        int nExternals = prototype.getExternalCount();
        FrameValue[] externals = new FrameValue[nExternals];
        
        for ( int i = 0; i < nExternals; i++ )
        {
            ExternalDescription descr = prototype.getExternalDescription( i );
            externals[i] = descr.isParentLocal()
                    ? frame.getLocal( descr.getParentIndex() )
                    : frame.getExternal( descr.getParentIndex() );
        }
        
        return externals;
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
        return Instructions.CLSR_NAME + " " + _index;
    }
    
    @Override
    public String toString( MtsFunctionPrototype proto )
    {
        return toString() + " (" + proto.getNestedPrototype( _index ).getName() + ")";
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.CLSR );
        stream.writeByte( _index );
    }
}
