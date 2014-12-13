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
package net.mobtalker.mobtalkerscript.api;

import static net.mobtalker.mobtalkerscript.v3.MtsCheck.*;
import net.mobtalker.mobtalkerscript.v3.MtsArgumentException;
import net.mobtalker.mobtalkerscript.v3.value.MtsValue;

public class ScriptApiCheck
{
    public static EntityReaction checkReaction( MtsValue arg, int i )
    {
        String s = checkString( arg, i );
        
        EntityReaction result = EntityReaction.forName( s );
        if ( result == null )
            throw new MtsArgumentException( i, "'%s' is not a valid reaction", s );
        
        return result;
    }
}
