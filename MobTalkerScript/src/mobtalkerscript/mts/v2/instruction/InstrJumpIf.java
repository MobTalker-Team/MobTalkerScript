package mobtalkerscript.mts.v2.instruction;

import static mobtalkerscript.mts.v2.value.MtsBoolean.*;

public final class InstrJumpIf extends MtsInstruction
{
    private final int _offset;
    
    // ========================================
    
    public InstrJumpIf( int offset )
    {
        _offset = offset;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        if ( isTrue( frame.pop() ) )
        {
            frame.setInstructionPointer( _offset );
        }
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "JUMPIF " + _offset;
    }
}
