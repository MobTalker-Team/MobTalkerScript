package mobtalkerscript.misl.v1.instructionV2;

import java.util.*;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;

import com.google.common.collect.*;

public class InstrClosure extends MislIndexedInstruction
{
    public InstrClosure( int prototype )
    {
        super( prototype );
    }
    
    // ========================================
    
    @Override
    public void execute( MislFrame frame )
    {
        MislFunctionPrototype prototype = frame.getClosure().getPrototype().getNestedPrototype( _index );
        List<External> externals = createExternals( frame, prototype );
        MislClosure closure = new MislClosure( prototype, externals );
        
        frame.push( closure );
    }
    
    private List<External> createExternals( MislFrame frame, MislFunctionPrototype prototype )
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
    
}
