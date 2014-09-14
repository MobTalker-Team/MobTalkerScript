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
package net.mobtalker.mobtalkerscript.v2.lib.console;

import static net.mobtalker.mobtalkerscript.v2.MtsCheck.*;
import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;
import net.mobtalker.mobtalkerscript.v2.*;
import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

import org.apache.commons.lang3.StringUtils;

public class ConsoleCommandLib
{
    private final MtsGlobals _G;
    
    public ConsoleCommandLib( MtsGlobals g )
    {
        _G = g;
    }
    
    // ========================================
    
    @MtsNativeFunction( name = Reference.FunctionNames.COMMAND_SAY )
    public void ShowText( MtsValue argTalker, MtsValue argText, MtsValue argIsLast )
    {
        StringBuilder s = new StringBuilder();
        
        if ( !argTalker.isNil() )
        {
            String talker = argTalker.asString().asJavaString();
            
            if ( !StringUtils.isWhitespace( talker ) )
            {
                s.append( '[' ).append( talker ).append( "] " );
            }
        }
        
        s.append( checkString( argText ) );
        s.append( isTrue( argIsLast ) ? " \u25A0" : " \u25BA" );
        
        _G.out.println( s.toString() );
    }
    
    // ========================================
    
    @MtsNativeFunction( name = Reference.FunctionNames.COMMAND_MENU )
    public MtsValue ShowMenu( MtsVarArgs args )
    {
        String caption = checkString( args, 0, "Make your choice" );
        
        int nOptions = args.count() - 1;
        if ( nOptions < 1 )
            throw new ScriptRuntimeException( "Must have at least 1 option!" );

        _G.out.println( caption );
        for ( int i = 1; i <= nOptions; i++ )
        {
            _G.out.println( "  " + i + ": " + checkString( args, i ) );
        }
        
        for ( ;; )
        {
            _G.out.print( "> " );
            try
            {
                String input = _G.in.readLine();
                int choice = Integer.parseInt( input ) - 1;
                if ( ( 0 <= choice ) && ( choice < nOptions ) )
                    return valueOf( choice );
            }
            catch ( Exception ex )
            {
                _G.out.println( ex.getMessage() );
            }
        }
    }
    
    // ========================================
    
    @MtsNativeFunction( name = Reference.FunctionNames.COMMAND_SHOW )
    public void ShowSprite( MtsVarArgs args )
    {
        String group = checkString( args, 0 );
        String subPath = checkString( args, 1 );
        String position = checkString( args, 2, "bottom" );
        int offsetX = checkInteger( args, 3, 0 );
        int offsetY = checkInteger( args, 4, 0 );
        String effect = "none";
        
        _G.out.println( "Displaying sprite '" + group + "/" + subPath + "' at " + position
                        + "[" + offsetX + "," + offsetY + "] with effect '" + effect + "'." );
    }

    @MtsNativeFunction( name = Reference.FunctionNames.COMMAND_SCENE )
    public void showScene( MtsValue argPath, MtsValue argMode )
    {
        String path = checkString( argPath );
        String mode = checkString( argMode, 1, "fill" );
        
        _G.out.println( "Displaying scene '" + path + "' as '" + mode + "'." );
    }

    // ========================================

    @MtsNativeFunction( name = Reference.FunctionNames.COMMAND_HIDE )
    public void hideTexture( MtsValue arg1 )
    {
        String group = checkString( arg1, 0 );

        _G.out.println( "Hiding texture '" + group + "'." );
    }
}
