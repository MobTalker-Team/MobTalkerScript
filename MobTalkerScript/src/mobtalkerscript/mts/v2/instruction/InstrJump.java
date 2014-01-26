package mobtalkerscript.mts.v2.instruction;

public final class InstrJump extends MtsInstruction
{
    private final int _offset;
    
    // ========================================
    
    public InstrJump( int offset )
    {
        _offset = offset;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        frame.setInstructionPointer( _offset );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "JUMP " + _offset;
    }
}
