package mobtalkerscript.mts.v2.instruction;

import static mobtalkerscript.mts.v2.value.MtsValue.*;
import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;

public class InstrSize extends MtsInstruction
{
    @Override
    public void execute( MtsFrame frame )
    {
        MtsValue x = frame.pop();
        if ( x.isTable() )
        {
            frame.push( valueOf( x.asTable().listSize() ) );
        }
        else if ( x.isString() )
        {
            frame.push( x.asString().getLength() );
        }
        else
        {
            throw new ScriptRuntimeException( "attempt to get length of a %s value", x.getType().getName() );
        }
    }
    
    @Override
    public int stackSizeChange()
    {
        return 0;
    }
    
    @Override
    public String toString()
    {
        return "SIZE";
    }
}
