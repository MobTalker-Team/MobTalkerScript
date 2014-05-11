package mobtalkerscript.mts.v2.instruction;

import static mobtalkerscript.mts.v2.value.MtsValue.*;
import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;

/* package */abstract class MtsArithmeticInstruction extends MtsInstruction
{
    @Override
    public final void execute( MtsFrame frame )
    {
        MtsNumber b = coerce( frame.pop() );
        frame.push( calculate( coerce( frame.pop() ), b ) );
    }
    
    public static MtsNumber coerce( MtsValue x )
    {
        if ( x.isNumber() )
            return x.asNumber();
        
        if ( x.isString() )
        {
            try
            {
                return valueOf( Double.parseDouble( x.asString().asJavaString() ) );
            }
            catch ( NumberFormatException ex )
            {}
        }
        
        throw new ScriptRuntimeException( "attempt to perform arithmetic on a %s value", x.getType() );
    }
    
    protected abstract MtsNumber calculate( MtsNumber a, MtsNumber b );
    
    @Override
    public final int stackSizeChange()
    {
        return -1;
    }
}
