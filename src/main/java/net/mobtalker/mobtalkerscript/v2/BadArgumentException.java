package net.mobtalker.mobtalkerscript.v2;

import net.mobtalker.mobtalkerscript.v2.value.*;

public class BadArgumentException extends ScriptRuntimeException
{
    public BadArgumentException( String msg, Object... args )
    {
        super( msg, args );
    }
    
    public BadArgumentException( int argIndex, String msg, Object... args )
    {
        super( "bad argument #" + argIndex + " (" + msg + ")", args );
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
