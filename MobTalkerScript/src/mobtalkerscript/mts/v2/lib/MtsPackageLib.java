package mobtalkerscript.mts.v2.lib;

import java.util.*;

import mobtalkerscript.mts.v2.value.*;

import com.google.common.collect.*;

public class MtsPackageLib extends MtsLibrary
{
    private final Map<String, MtsValue> _loadedPackages;
    
    // ========================================
    
    {
        _loadedPackages = Maps.newHashMap();
    }
    
    // ========================================
    
    @Override
    public MtsValue bind( MtsString name, MtsValue env )
    {
        checkIfGlobals( env );
        
        bindFunction( env, "require", new Require() );
        
        return null;
    }
    
    // ========================================
    
    private static final class Require extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg )
        {
            return null;
        }
    }
    
}
