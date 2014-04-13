package mobtalkerscript.mts.v2.value;

import static com.google.common.base.Preconditions.*;

import java.util.*;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.compiler.*;
import mobtalkerscript.mts.v2.instruction.*;

public final class MtsClosure extends MtsFunction
{
    private final MtsFunctionPrototype _prototype;
    private final List<FrameValue> _externals;
    
    // ========================================
    
    public MtsClosure( MtsFunctionPrototype prototype, MtsTable env )
    {
        this( prototype, Collections.singletonList( new FrameValue( env ) ) );
    }
    
    public MtsClosure( MtsFunctionPrototype prototype, List<FrameValue> externals )
    {
        checkNotNull( prototype );
        checkNotNull( externals );
        
        _prototype = prototype;
        _externals = externals;
    }
    
    // ========================================
    
    public MtsFunctionPrototype getPrototype()
    {
        return _prototype;
    }
    
    // ========================================
    
    @Override
    public MtsValue call( MtsVarArgs args )
    {
        MtsFrame frame = new MtsFrame( this, args, _externals );
        try
        {
            return frame.run();
        }
        catch ( ScriptRuntimeException ex )
        {
            String source = _prototype.getSourceFile();
            SourcePosition pos = _prototype.getSourcePosition( frame.getInstructionPointer() );
            String name = _prototype.getName();
            
            ex.addStackTraceElement( new MtsStackTraceElement( source, pos, name ) );
            
            throw ex;
        }
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
