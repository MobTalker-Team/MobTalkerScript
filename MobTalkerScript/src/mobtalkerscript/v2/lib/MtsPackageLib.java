package mobtalkerscript.v2.lib;

import static mobtalkerscript.util.MtsCheck.*;

import java.nio.file.*;
import java.util.*;

import mobtalkerscript.v2.*;
import mobtalkerscript.v2.compiler.*;
import mobtalkerscript.v2.value.*;
import mobtalkerscript.v2.value.userdata.*;

import com.google.common.collect.*;

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
        _searchPaths = Lists.newArrayList( "/?.mts",
                                           "/?.script",
                                           "/lib/?.mts",
                                           "/lib/?.script" );
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
    
    @MtsNativeFunction( name = "require" )
    public MtsValue Require( MtsValue arg )
    {
        String libName = checkString( arg, 0 );
        MtsValue lib = _loadedPackages.get( libName );
        
        if ( !lib.isNil() )
            return lib;
        
        for ( String pathPattern : _searchPaths )
        {
            pathPattern = _basePath + pathPattern;
            Path path = Paths.get( pathPattern.replace( "?", libName ) );
            
            _G.out.println( "Searching path '"
                            + path.toString()
                            + " for module '"
                            + libName
                            + "'" );
            
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
