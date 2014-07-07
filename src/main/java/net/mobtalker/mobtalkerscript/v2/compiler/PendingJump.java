package net.mobtalker.mobtalkerscript.v2.compiler;

import static com.google.common.base.Preconditions.*;
import net.mobtalker.mobtalkerscript.v2.instruction.*;

/**
 * Wrapper around a {@link MtsJumpInstruction} whose target instruction is not yet determined.
 */
public class PendingJump
{
    private final MtsJumpInstruction _instr;
    private final int _instrIndex;
    
    // ========================================
    
    /**
     * @param instr The instruction to wrap.
     * @param instrIndex The (absolute) index of <code>instr</code>.
     */
    public PendingJump( MtsJumpInstruction instr, int instrIndex )
    {
        checkNotNull( instr );
        checkArgument( instrIndex >= 0 );
        
        _instr = instr;
        _instrIndex = instrIndex;
    }
    
    // ========================================
    
    /**
     * Sets the absolute jump target.
     */
    public void setTarget( int targetIndex )
    {
        setDistance( targetIndex - _instrIndex );
    }
    
    /**
     * Sets the relative jump target.
     */
    public void setDistance( int distance )
    {
        _instr.setDistance( distance );
    }
}
