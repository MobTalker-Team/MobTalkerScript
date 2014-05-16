package mobtalkerscript.mts.v2.lib;

import static mobtalkerscript.util.MtsCheck.*;

import java.nio.file.*;
import java.util.*;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.compiler.*;
import mobtalkerscript.mts.v2.value.*;

import com.google.common.collect.*;

public class MtsPackageLib extends MtsGlobalLibrary
{
    private final List<String> _searchPaths;
    private final MtsTable _loadedPackages;
    
    // ========================================
    
    public MtsPackageLib()
    {
        this( "." );
    }
    
    public MtsPackageLib( String basePath )
    {
        _loadedPackages = new MtsTable( 0, 1 );
        _searchPaths = Lists.newArrayList( basePath + "/?.mts",
                                           basePath + "/?.script",
                                           basePath + "/lib/?.mts",
                                           basePath + "/lib/?.script" );
    }
    
    // ========================================
    
    /**
     * Adds a module to the list of known modules of this library.
     */
    public void addModule( String name, MtsTable module )
    {
        _loadedPackages.set( name, module );
    }
    
    // ========================================
    
    @Override
    public void bind()
    {
        bindFunction( _G, "require", new Require() );
    }
    
    // ========================================
    
    private final class Require extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg )
        {
            String libName = checkString( arg, 0 );
            MtsValue lib = _loadedPackages.get( libName );
            
            if ( !lib.isNil() )
                return lib;
            
            for ( String pathPattern : _searchPaths )
            {
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
}
