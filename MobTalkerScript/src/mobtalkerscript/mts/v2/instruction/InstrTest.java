package mobtalkerscript.mts.v2.instruction;

import static mobtalkerscript.mts.v2.value.MtsBoolean.*;

public final class InstrTest extends MtsJumpInstruction
{
    public InstrTest( int offset )
    {
        super( offset );
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        if ( !isTrue( frame.pop() ) )
        {
            frame.setInstructionPointer( _offset );
        }
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "TEST " + _offset;
    }
}
