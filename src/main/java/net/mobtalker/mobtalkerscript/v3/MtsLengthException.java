package net.mobtalker.mobtalkerscript.v3;

import net.mobtalker.mobtalkerscript.v3.value.MtsType;

public class MtsLengthException extends MtsScriptRuntimeException
{
    public MtsLengthException( MtsType a )
    {
        super( "attempt to get length of a %s value", a );
    }
}
