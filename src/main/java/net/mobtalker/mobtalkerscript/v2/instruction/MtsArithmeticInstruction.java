package net.mobtalker.mobtalkerscript.v2.instruction;

import net.mobtalker.mobtalkerscript.v2.*;
import net.mobtalker.mobtalkerscript.v2.value.*;

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
            return x.toMtsNumber();
        
        throw new ScriptRuntimeException( "attempt to perform arithmetic on a %s value", x.getType() );
    }
    
    protected abstract MtsNumber calculate( MtsNumber a, MtsNumber b );
    
    @Override
    public final int stackSizeChange()
    {
        return -1;
    }
}
