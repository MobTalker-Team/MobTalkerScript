package mobtalkerscript;

import java.util.logging.*;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.instruction.*;
import mobtalkerscript.mts.v2.lib.*;
import mobtalkerscript.mts.v2.lib.mobtalker.*;
import mobtalkerscript.mts.v2.value.*;
import mobtalkerscript.util.logging.*;

/**
 * Intended to be the entry point for an interactive shell for MobTalkerScript.
 */
public class MobTalkerScript
{
    
    public static void main( String[] args ) throws Exception
    {
        MtsLog.setLogger( Logger.getLogger( "MTS" ), true );
        MtsLog.CompilerLog.setLevel( Level.OFF );
        MtsLog.EngineLog.setLevel( Level.OFF );
        
        Instructions.class.getClass();
        
        MtsGlobals _G = new MtsGlobals();
        _G.loadLibrary( new MtsMathLib() );
        _G.loadLibrary( new MtsTableLib() );
        
        _G.loadLibrary( new ConsoleCommandLib() );
        _G.loadLibrary( new MobTalkerConsoleBaseLib( "Console", 0 ) );
        _G.loadLibrary( new MobTalkerConsoleCharacterLib() );
        
        _G.out.println( "MobTalkerScript " //
                        + _G.get( "_VERSION" ).asString().toJava()
                        + " Copyright \u00A9 2013-2014 Tobias Rummelt, mobtalker.net" );
        
        if ( args.length >= 1 )
        {
            _G.out.println( "Loading file '" + args[0] + "'" );
            
            MtsFunctionPrototype fileChunk = _G.loadFile( args[0] );
            new MtsClosure( fileChunk, _G ).call();
        }
        
        for ( ;; )
        {
            String line = _G.in.readLine();
            
            if ( line.startsWith( "exit" ) )
                break;
            
            MtsFunctionPrototype chunk;
            try
            {
                chunk = _G.loadString( line );
            }
            catch ( Exception ex )
            {
                _G.out.println( ex.getMessage() );
                continue;
            }
            
            new MtsClosure( chunk, _G ).call();
        }
    }
    
    private MobTalkerScript()
    {}
    
}
