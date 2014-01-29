package mobtalkerscript.mts.v2.instruction;

import java.util.*;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;

import com.google.common.collect.*;

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
        List<External> externals = createExternals( frame, prototype );
        MtsClosure closure = new MtsClosure( prototype, externals );
        
        frame.push( closure );
    }
    
    private List<External> createExternals( MtsFrame frame, MtsFunctionPrototype prototype )
    {
        List<ExternalDescription> descriptions = prototype.getExternals();
        ArrayList<External> externals = Lists.newArrayListWithCapacity( descriptions.size() );
        
        for ( ExternalDescription descr : descriptions )
        {
            externals.add( descr.isLocal()
                    ? new External( descr.getIndex(), frame )
                    : frame.getExternal( descr.getIndex() ) );
        }
        
        return externals;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "CLOSURE " + _index;
    }
}
