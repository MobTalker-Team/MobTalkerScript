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

import net.mobtalker.mobtalkerscript.v3.*;

public final class InstrStoreL extends MtsIndexedInstruction
{
    /* package */InstrStoreL( int i )
    {
        super( i );
    }

    // ========================================

    @Override
    public void execute( MtsFrame frame )
    {
        frame.storeLocal( _index );
    }

    @Override
    public int stackSizeChange()
    {
        return -1;
    }

    // ========================================

    @Override
    public String toString()
    {
        return Instructions.STL_NAME + " " + _index;
    }

    @Override
    public String toString( MtsFunctionPrototype proto )
    {
        return toString() + " (" + proto.getLocalDescription( _index ).getName() + ")";
    }

    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.STL );
        stream.writeByte( _index );
    }
}
