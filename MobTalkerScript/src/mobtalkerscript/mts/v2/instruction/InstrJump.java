package mobtalkerscript.mts.v2.instruction;

public class InstrJump extends MtsJumpInstruction
{
    
    public InstrJump()
    {
        super();
    }
    
    public InstrJump( int target )
    {
        super( target );
    }
    
    // ========================================
    
    @Override
    public int stackSizeChange()
    {
        return 0;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "JUMP " + getTarget();
    }
    
}
