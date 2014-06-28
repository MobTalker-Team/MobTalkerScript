package mobtalkerscript.v2.compiler;

import java.util.*;

import com.google.common.collect.*;

public class PendingJumpCollection
{
    private final ArrayList<PendingJump> _jumps;
    
    // ========================================
    
    public PendingJumpCollection()
    {
        _jumps = Lists.newArrayListWithExpectedSize( 4 );
    }
    
    // ========================================
    
    public void add( PendingJump jump )
    {
        _jumps.add( jump );
    }
    
    public void setTarget( int targetIndex )
    {
        for ( PendingJump jump : _jumps )
        {
            jump.setTarget( targetIndex );
        }
    }
    
    public void setDistance( int distance )
    {
        for ( PendingJump jump : _jumps )
        {
            jump.setDistance( distance );
        }
    }
}
