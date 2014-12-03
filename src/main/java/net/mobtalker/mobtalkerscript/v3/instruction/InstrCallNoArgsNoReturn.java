package net.mobtalker.mobtalkerscript.v3.instruction;

import static net.mobtalker.mobtalkerscript.v3.value.MtsValue.*;
import net.mobtalker.mobtalkerscript.v3.MtsFrame;
import net.mobtalker.mobtalkerscript.v3.value.*;

public class InstrCallNoArgsNoReturn extends InstrCall
{
    /* package */InstrCallNoArgsNoReturn()
    {
        super( 0, 0 );
    }
    
    // ========================================
    
    @Override
    protected final MtsVarargs getCallArgs( MtsFrame frame )
    {
        return EMPTY_VARARGS;
    }
    
    @Override
    protected final void pushResults( MtsFrame frame, MtsValue result )
    {}
}
