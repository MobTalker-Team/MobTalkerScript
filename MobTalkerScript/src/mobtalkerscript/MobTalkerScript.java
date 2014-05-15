package mobtalkerscript;

import java.nio.file.*;
import java.util.logging.*;

import joptsimple.*;
import joptsimple.internal.*;
import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.compiler.*;
import mobtalkerscript.mts.v2.lib.*;
import mobtalkerscript.mts.v2.lib.mobtalker.*;
import mobtalkerscript.mts.v2.value.*;
import mobtalkerscript.mts.v2.value.userdata.*;
import mobtalkerscript.util.logging.*;

/**
 * Intended to be the entry point for an interactive shell for MobTalkerScript.
 */
public class MobTalkerScript
{
    public static void main( String[] args ) throws Exception
    {
        MtsLog.setLogger( Logger.getLogger( "MTS" ), true );
        MtsCompiler.loadString( ";", "" );
        
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
        
        // Initialize engine
        MtsGlobals _G = new MtsGlobals();
        
        _G.loadLibrary( new MtsMathLib() );
        _G.loadLibrary( new MtsTableLib() );
        
//        _G.loadLibrary( new ConsoleCommandLib() );
        LibraryAdapter.bindTo( new AnnotatedConsoleCommandLib( _G ), _G );
        
        _G.loadLibrary( new MobTalkerConsoleInteractionLib( new DummyTalkingPlayer( "Console", 20 ),
                                                            new DummyTalkingEntity( "", "Creeper", 20, 0 ) ) );
        _G.loadLibrary( new MobTalkerConsoleCharacterLib() );
//        _G.loadLibrary( new MinecraftConsoleWorldLib() );
        
        _G.set( "World", LibraryAdapter.bind( new MinecraftConsoleWorldLib() ) );
        
        _G.out.println( "MobTalkerScript " //
                        + _G.get( "_VERSION" ).asString().asJavaString()
                        + " Copyright (c) 2013-2014 Tobias Rummelt, mobtalker.net" );
        _G.out.println( "CAUTION: This is an alpha version software and may contain bugs and incomplete features!" );
        _G.out.println( "         Please report any bugs you may encounter to the bug tracker." );
        
//        Thread.sleep( 5000 );
        
        // Load specified file if any
        if ( !Strings.isNullOrEmpty( options.valueOf( files ) ) )
        {
            String path = options.valueOf( files );
            
            _G.out.println( "Loading file '" + path + "'" );
            
            _G.loadLibrary( new MtsPackageLib( Paths.get( path ).getParent().toString() ) );
            
            MtsFunctionPrototype fileChunk = null;
            try
            {
                fileChunk = MtsCompiler.loadFile( path );
            }
            catch ( MtsSyntaxError ex )
            {
                _G.err.println( ex.getMessage() );
            }
            catch ( Exception ex )
            {
                ex.printStackTrace();
            }
            Thread.sleep( 1 );
            
            if ( fileChunk != null )
            {
                new MtsClosure( fileChunk, _G ).call();
            }
        }
        else
        {
            _G.loadLibrary( new MtsPackageLib() );
        }
        
        // Interactive loop
        for ( ;; )
        {
            Thread.sleep( 100 );
            
            _G.out.print( "> " );
            String line = _G.in.readLine();
            
            if ( line.startsWith( "exit" ) )
                break;
            
            MtsFunctionPrototype chunk;
            try
            {
                chunk = MtsCompiler.loadString( line, "stdin" );
            }
            catch ( Exception ex )
            {
                ex.printStackTrace( _G.err );
                continue;
            }
            
            try
            {
                new MtsClosure( chunk, _G ).call();
            }
            catch ( ScriptRuntimeException ex )
            {
                _G.err.println( ex.createStackTrace() );
            }
        }
    }
    
    private MobTalkerScript()
    {}
    
}
