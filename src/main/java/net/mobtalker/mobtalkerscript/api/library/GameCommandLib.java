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
package net.mobtalker.mobtalkerscript.api.library;

import static net.mobtalker.mobtalkerscript.v3.MtsCheck.*;

import java.util.*;

import net.mobtalker.mobtalkerscript.v3.value.*;
import net.mobtalker.mobtalkerscript.v3.value.userdata.MtsNativeFunction;

import com.google.common.collect.Lists;

public class GameCommandLib extends AbstractUnifiedLib<IGameCommandLibLogic>
{
    public GameCommandLib( IGameCommandLibLogic logic )
    {
        super( logic );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsVarargs execute( MtsVarargs args )
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
        return MtsVarargs.of( MtsBoolean.of( executions > 0 ), MtsNumber.of( executions ) );
    }
}