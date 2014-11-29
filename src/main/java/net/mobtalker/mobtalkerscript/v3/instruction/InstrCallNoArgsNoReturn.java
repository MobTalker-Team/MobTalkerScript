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
    protected MtsVarArgs getCallArgs( MtsFrame frame )
    {
        return EMPTY_VARARGS;
    }
    
    @Override
    protected void pushResults( MtsFrame frame, MtsValue result )
    {}
}
