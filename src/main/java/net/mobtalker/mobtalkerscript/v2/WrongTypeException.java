package net.mobtalker.mobtalkerscript.v2;

import net.mobtalker.mobtalkerscript.v2.value.*;

public class WrongTypeException extends ScriptRuntimeException
{
    public WrongTypeException( MtsType expected, MtsType actual )
    {
        super( "Expected " + expected.getName() + ", got " + actual.getName() );
    }
}
