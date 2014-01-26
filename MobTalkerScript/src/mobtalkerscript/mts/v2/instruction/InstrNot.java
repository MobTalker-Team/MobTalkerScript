package mobtalkerscript.mts.v2.instruction;

import static mobtalkerscript.mts.v2.value.MtsBoolean.*;
import static mobtalkerscript.mts.v2.value.MtsValue.*;

@SuppressWarnings( "unused" )
public class InstrNot extends MtsInstruction
{
    @Override
    public void execute( MtsFrame frame )
    {
        frame.push( isTrue( frame.pop() ) ? FALSE : TRUE );
    }
    
    @Override
    public String toString()
    {
        return "NOT";
    }
}
