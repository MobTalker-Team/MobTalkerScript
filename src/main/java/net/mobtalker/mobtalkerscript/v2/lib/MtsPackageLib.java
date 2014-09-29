/*
 * Copyright (C) 2013-2014 Chimaine
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
package net.mobtalker.mobtalkerscript.v2.lib;

import static net.mobtalker.mobtalkerscript.v2.MtsCheck.*;

import java.nio.file.*;
import java.util.List;

import net.mobtalker.mobtalkerscript.v2.*;
import net.mobtalker.mobtalkerscript.v2.compiler.*;
import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

import com.google.common.collect.Lists;

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
        _searchPaths = Lists.newArrayList( "/?", "/lib/?" );
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
                    throw new ScriptEngineException( ex );
                }
                
                lib = new MtsClosure( p, _G ).call();
                _loadedPackages.set( libName, lib );
                
                return lib;
            }
        }
        
        throw new ScriptRuntimeException( "module '%s' not found", libName );
    }
}
