package mobtalkerscript.mts.v2.instruction;

import java.util.*;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;

public class InstrClosure extends MtsIndexedInstruction
{
    public InstrClosure( int prototype )
    {
        super( prototype );
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsFunctionPrototype prototype = frame.getClosure().getPrototype().getNestedPrototype( _index );
        FrameValue[] externals = createExternals( frame, prototype );
        MtsClosure closure = new MtsClosure( prototype, externals );
        
        frame.push( closure );
    }
    
    private FrameValue[] createExternals( MtsFrame frame, MtsFunctionPrototype prototype )
    {
        List<ExternalDescription> descriptions = prototype.getExternals();
        FrameValue[] externals = new FrameValue[descriptions.size()];
        
        for ( int i = 0; i < externals.length; i++ )
        {
            ExternalDescription descr = descriptions.get( i );
            
            if ( descr.isParentLocal() )
                externals[i] = frame.getLocal( descr.getParentIndex() );
            else
                externals[i] = frame.getExternal( descr.getParentIndex() );
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
