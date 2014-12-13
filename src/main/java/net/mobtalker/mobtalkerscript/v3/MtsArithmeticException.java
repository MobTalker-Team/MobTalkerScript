package net.mobtalker.mobtalkerscript.v3;

import net.mobtalker.mobtalkerscript.v3.value.MtsType;

public class MtsArithmeticException extends MtsScriptRuntimeException
{
    public MtsArithmeticException( MtsType a )
    {
        super( "attempt to perform arithmetic on a %s value", a );
    }
}
