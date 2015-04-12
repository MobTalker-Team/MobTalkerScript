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
package net.mobtalker.mobtalkerscript.standalone.lib;

import java.util.List;

import joptsimple.internal.Strings;
import net.mobtalker.mobtalkerscript.api.library.IGameCommandLibLogic;
import net.mobtalker.mobtalkerscript.v3.MtsGlobals;

public class ConsoleGameCommandLibLogic implements IGameCommandLibLogic
{
    private final MtsGlobals _G;
    
    // ========================================
    
    public ConsoleGameCommandLibLogic( MtsGlobals g )
    {
        _G = g;
    }
    
    // ========================================

    @Override
    public int execute( String command, List<String> args )
    {
        _G.out.println( "Executing command '" + command + "' with arguments [" + Strings.join( args, "," ) + "]" );
        return 1;
    }
}
