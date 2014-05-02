package mobtalkerscript.mts.v2;

/**
 * Thrown when a script engine encounters an internal error.
 */
@SuppressWarnings( "serial" )
public class ScriptEngineException extends RuntimeException
{
    
    public ScriptEngineException( String msg )
    {
        super( msg );
    }
    
    public ScriptEngineException( Exception parent )
    {
        super( parent );
    }
    
    public ScriptEngineException( String msg, Object... args )
    {
        super( String.format( msg, args ) );
    }
    
    public ScriptEngineException( String msg, Exception parent, Object... args )
    {
        super( String.format( msg, args ), parent );
    }
    
}
