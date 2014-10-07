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

import net.mobtalker.mobtalkerscript.v2.MtsFrame;
import net.mobtalker.mobtalkerscript.v2.value.*;

public final class InstrTailcall extends MtsInstruction
{
    private final int _nArgs;
    private final int _nReturn;
    
    // ========================================
    
    /* package */InstrTailcall( int nArgs, int nReturn )
    {
        _nArgs = nArgs;
        _nReturn = nReturn;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsValue[] args = new MtsValue[_nArgs];
        for ( int i = _nArgs - 1; i >= 0; i-- )
        {
            args[i] = frame.pop();
        }
        
        MtsValue target = frame.pop();
        
        frame.push( new MtsTailcall( target, MtsVarArgs.of( args ), _nReturn ) );
    }
    
    @Override
    public boolean exits()
    {
        return false;
    }
    
    @Override
    public int stackSizeChange()
    {
        return 1 - _nReturn;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "TAILCALL " + _nArgs + " " + _nReturn;
    }
}
