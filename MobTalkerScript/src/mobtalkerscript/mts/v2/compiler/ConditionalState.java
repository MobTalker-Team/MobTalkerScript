package mobtalkerscript.mts.v2.compiler;

public class ConditionalState
{
    private PendingJump _pendingNext;
    private final PendingJumpCollection _pendingLeaves;
    
    public ConditionalState()
    {
        _pendingLeaves = new PendingJumpCollection();
    }
    
    public void setPendingNext( PendingJump jump )
    {
        if ( _pendingNext != null )
            throw new IllegalStateException();
        
        _pendingNext = jump;
    }
    
    public void markBeginNext( int target )
    {
        if ( _pendingNext == null )
            throw new IllegalStateException();
        
        _pendingNext.setTarget( target );
        _pendingNext = null;
    }
    
    public void addPendingExit( PendingJump jump )
    {
        _pendingLeaves.add( jump );
    }
    
    public void setExitTarget( int target )
    {
        _pendingLeaves.setTarget( target );
        
        if ( _pendingNext != null )
        {
            markBeginNext( target );
        }
    }
    
}
