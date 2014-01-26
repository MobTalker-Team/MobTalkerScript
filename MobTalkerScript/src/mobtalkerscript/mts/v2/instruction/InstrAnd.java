package mobtalkerscript.mts.v2.instruction;

import static mobtalkerscript.mts.v2.value.MtsBoolean.*;

public class InstrAnd extends MtsInstruction
{
    @Override
    public void execute( MtsFrame frame )
    {
        if ( isTrue( frame.peek() ) )
        {
            frame.pop();
        }
        else
        {
            frame.setInstructionPointer( 1 );
        }
    }
    
    @Override
    public String toString()
    {
        return "AND";
    }
}
