/*
 * Copyright (C) 2013-2014 Chimaine
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mobtalker.mobtalkerscript.v2.instruction;

import net.mobtalker.mobtalkerscript.v2.*;

public class InstrDecrL extends MtsIndexedInstruction
{
    /* package */InstrDecrL( int i )
    {
        super( i );
    }
    
    // ========================================

    @Override
    public void execute( MtsFrame frame )
    {
        FrameValue register = frame.getLocal( _index );
        register.set( register.get().asNumber().decr() );
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
        return "DECRL " + _index;
    }
}
