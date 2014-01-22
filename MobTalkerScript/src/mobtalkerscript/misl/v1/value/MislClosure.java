package mobtalkerscript.misl.v1.value;

import static com.google.common.base.Preconditions.*;

import java.util.*;

import mobtalkerscript.misl.v1.*;

public final class MislClosure extends MislFunction
{
    private final MislFunctionPrototype _prototype;
    private final List<External> _externals;
    
    // ========================================
    
    public MislClosure( MislFunctionPrototype prototype, List<External> externals )
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
    public MislValue call( MislValue... args )
    {
        MislFrame frame = new MislFrame( _prototype.getInstructions(),
                                         _prototype.getMaxStackSize(),
                                         _prototype.getLocalCount(),
                                         args,
                                         _prototype.getConstants(),
                                         _externals );
        
        return frame.run();
    }
    
    // ========================================
    
    @Override
    public final boolean isClosure()
    {
        return true;
    }
    
    @Override
    public final MislClosure asClosure()
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
        if ( !( obj instanceof MislClosure ) )
            return false;
        
        return ( (MislClosure) obj ).asClosure().getPrototype().equals( _prototype );
    }
    
}
