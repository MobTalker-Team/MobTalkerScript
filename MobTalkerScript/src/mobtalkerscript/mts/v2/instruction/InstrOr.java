package mobtalkerscript.mts.v2.instruction;

import static mobtalkerscript.mts.v2.value.MtsBoolean.*;

public class InstrOr extends MtsInstruction
{
    @Override
    public void execute( MtsFrame frame )
    {
        if ( !isTrue( frame.peek() ) )
        {
            frame.pop();
            frame.setInstructionPointer( 1 );
        }
    }
    
    @Override
    public String toString()
    {
        return "OR";
    }
}
