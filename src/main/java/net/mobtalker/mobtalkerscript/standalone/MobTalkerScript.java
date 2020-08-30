/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.standalone;

import java.nio.file.Paths;

import joptsimple.*;
import joptsimple.internal.Strings;
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
    
    // ========================================
    
    private MobTalkerScript()
    {}
    
}
