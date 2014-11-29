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

import static net.mobtalker.mobtalkerscript.v3.MtsCheck.*;
import static net.mobtalker.mobtalkerscript.v3.value.MtsValue.*;

import java.util.List;

import net.mobtalker.mobtalkerscript.v3.ScriptRuntimeException;
import net.mobtalker.mobtalkerscript.v3.value.*;
import net.mobtalker.mobtalkerscript.v3.value.userdata.MtsNativeFunction;

import com.google.common.collect.Lists;

public class InteractionCommandLib
{
    private IInteractionCommandLibLogic _logic;
    
    // ========================================
    
    public InteractionCommandLib( IInteractionCommandLibLogic logic )
    {
        _logic = logic;
    }
    
    // ========================================
    
    public IInteractionCommandLibLogic getLogic()
    {
        return _logic;
    }
    
    // ========================================
    
    @MtsNativeFunction
    public void showText( MtsValue argName, MtsValue argText, MtsValue argIsLast )
    {
        _logic.showText( checkString( argName, 0, "" ),
                         checkString( argText, 1 ),
                         isTrue( argIsLast ) );
    }
    
    @MtsNativeFunction
    public MtsValue showMenu( MtsVarArgs args )
    {
        String caption = checkString( args, 0, "" );
        MtsValue arg1 = args.get( 1 );
        
        if ( arg1.isNil() )
            throw new ScriptRuntimeException( "must provide at least one option" );
        
        List<String> options;
        if ( arg1.isTable() )
        {
            MtsTable argOptions = arg1.asTable();
            
            options = Lists.newArrayListWithCapacity( argOptions.listSize() );
            for ( MtsValue arg : argOptions.listView() )
            {
                options.add( checkString( arg ) );
            }
        }
        else
        {
            options = Lists.newArrayListWithCapacity( args.count() - 1 );
            for ( int i = 1; i < args.count(); i++ )
            {
                options.add( checkString( args, i ) );
            }
        }
        
        return valueOf( _logic.showMenu( caption, options ) );
    }
    
    @MtsNativeFunction
    public void showSprite( MtsValue argGroup, MtsValue argSubPath,
                            MtsValue argPosition, MtsValue argOffsetX, MtsValue argOffsetY )
    {
        _logic.showSprite( checkString( argGroup, 0 ),
                           checkString( argSubPath, 1 ),
                           checkString( argPosition, 2, "bottom" ),
                           checkInteger( argOffsetX, 3, 0 ),
                           checkInteger( argOffsetY, 4, 0 ) );
    }
    
    @MtsNativeFunction
    public void showScene( MtsValue argPath, MtsValue argMode )
    {
        _logic.showScene( checkString( argPath, 0 ),
                          checkString( argMode, 1, "fill" ) );
    }
    
    @MtsNativeFunction
    public void hideTexture( MtsValue argGroup )
    {
        _logic.hideTexture( checkString( argGroup, 0 ) );
    }
}
