package mobtalkerscript.mts.v2.instruction;

public class InstrJump extends MtsJumpInstruction
{
    
    public InstrJump()
    {
        super();
    }
    
    public InstrJump( int distance )
    {
        super( distance );
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
        return "JUMP " + getDistance();
    }
    
}
