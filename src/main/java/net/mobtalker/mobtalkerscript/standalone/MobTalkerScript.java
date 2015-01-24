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
package net.mobtalker.mobtalkerscript.standalone;

import static net.mobtalker.mobtalkerscript.v3.value.userdata.MtsNatives.*;

import java.nio.file.Paths;

import joptsimple.*;
import joptsimple.internal.Strings;
import net.mobtalker.mobtalkerscript.api.WorldPosition;
import net.mobtalker.mobtalkerscript.api.library.*;
import net.mobtalker.mobtalkerscript.standalone.lib.*;
import net.mobtalker.mobtalkerscript.util.PrettyPrinter;
import net.mobtalker.mobtalkerscript.v3.*;
import net.mobtalker.mobtalkerscript.v3.compiler.*;
import net.mobtalker.mobtalkerscript.v3.value.*;

/**
 * Intended to be the entry point for an interactive shell for MobTalkerScript.
 */
public class MobTalkerScript
{
    public static void main( String[] args ) throws Exception
    {
        System.out.print( "Loading...\r" );
        
        // Initialize the parser
        MtsCompiler.loadChunk( ";", "" );
        
        // Options
        OptionParser parser = new OptionParser();
        OptionSpec<String> files = parser.nonOptions();
        
        OptionSet options = parser.parse( args );
        
        // Initialize globals
        MtsGlobals _G = new MtsGlobals();
        createLibraries( _G );
        
        _G.out.println( "MobTalkerScript " //
                        + MtsGlobals.VERSION.toJava()
                        + " Copyright (c) 2013-2014 Chimaine" );
        _G.out.println( "This is free software licensed under the GNU Lesser General Public License version 3." );
        
        // Load specified file if any
        if ( !Strings.isNullOrEmpty( options.valueOf( files ) ) )
        {
            String path = options.valueOf( files );
            
            _G.out.println( "Loading file '" + path + "'" );
            
            _G.PackageLib.setBasePath( Paths.get( path ).toAbsolutePath().getParent().toString() );
            
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
                catch ( MtsRuntimeException ex )
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
                chunk = MtsCompiler.loadChunk( line, "stdin" );
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
                    _G.out.print( new PrettyPrinter().print( result, "result" ) );
                }
            }
            catch ( MtsRuntimeException ex )
            {
                _G.err.println( ex.createStackTrace() );
                Thread.sleep( 100 );
            }
        }
    }
    
    private static void createLibraries( MtsGlobals env )
    {
        DummyCreature dummyCreature = new DummyCreature( "DummyMob", 10, 10, new WorldPosition( 0, 0, 0 ) );
        DummyPlayer dummyPlayer = new DummyPlayer( "Player", new WorldPosition( 0, 0, 0 ) );
        
        {
            createLibrary( new InteractionCommandLib( new ConsoleInteractionCommandLibLogic( env ) ), env );
        }
        {
            MtsTable lib = new MtsTable( 0, 0 );
            createLibrary( new GameCommandLib( new ConsoleGameCommandLibLogic( env ) ), lib );
            env.set( "Command", lib );
        }
        {
            MtsTable lib = new MtsTable( 0, 0 );
            createLibrary( new EntityLib( new ConsoleEntityLibLogic( dummyCreature ) ), lib );
            createLibrary( new CreatureLib( new ConsoleCreatureLibLogic( dummyCreature ) ), lib );
            env.set( "Entity", lib );
        }
        {
            MtsTable lib = new MtsTable( 0, 0 );
            createLibrary( new EntityLib( new ConsoleEntityLibLogic( dummyPlayer ) ), lib );
            createLibrary( new PlayerLib( new ConsolePlayerLibLogic( dummyPlayer ) ), lib );
            env.set( "Player", lib );
        }
        {
            MtsTable lib = new MtsTable( 0, 0 );
            createLibrary( new InteractionWorldLib( new ConsoleInteractionWorldLibLogic() ), lib );
            env.set( "World", lib );
        }
        {
            MtsTable lib = new MtsTable( 0, 0 );
            createLibrary( new ScoreboardLib( new ConsoleScoreboardLibLogic() ), lib );
            env.set( "Scoreboard", lib );
        }
    }
    
    // ========================================
    
    private MobTalkerScript()
    {}
    
}
