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
package net.mobtalker.mobtalkerscript.api.library;

import static net.mobtalker.mobtalkerscript.v2.MtsCheck.*;
import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;

import java.util.*;

import net.mobtalker.mobtalkerscript.v2.value.MtsVarArgs;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

import com.google.common.collect.Lists;

public class GameCommandLib
{
    private IGameCommandLibLogic _logic;
    
    // ========================================
    
    public GameCommandLib( IGameCommandLibLogic logic )
    {
        _logic = logic;
    }
    
    // ========================================
    
    public IGameCommandLibLogic getLogic()
    {
        return _logic;
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsVarArgs execute( MtsVarArgs args )
    {
        String command = checkString( args, 0 );
        
        List<String> arguments;
        int nArguments = args.count() - 1;
        
        if ( nArguments > 0 )
        {
            arguments = Lists.newArrayListWithCapacity( nArguments );
            for ( int i = 1; i <= nArguments; i++ )
            {
                arguments.add( checkString( args.get( i ), i ) );
            }
        }
        else
        {
            arguments = Collections.emptyList();
        }
        
        int executions = _logic.execute( command, arguments );
        return MtsVarArgs.of( valueOf( executions > 0 ), valueOf( executions ) );
    }
}