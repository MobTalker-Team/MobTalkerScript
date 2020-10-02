/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.lib;

import static net.mobtalker.mobtalkerscript.v3.MtsCheck.*;

import java.nio.file.*;
import java.util.Arrays;
import java.util.List;

import net.mobtalker.mobtalkerscript.v3.*;
import net.mobtalker.mobtalkerscript.v3.compiler.*;
import net.mobtalker.mobtalkerscript.v3.value.*;
import net.mobtalker.mobtalkerscript.v3.value.userdata.MtsNativeFunction;

public class MtsPackageLib
{
    private final MtsGlobals _G;
    private String _basePath;
    private final List<String> _searchPaths;
    private final MtsTable _loadedPackages;
    
    // ========================================
    
    public MtsPackageLib( MtsGlobals g )
    {
        this( g, "." );
    }
    
    public MtsPackageLib( MtsGlobals g, String basePath )
    {
        _G = g;
        _loadedPackages = new MtsTable( 0, 1 );
        _basePath = basePath;
        _searchPaths = Arrays.asList("/?", "/lib/?");
    }
    
    // ========================================
    
    public String getBasePath()
    {
        return _basePath;
    }
    
    public void setBasePath( String basePath )
    {
        _basePath = basePath;
    }
    
    /**
     * Adds a module to the list of known modules of this library.
     */
    public void addModule( String name, MtsTable module )
    {
        _loadedPackages.set( name, module );
    }
    
    // ========================================
    
    @MtsNativeFunction( "require" )
    public MtsValue require( MtsValue arg )
    {
        String libName = checkString( arg, 0 );
        MtsValue lib = _loadedPackages.get( libName );
        
        if ( !lib.isNil() )
            return lib;
        
        for ( String pathPattern : _searchPaths )
        {
            pathPattern = _basePath + pathPattern;
            Path path = Paths.get( pathPattern.replace( "?", libName ) );
            
            _G.out.println( "Searching path '" + path.toString() + " for module '" + libName + "'" );
            
            if ( Files.exists( path ) )
            {
                MtsFunctionPrototype p;
                try
                {
                    p = MtsCompiler.loadFile( path );
                }
                catch ( MtsSyntaxError ex )
                {
                    throw ex;
                }
                catch ( Exception ex )
                {
                    throw new MtsEngineException( ex );
                }
                
                lib = new MtsClosure( p, _G ).call();
                _loadedPackages.set( libName, lib );
                
                return lib;
            }
        }
        
        throw new MtsRuntimeException( "module '%s' not found", libName );
    }
}
