package mobtalkerscript.v2;

import mobtalkerscript.v2.value.*;

public class BadArgumentException extends ScriptRuntimeException
{
    public BadArgumentException( String msg, Object... args )
    {
        super( msg, args );
    }
    
    public BadArgumentException( MtsType expected, MtsType actual )
    {
        super( "bad argument (%s expected, got %s)", expected, actual );
    }
    
    public BadArgumentException( int argIndex, MtsType expected, MtsType actual )
    {
        super( "bad argument #%s (%s expected, got %s)", argIndex, expected, actual );
    }
}
