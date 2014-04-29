package mobtalkerscript.mts.v2.lib;

import java.nio.file.*;
import java.util.*;

import mobtalkerscript.mts.v2.value.*;

import com.google.common.collect.*;

public class MtsPackageLib extends MtsLibrary
{
    private final List<String> _searchPaths = Lists.newArrayList( "./?.lua", "./?" );
    private final MtsTable _loadedPackages;
    
    // ========================================
    
    public MtsPackageLib()
    {
        _loadedPackages = new MtsTable( 0, 1 );
    }
    
    // ========================================
    
    @Override
    public MtsValue bind( MtsString name, MtsValue env )
    {
        checkIfGlobals( env );
        
        bindFunction( env, "require", new Require() );
//        env.set( "_LOADED", _loadedPackages );
        
//        MtsValue defaultPath = env.get( "MTS_PATH" );
//        if ( defaultPath.isString() )
//        {
//            _searchPaths.add( 0, defaultPath.asString().toJava() );
//        }
        
        return null;
    }
    
    // ========================================
    
    private final class Require extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg )
        {
            checkString( arg, 1 );
            
            String libName = arg.asString().toJava();
            MtsValue lib = _loadedPackages.get( libName );
            
            if ( lib != null )
                return lib;
            
            for ( String pathPattern : _searchPaths )
            {
                Path path = Paths.get( pathPattern.replace( "?", libName ) );
                if ( Files.exists( path ) )
                {
                    // TODO Compile it
                }
            }
            
            return NIL;
        }
    }
}
