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

import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;
import net.mobtalker.mobtalkerscript.v2.*;
import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

public class ConsoleCommandLib
{
    private final MtsGlobals _G;
    
    public ConsoleCommandLib( MtsGlobals g )
    {
        _G = g;
    }
    
    // ========================================
    
    @MtsNativeFunction( name = Reference.FunctionNames.COMMAND_SAY )
    public void ShowText( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
    {
        StringBuilder s = new StringBuilder();
        
        if ( !arg1.isNil() )
        {
            String name = arg1.asString().asJavaString();
            
            s.append( '[' ).append( name ).append( "] " );
        }
        
        s.append( arg2.asString() );
        
        if ( !arg3.asBoolean().toJavaValue() )
            s.append( " \u25BA" );
        else
            s.append( " \u25A0" );
        
        _G.out.println( s.toString() );
    }
    
    // ========================================
    
    @MtsNativeFunction( name = Reference.FunctionNames.COMMAND_SHOW )
    public void ShowSprite( MtsVarArgs args )
    {
        MtsValue characterArg = args.get( 0 );
        MtsValue pathArg = args.get( 1 );
        MtsValue positionArg = args.get( 2 );
        MtsValue offsetXArg = args.get( 3 );
        MtsValue offsetYArg = args.get( 4 );
        //        MtsValue effectArg = args.get( 5 );
        
        // Character
        StringBuilder pathBuilder = new StringBuilder();
        if ( characterArg.isTable() )
        {
            String basePath = characterArg.get( "SpritePath" ).toMtsString().asJavaString();
            pathBuilder.append( basePath );
        }
        else
        {
            String basePath = characterArg.asString().asJavaString();
            pathBuilder.append( basePath );
        }
        
        // Path
        if ( pathArg.isTable() )
        {
            for ( MtsValue pathPart : pathArg.asTable().listView() )
            {
                pathBuilder.append( "/" ).append( pathPart.toMtsString().asJavaString() );
            }
        }
        else
        {
            pathBuilder.append( "/" ).append( pathArg.toMtsString() );
        }
        
        String path = pathBuilder.toString();
        
        if ( !path.endsWith( ".png" ) )
        {
            path += ".png";
        }
        
        // Position
        String position;
        if ( positionArg.isNil() )
        {
            position = "center";
        }
        else
        {
            position = positionArg.toMtsString().asJavaString();
        }
        
        // Offset
        int offsetX = offsetXArg.asNumber().asJavaInt();
        int offsetY = offsetYArg.asNumber().asJavaInt();
        
        // Effect
        String effect = "none";
        
        _G.out.println( "Displaying sprite '"
                + path
                + "' at "
                + position
                + "["
                + offsetX
                + ","
                + offsetY
                + "] with effect '"
                + effect + "'." );
    }
    
    // ========================================
    
    @MtsNativeFunction( name = Reference.FunctionNames.COMMAND_MENU )
    public MtsValue ShowMenu( MtsVarArgs args )
    {
        if ( !args.get( 0 ).isNil() )
        {
            String caption = args.get( 0 ).asString().asJavaString();
            _G.out.println( caption );
        }
        else
        {
            _G.out.println( "Make your choice" );
        }
        
        int nOptions = args.count() - 1;
        if ( nOptions < 1 )
            throw new ScriptRuntimeException( "Must have at least 1 option!" );
        
        for ( int i = 1; i <= nOptions; i++ )
        {
            _G.out.println( "  " + i + ": " + args.get( i ).asString().asJavaString() );
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
}
