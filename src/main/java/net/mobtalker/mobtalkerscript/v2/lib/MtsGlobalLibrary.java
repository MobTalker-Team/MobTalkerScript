package net.mobtalker.mobtalkerscript.v2.lib;

import net.mobtalker.mobtalkerscript.v2.*;
import net.mobtalker.mobtalkerscript.v2.value.*;

public abstract class MtsGlobalLibrary extends MtsLibrary
{
    public static MtsGlobals checkGlobals( MtsValue env )
    {
        if ( env instanceof MtsGlobals )
            return (MtsGlobals) env;
        
        throw new ScriptEngineException( "env is not an instance of MtsGlobals!" );
    }
    
    // ========================================
    
    protected MtsGlobals _G;
    
    // ========================================
    
    public MtsGlobals getGlobals()
    {
        return _G;
    }
    
    // ========================================
    
    @Override
    public final MtsValue bind( MtsString name, MtsValue env )
    {
        _G = checkGlobals( env );
        bind();
        
        return null;
    }
    
    protected abstract void bind();
    
    // ========================================
    
    protected void bindFunction( String name, MtsJavaFunction func )
    {
        _G.set( name, func, false );
    }
    
}
