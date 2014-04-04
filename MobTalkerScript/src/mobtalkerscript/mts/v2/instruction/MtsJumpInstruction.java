package mobtalkerscript.mts.v2.instruction;

import static com.google.common.base.Preconditions.*;

public abstract class MtsJumpInstruction extends MtsInstruction
{
    private int _distance;
    
    // ========================================
    
    public MtsJumpInstruction()
    {
        this( 0 );
    }
    
    public MtsJumpInstruction( int distance )
    {
        _distance = distance;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        frame.setInstructionPointer( ( frame.getInstructionPointer() + getDistance() ) - 1 );
    }
    
    // ========================================
    
    public int getDistance()
    {
        return _distance;
    }
    
    public void setDistance( int distance )
    {
        checkArgument( distance != 0, "target can not be self (infinite loop)" );
        
        _distance = distance;
    }
    
    public void setTarget( int sourceIndex, int targetIndex )
    {
        setDistance( targetIndex - sourceIndex );
    }
    
}
