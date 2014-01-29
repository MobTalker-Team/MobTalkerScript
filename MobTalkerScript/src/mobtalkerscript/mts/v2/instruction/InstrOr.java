package mobtalkerscript.mts.v2.instruction;

import static mobtalkerscript.mts.v2.value.MtsBoolean.*;

public final class InstrOr extends MtsJumpInstruction
{
    public InstrOr( int offset )
    {
        super( offset );
    }
    
    @Override
    public void execute( MtsFrame frame )
    {
        if ( !isTrue( frame.peek() ) )
        {
            frame.pop();
            frame.setInstructionPointer( _offset );
        }
    }
    
    @Override
    public String toString()
    {
        return "OR " + _offset;
    }
}
