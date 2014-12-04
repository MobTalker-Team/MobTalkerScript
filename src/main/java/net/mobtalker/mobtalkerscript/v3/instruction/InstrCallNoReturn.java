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

import net.mobtalker.mobtalkerscript.v3.MtsFrame;
import net.mobtalker.mobtalkerscript.v3.value.*;

public final class InstrCallNoReturn extends InstrCall
{
    InstrCallNoReturn( int nArgs )
    {
        super( nArgs, 0 );
    }
    
    // ========================================
    
    @Override
    protected final void pushResults( MtsFrame frame, MtsValue result )
    {
        if ( result instanceof MtsTailcall )
        {
            ( (MtsTailcall) result ).evaluate();
        }
    }
}
