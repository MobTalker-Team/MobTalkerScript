package mobtalkerscript.mts.v2.instruction;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;

public abstract class MtsArithmeticInstruction extends MtsInstruction
{
    @Override
    public final void execute( MtsFrame frame )
    {
        MtsNumber b = coerce( frame.pop() );
        frame.push( calculate( coerce( frame.pop() ), b ) );
    }
    
    public static MtsNumber coerce( MtsValue x )
    {
        try
        {
            return x.asNumber();
        }
        catch ( Exception ex )
        {
            throw new ScriptRuntimeException( "attempt to perform arithmetic on a %s value", x.getType() );
        }
    }
    
    protected abstract MtsNumber calculate( MtsNumber a, MtsNumber b );
    
    @Override
    public final int stackSizeChange()
    {
        return -1;
    }
}
