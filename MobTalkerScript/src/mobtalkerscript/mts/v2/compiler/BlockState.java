package mobtalkerscript.mts.v2.compiler;

import static com.google.common.base.Preconditions.*;

public class BlockState
{
    private final BlockState _parent;
    
    protected BlockState( BlockState parent )
    {
        checkNotNull( parent );
        
        _parent = parent;
    }
    
    @SuppressWarnings( "unchecked" )
    public <T extends BlockState> T getParent()
    {
        return (T) _parent;
    }
    
}
