package mobtalkerscript.v2;

import mobtalkerscript.v2.value.*;

public class WrongTypeException extends ScriptRuntimeException
{
    public WrongTypeException( MtsType expected, MtsType actual )
    {
        super( "Expected " + expected.getName() + ", got " + actual.getName() );
    }
}
