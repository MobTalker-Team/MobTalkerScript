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

import net.mobtalker.mobtalkerscript.v3.MtsFrame;

public final class InstrAnd extends MtsJumpInstruction
{
    /* package */InstrAnd()
    {
        super();
    }
    
    /* package */InstrAnd( int offset )
    {
        super( offset );
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        if ( frame.peek().isTrue() )
        {
            frame.pop();
        }
        else
        {
            super.execute( frame );
        }
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
        return Instructions.AND_NAME + " " + getDistance();
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.AND );
        stream.writeByte( getDistance() );
    }
}
