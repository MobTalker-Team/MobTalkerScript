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

import net.mobtalker.mobtalkerscript.v2.*;
import net.mobtalker.mobtalkerscript.v2.value.*;

/* package */abstract class MtsArithmeticInstruction extends MtsInstruction
{
    @Override
    public final void execute( MtsFrame frame )
    {
        MtsNumber b = coerce( frame.pop() );
        frame.push( calculate( coerce( frame.pop() ), b ) );
    }
    
    @Override
    public final int stackSizeChange()
    {
        return -1;
    }
    
    // ========================================
    
    public static MtsNumber coerce( MtsValue x )
    {
        if ( x.isNumber() )
            return x.asNumber();
        if ( x.isString() )
            return x.toMtsNumber();
        
        throw new ScriptRuntimeException( "attempt to perform arithmetic on a %s value", x.getType() );
    }
    
    protected abstract MtsNumber calculate( MtsNumber a, MtsNumber b );
}
