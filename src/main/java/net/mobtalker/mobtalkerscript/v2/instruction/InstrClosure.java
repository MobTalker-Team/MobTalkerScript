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
package net.mobtalker.mobtalkerscript.v2.instruction;

import java.util.List;

import net.mobtalker.mobtalkerscript.v2.*;
import net.mobtalker.mobtalkerscript.v2.value.MtsClosure;

import com.google.common.collect.Lists;

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
        List<FrameValue> externals = createExternals( frame, prototype );
        MtsClosure closure = new MtsClosure( prototype, externals );
        
        frame.push( closure );
    }
    
    private List<FrameValue> createExternals( MtsFrame frame, MtsFunctionPrototype prototype )
    {
        int nExternals = prototype.getExternalCount();
        List<FrameValue> externals = Lists.newArrayListWithCapacity( nExternals );
        
        for ( int i = 0; i < nExternals; i++ )
        {
            ExternalDescription descr = prototype.getExternalDescription( i );
            
            if ( descr.isParentLocal() )
                externals.add( frame.getLocal( descr.getParentIndex() ) );
            else
                externals.add( frame.getExternal( descr.getParentIndex() ) );
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
        return "CLOSURE " + _index;
    }
}
