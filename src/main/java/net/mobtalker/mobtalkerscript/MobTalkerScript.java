package mobtalkerscript;

import static mobtalkerscript.v2.value.userdata.MtsNatives.*;

import java.nio.file.*;
import java.util.logging.*;

import joptsimple.*;
import joptsimple.internal.*;
import mobtalkerscript.util.logging.*;
import mobtalkerscript.v2.*;
import mobtalkerscript.v2.compiler.*;
import mobtalkerscript.v2.lib.*;
import mobtalkerscript.v2.lib.console.*;
import mobtalkerscript.v2.lib.console.mobtalker.*;
import mobtalkerscript.v2.value.*;

/**
 * Intended to be the entry point for an interactive shell for MobTalkerScript.
 */
public class MobTalkerScript
{
    public static void main( String[] args ) throws Exception
    {
        System.out.print( "Loading...\r" );
        
        MtsLog.setLogger( Logger.getLogger( "MTS" ), true );
        
        // Initialize the parser
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
        
        // Initialize globals
        MtsGlobals _G = new MtsGlobals();
        
        // Initialize Minecraft/MobTalker libraries
        _G.set( "World", createLibrary( new MinecraftConsoleWorldLib() ) );
        createLibrary( new ConsoleCommandLib( _G ), _G );
        createLibrary( new MobTalkerConsoleInteractionLib( _G,
                                                           new DummyTalkingPlayer( "Console", 20 ),
                                                           new DummyTalkingEntity( "", "Creeper", 20, 0 ) ),
                       _G );
        
        _G.out.println( "MobTalkerScript " //
                        + MtsGlobals.VERSION.asString().asJavaString()
                        + " Copyright (c) 2013-2014 Tobias Rummelt, mobtalker.net" );
        _G.out.println( "Please report any problems or bugs you may encounter to the bug tracker." );
        
        // Load specified file if any
        if ( !Strings.isNullOrEmpty( options.valueOf( files ) ) )
        {
            String path = options.valueOf( files );
            
            _G.out.println( "Loading file '" + path + "'" );
            
            _G.PackageLib.setBasePath( Paths.get( path ).getParent().toString() );
            
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
            
            if ( fileChunk != null )
            {
                try
                {
                    new MtsClosure( fileChunk, _G ).call();
                    Thread.sleep( 100 );
                }
                catch ( ScriptRuntimeException ex )
                {
                    _G.err.println( ex.createStackTrace() );
                    Thread.sleep( 100 );
                }
            }
        }
        
        // Interactive loop
        for ( ;; )
        {
            _G.out.print( "> " );
            String line = _G.in.readLine();
            
            if ( line.trim().equals( "exit" ) )
                break;
            
            MtsFunctionPrototype chunk;
            try
            {
                chunk = MtsCompiler.loadString( line, "stdin" );
            }
            catch ( MtsSyntaxError ex )
            {
                _G.err.println( ex.getMessage() );
                Thread.sleep( 100 );
                continue;
            }
            
            try
            {
                MtsValue result = new MtsClosure( chunk, _G ).call();
                Thread.sleep( 100 );
                
                if ( ( result.isVarArgs() && ( result.asVarArgs().count() > 0 ) )
                     || ( !result.isNil() && !result.isVarArgs() ) )
                {
                    _G.out.println( result );
                }
            }
            catch ( ScriptRuntimeException ex )
            {
                _G.err.println( ex.createStackTrace() );
                Thread.sleep( 100 );
            }
        }
    }
    
    private MobTalkerScript()
    {}
    
}
