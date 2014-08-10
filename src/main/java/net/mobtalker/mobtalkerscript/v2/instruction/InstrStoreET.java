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

import net.mobtalker.mobtalkerscript.v2.MtsFrame;
import net.mobtalker.mobtalkerscript.v2.value.MtsValue;

public class InstrStoreET extends MtsInstruction
{
    private final int _external;
    private final int _constant;
    
    // ========================================
    
    /* package */InstrStoreET( int external, int constant )
    {
        _external = external;
        _constant = constant;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsValue table = frame.getExternal( _external ).get();
        MtsValue key = frame.getConstant( _constant );
        table.set( key, frame.pop(), true );
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
        return "STOREET " + _external + " " + _constant;
    }
}
