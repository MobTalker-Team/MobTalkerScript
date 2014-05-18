package mobtalkerscript;

import static mobtalkerscript.mts.v2.value.MtsValue.*;
import static mobtalkerscript.mts.v2.value.userdata.MtsNatives.*;

import java.nio.file.*;
import java.util.logging.*;

import joptsimple.*;
import joptsimple.internal.*;
import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.compiler.*;
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
        
        _G.set( "Math", createLibrary( MtsMathLib.class ) );
        _G.set( "Table", createLibrary( MtsTableLib.class ) );
        
        MtsTable stringLib = createLibrary( MtsStringLib.class );
        MtsTable stringMT = new MtsTable( 0, 1 );
        stringMT.set( __INDEX, stringLib );
        MtsType.STRING.setMetaTable( stringMT );
        
//        _G.loadLibrary( new ConsoleCommandLib() );
        createLibrary( new ConsoleCommandLib( _G ), _G );
        
        _G.loadLibrary( new MobTalkerConsoleInteractionLib( new DummyTalkingPlayer( "Console", 20 ),
                                                            new DummyTalkingEntity( "", "Creeper", 20, 0 ) ) );
        
        _G.set( "World", createLibrary( new MinecraftConsoleWorldLib() ) );
        
        _G.out.println( "MobTalkerScript " //
                        + _G.get( "_VERSION" ).asString().asJavaString()
                        + " Copyright (c) 2013-2014 Tobias Rummelt, mobtalker.net" );
        _G.out.println( "CAUTION: This is an alpha version software and may contain bugs and incomplete features!" );
        _G.out.println( "         Please report any bugs you may encounter to the bug tracker." );
        
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
            _G.out.print( "> " );
            String line = _G.in.readLine();
            
            if ( line.trim().equals( "exit" ) )
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
            }
        }
    }
    
    private MobTalkerScript()
    {}
    
}
