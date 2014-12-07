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

import net.mobtalker.mobtalkerscript.v3.MtsFrame;

public class InstrVarargs extends MtsInstruction
{
    private final int _count;
    
    // ========================================
    
    /* package */InstrVarargs( int count )
    {
        assert count > 0;
        _count = count;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        if ( _count == -1 )
            frame.pushVarargs();
        else
            frame.pushVarargs( _count );
    }
    
    @Override
    public int stackSizeChange()
    {
        return _count;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "VARARGS " + _count;
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.VARARG );
        stream.writeByte( _count );
    }
}
