/*
 * Copyright (C) 2013-2014 Chimaine
 * 
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
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
