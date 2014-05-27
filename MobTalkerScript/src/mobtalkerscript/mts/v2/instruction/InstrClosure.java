package mobtalkerscript.mts.v2.instruction;

import java.util.*;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;

import com.google.common.collect.*;

public class InstrClosure extends MtsIndexedInstruction
{
    /* package */InstrClosure( int prototype )
    {
        super( prototype );
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsFunctionPrototype prototype = frame.getClosure().getPrototype().getNestedPrototype( _index );
        List<FrameValue> externals = createExternals( frame, prototype );
        MtsClosure closure = new MtsClosure( prototype, externals );
        
        frame.push( closure );
    }
    
    private List<FrameValue> createExternals( MtsFrame frame, MtsFunctionPrototype prototype )
    {
        int nExternals = prototype.getExternalCount();
        List<FrameValue> externals = Lists.newArrayListWithCapacity( nExternals );
        
        for ( int i = 0; i < nExternals; i++ )
        {
            ExternalDescription descr = prototype.getExternalDescription( i );
            
            if ( descr.isParentLocal() )
                externals.add( frame.getLocal( descr.getParentIndex() ) );
            else
                externals.add( frame.getExternal( descr.getParentIndex() ) );
        }
        
        return externals;
    }
    
    @Override
    public int stackSizeChange()
    {
        return 1;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "CLOSURE " + _index;
    }
}
