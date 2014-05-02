package mobtalkerscript;

import java.util.logging.*;

import joptsimple.*;
import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.compiler.*;
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
        
        // Options
        OptionParser parser = new OptionParser();
        OptionSpec<String> compilerLogLevel = parser.accepts( "compilerLog" )
                                                    .withRequiredArg()
                                                    .defaultsTo( "OFF" );
        OptionSpec<String> engineLogLevel = parser.accepts( "engineLog" )
                                                  .withRequiredArg()
                                                  .defaultsTo( "OFF" );
        OptionSpec<String> files = parser.nonOptions();
        
        OptionSet options = parser.parse( args );
        
        MtsLog.CompilerLog.setLevel( Level.parse( options.valueOf( compilerLogLevel ) ) );
        MtsLog.EngineLog.setLevel( Level.parse( options.valueOf( engineLogLevel ) ) );
        
        // Preload some classes
        Instructions.class.getClass();
        
        // Initialize engine
        MtsGlobals _G = new MtsGlobals();
        _G.loadLibrary( new MtsMathLib() );
        _G.loadLibrary( new MtsTableLib() );
        
        _G.loadLibrary( new ConsoleCommandLib() );
        _G.loadLibrary( new MobTalkerConsoleBaseLib( "Console", 0 ) );
        _G.loadLibrary( new MobTalkerConsoleCharacterLib() );
        
        _G.out.println( "MobTalkerScript " //
                        + _G.get( "_VERSION" ).asString().toJava()
                        + " Copyright (c) 2013-2014 Tobias Rummelt, mobtalker.net" );
        _G.out.println( "CAUTION: This is an alpha version software and may contain serious bugs and incomplete features!" );
        _G.out.println( "         Please report any bugs you may encount." );
        
        // Load specified file if any
        if ( options.has( files ) )
        {
            String path = options.valueOf( files );
            
            _G.out.println( "Loading file '" + path + "'" );
            
            MtsFunctionPrototype fileChunk = null;
            try
            {
                fileChunk = _G.loadFile( path );
            }
            catch ( MtsSyntaxError ex )
            {
                _G.err.println( ex.getMessage() );
            }
            catch ( Exception ex )
            {
                ex.printStackTrace();
            }
            
            // Give the err stream time to print
            Thread.sleep( 1 );
            
            if ( fileChunk != null )
            {
                new MtsClosure( fileChunk, _G ).call();
            }
        }
        
        // Interactive loop
        for ( ;; )
        {
            _G.out.print( "> " );
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
