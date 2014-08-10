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

import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;
import net.mobtalker.mobtalkerscript.v2.MtsFrame;
import net.mobtalker.mobtalkerscript.v2.value.*;

public final class InstrReturn extends MtsInstruction
{
    private final int _count;
    
    // ========================================
    
    /* package */InstrReturn( int count )
    {
        _count = count;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsValue result;
        if ( _count == 0 )
            result = EMPTY_VARARGS;
        else if ( _count == 1 )
            result = frame.pop();
        else
            result = MtsVarArgs.of( frame.pop( _count ) );
        
        frame.push( result );
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
        return "RETURN " + _count;
    }
}
