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

import net.mobtalker.mobtalkerscript.api.library.IInteractionCommandLibLogic;
import net.mobtalker.mobtalkerscript.v3.MtsGlobals;

public class ConsoleInteractionCommandLibLogic implements IInteractionCommandLibLogic
{
    private final MtsGlobals _G;
    
    // ========================================
    
    public ConsoleInteractionCommandLibLogic( MtsGlobals g )
    {
        _G = g;
    }
    
    // ========================================
    
    @Override
    public void showText( String character, String text, boolean isLast )
    {
        _G.out.println( "[" + character + "] " + text + ( isLast ? " \u25A0" : " \u25BA" ) );
    }
    
    @Override
    public int showMenu( String caption, List<String> options )
    {
        _G.out.println( caption );
        
        int nOptions = options.size();
        for ( int i = 0; i < nOptions; i++ )
        {
            _G.out.println( "  " + ( i + 1 ) + ": " + options.get( i ) );
        }
        
        for ( ;; )
        {
            _G.out.print( "Your answer: " );
            
            try
            {
                int choice = Integer.parseInt( _G.in.readLine() );
                if ( ( 0 < choice ) && ( choice <= nOptions ) )
                    return choice;
            }
            catch ( NumberFormatException ex )
            {
                _G.out.println( "Please enter the number of the choice you made!" );
            }
            catch ( Exception ex )
            {
                _G.out.println( ex.getMessage() );
            }
        }
    }
    
    @Override
    public void showSprite( String group, String path, String screenPositon, int screenOffsetX, int screenOffsetY )
    {
        _G.out.println( "Displaying sprite '" + group + "/" + path + "' at " + screenPositon
                        + "[" + screenOffsetX + "," + screenOffsetY + "]" );
    }
    
    @Override
    public void showScene( String path, String mode )
    {
        _G.out.println( "Displaying scene '" + path + "' as '" + mode + "'." );
    }
    
    @Override
    public void hideTexture( String group )
    {
        _G.out.println( "Hiding texture '" + group + "'." );
    }
}
