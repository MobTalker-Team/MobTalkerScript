package net.mobtalker.mobtalkerscript.standalone.lib;

import java.util.List;

import net.mobtalker.mobtalkerscript.api.library.IInteractionCommandLibLogic;
import net.mobtalker.mobtalkerscript.v2.MtsGlobals;

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
