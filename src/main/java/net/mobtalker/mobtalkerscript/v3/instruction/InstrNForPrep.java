/*
 * Copyright (C) 2013-2020 Chimaine, MobTalkerScript contributors
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
