package net.mobtalker.mobtalkerscript.v3;

import net.mobtalker.mobtalkerscript.v3.value.MtsType;

public class MtsComparisonException extends MtsScriptRuntimeException
{
    public MtsComparisonException( MtsType a, MtsType b )
    {
        super( "attempt to compare %s with %s", a, b );
    }
}
