package net.mobtalker.mobtalkerscript.v2.instruction;

import static com.google.common.base.Preconditions.*;

public abstract class MtsJumpInstruction extends MtsInstruction
{
    private int _distance;
    
    // ========================================
    
    protected MtsJumpInstruction()
    {
        this( 0 );
    }
    
    protected MtsJumpInstruction( int distance )
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
    
    public MtsJumpInstruction setDistance( int distance )
    {
        checkArgument( distance != 0, "target can not be self (infinite loop)" );
        
        _distance = distance;
        return this;
    }
    
    public MtsJumpInstruction setTarget( int sourceIndex, int targetIndex )
    {
        return setDistance( targetIndex - sourceIndex );
    }
    
}
