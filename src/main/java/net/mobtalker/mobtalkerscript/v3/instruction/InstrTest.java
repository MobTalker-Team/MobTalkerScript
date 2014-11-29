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

import static net.mobtalker.mobtalkerscript.v3.value.MtsValue.*;
import net.mobtalker.mobtalkerscript.v3.MtsFrame;

/**
 * Jump instruction that only jumps if the top of the stack is not true.
 */
public final class InstrTest extends MtsJumpInstruction
{
    /* package */InstrTest()
    {
        super();
    }
    
    /* package */InstrTest( int offset )
    {
        super( offset );
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        if ( !isTrue( frame.pop() ) )
        {
            super.execute( frame );
        }
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
        return "TEST " + getDistance();
    }
}
