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
package net.mobtalker.mobtalkerscript;

import static net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNatives.*;

import java.nio.file.Paths;
import java.util.logging.*;

import joptsimple.*;
import joptsimple.internal.Strings;
import net.mobtalker.mobtalkerscript.util.logging.MtsLog;
import net.mobtalker.mobtalkerscript.v2.*;
import net.mobtalker.mobtalkerscript.v2.compiler.*;
import net.mobtalker.mobtalkerscript.v2.lib.console.ConsoleCommandLib;
import net.mobtalker.mobtalkerscript.v2.lib.console.mobtalker.*;
import net.mobtalker.mobtalkerscript.v2.value.*;

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
        
        // Create libraries
        createLibrary( new ConsoleCommandLib( _G ), _G );
        _G.set( "Scoreboard", createLibrary( new ScoreboardLib() ) );
        _G.set( "Command", createLibrary( new MinecraftCommandLib() ) );
        _G.set( "Entity", createLibrary( new InteractionEntityLib() ) );
        _G.set( "Player", createLibrary( new InteractionPlayerLib() ) );
        _G.set( "World", createLibrary( new InteractionWorldLib() ) );
        
        _G.out.println( "MobTalkerScript " //
                        + MtsGlobals.VERSION.asString().asJavaString()
                        + " Copyright (c) 2013-2014 Chimaine" );
        _G.out.println( "This is free software licensed under the GNU General Public License version 3." );
        
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
            
            if ( ( line == null ) || line.equals( "exit" ) )
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
