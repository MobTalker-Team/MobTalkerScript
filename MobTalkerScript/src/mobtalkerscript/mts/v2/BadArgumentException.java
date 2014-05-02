package mobtalkerscript.mts.v2;

public class BadArgumentException extends ScriptRuntimeException
{
    public BadArgumentException( int level, String msg, Object... args )
    {
        super( level, msg, args );
    }
    
    public BadArgumentException( int level, String msg )
    {
        super( level, msg );
    }
    
    public BadArgumentException( String msg, Object... args )
    {
        super( msg, args );
    }
    
    public BadArgumentException( String msg )
    {
        super( msg );
    }
}
