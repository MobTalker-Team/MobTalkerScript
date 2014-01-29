package mobtalkerscript.mts.v2.instruction;

public abstract class MtsJumpInstruction extends MtsInstruction
{
    protected final int _offset;
    
    // ========================================
    
    public MtsJumpInstruction( int offset )
    {
        _offset = offset;
    }
    
}
