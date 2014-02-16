package mobtalkerscript.mts.v2.compiler;

import java.util.*;

import mobtalkerscript.mts.v2.instruction.*;

import com.google.common.collect.*;

public class LoopBlock
{
    private final int _firstInstrIndex;
    private final List<MtsJumpInstruction> _breaks;
    
    private MtsJumpInstruction _pendingLeave;
    
    public LoopBlock( int firstInstrIndex )
    {
        _firstInstrIndex = firstInstrIndex;
        _breaks = Lists.newArrayList();
    }
    
    public void addBreak( MtsInstruction instr )
    {
        _breaks.add( (MtsJumpInstruction) instr );
    }
    
    public void setBreakTarget( int target )
    {
        for ( MtsJumpInstruction instr : _breaks )
        {
            instr.setTarget( target );
        }
    }
    
    public int getFirstInstructionIndex()
    {
        return _firstInstrIndex;
    }
    
    public void addPendingLeave( MtsInstruction instr )
    {
        if ( _pendingLeave != null )
            throw new IllegalStateException();
        
        _pendingLeave = (MtsJumpInstruction) instr;
    }
    
    public void setLeaveTarget( int target )
    {
        if ( _pendingLeave == null )
            throw new IllegalStateException();
        
        _pendingLeave.setTarget( target );
    }
    
}
