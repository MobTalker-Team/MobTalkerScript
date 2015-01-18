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
package net.mobtalker.mobtalkerscript.v3;

import net.mobtalker.mobtalkerscript.v3.value.MtsType;

public class MtsArgumentException extends MtsRuntimeException
{
    public MtsArgumentException( String msg, Object... args )
    {
        super( msg, args );
    }
    
    public MtsArgumentException( int argIndex, String msg, Object... args )
    {
        super( "bad argument #" + argIndex + " (" + msg + ")", args );
    }
    
    public MtsArgumentException( MtsType expected, MtsType actual )
    {
        super( "bad argument (%s expected, got %s)", expected, actual );
    }
    
    public MtsArgumentException( int argIndex, MtsType expected, MtsType actual )
    {
        super( "bad argument #%s (%s expected, got %s)", argIndex, expected, actual );
    }
}
