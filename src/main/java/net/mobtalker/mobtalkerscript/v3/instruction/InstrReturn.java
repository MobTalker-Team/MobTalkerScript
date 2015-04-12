/*
 * Copyright (C) 2013-2015 Chimaine
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
import net.mobtalker.mobtalkerscript.v3.value.MtsTailcall;

public final class InstrReturn extends MtsInstruction
{
    private final int _count;

    // ========================================

    /* package */InstrReturn( int count )
    {
        assert count >= 0;
        _count = count;
    }

    // ========================================

    @Override
    public void execute( MtsFrame frame )
    {
        assert frame.count() == _count;

        if ( ( _count > 0 ) && ( frame.peek() instanceof MtsTailcall ) )
            return;

        frame.pack();
    }

    @Override
    public boolean exits()
    {
        return true;
    }

    @Override
    public int stackSizeChange()
    {
        return 1 - _count;
    }

    // ========================================

    @Override
    public String toString()
    {
        return Instructions.RET_NAME + " " + _count;
    }

    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.RET );
        stream.writeByte( _count );
    }
}
