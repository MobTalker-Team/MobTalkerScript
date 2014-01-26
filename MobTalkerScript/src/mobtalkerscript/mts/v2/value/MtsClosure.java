package mobtalkerscript.mts.v2.value;

import static com.google.common.base.Preconditions.*;

import java.util.*;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.instruction.*;

public final class MtsClosure extends MtsFunction
{
    private final MislFunctionPrototype _prototype;
    private final List<External> _externals;
    
    // ========================================
    
    public MtsClosure( MislFunctionPrototype prototype, List<External> externals )
    {
        checkNotNull( prototype );
        checkNotNull( externals );
        
        _prototype = prototype;
        _externals = externals;
    }
    
    // ========================================
    
    public MislFunctionPrototype getPrototype()
    {
        return _prototype;
    }
    
    // ========================================
    
    @Override
    public MtsValue call( MtsValue... args )
    {
        return new MtsFrame( this, args, _externals ).run();
    }
    
    // ========================================
    
    @Override
    public final boolean isClosure()
    {
        return true;
    }
    
    @Override
    public final MtsClosure asClosure()
    {
        return this;
    }
    
    // ========================================
    
    @Override
    public int hashCode()
    {
        return _prototype.hashCode();
    }
    
    @Override
    public boolean equals( Object obj )
    {
        if ( obj == this )
            return true;
        if ( !( obj instanceof MtsClosure ) )
            return false;
        
        return ( (MtsClosure) obj ).asClosure().getPrototype().equals( _prototype );
    }
    
}
