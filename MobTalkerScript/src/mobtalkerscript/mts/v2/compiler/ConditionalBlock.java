package mobtalkerscript.mts.v2.compiler;

import java.util.*;

import mobtalkerscript.mts.v2.instruction.*;

import com.google.common.collect.*;

public class ConditionalBlock
{
    private MtsJumpInstruction _pendingJumpNext;
    private final List<MtsJumpInstruction> _pendingLeaves;
    
    public ConditionalBlock()
    {
        _pendingLeaves = Lists.newArrayList();
    }
    
    public void addPendingNext( MtsInstruction instr )
    {
        if ( _pendingJumpNext != null )
            throw new IllegalStateException();
        
        _pendingJumpNext = (MtsJumpInstruction) instr;
    }
    
    public void markBeginNext( int ip )
    {
        if ( _pendingJumpNext == null )
            throw new IllegalStateException();
        
        _pendingJumpNext.setTarget( ip );
        _pendingJumpNext = null;
    }
    
    public void addPendingLeave( MtsInstruction instr )
    {
        _pendingLeaves.add( (MtsJumpInstruction) instr );
    }
    
    public void setLeaveTarget( int ip )
    {
        for ( MtsJumpInstruction instr : _pendingLeaves )
        {
            instr.setTarget( ip );
        }
    }
    
}
