package mobtalkerscript.mts.v2.instruction;

public abstract class MtsJumpInstruction extends MtsInstruction
{
    protected int _offset;
    
    // ========================================
    
    public MtsJumpInstruction()
    {
        this( 0 );
    }
    
    public MtsJumpInstruction( int offset )
    {
        _offset = offset;
    }
    
    // ========================================
    
    public void setOffset( int offset )
    {
        _offset = offset;
    }
    
}
