package mobtalkerscript.mts.v2.instruction;

public abstract class MtsJumpInstruction extends MtsInstruction
{
    private int _target;
    
    // ========================================
    
    public MtsJumpInstruction()
    {
        this( -1 );
    }
    
    public MtsJumpInstruction( int target )
    {
        _target = target;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        frame.setInstructionPointer( _target - 1 );
    }
    
    // ========================================
    
    public int getTarget()
    {
        return _target;
    }
    
    public void setTarget( int target )
    {
        _target = target;
    }
    
}
