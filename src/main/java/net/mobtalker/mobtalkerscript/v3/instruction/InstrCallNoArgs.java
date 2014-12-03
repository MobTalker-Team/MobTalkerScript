package net.mobtalker.mobtalkerscript.v3.instruction;

import static net.mobtalker.mobtalkerscript.v3.value.MtsValue.*;
import net.mobtalker.mobtalkerscript.v3.MtsFrame;
import net.mobtalker.mobtalkerscript.v3.value.MtsVarargs;

public class InstrCallNoArgs extends InstrCall
{
    /* package */InstrCallNoArgs( int nReturn )
    {
        super( 0, nReturn );
    }
    
    // ========================================
    
    @Override
    protected final MtsVarargs getCallArgs( MtsFrame frame )
    {
        return EMPTY_VARARGS;
    }
}
