package mobtalkerscript.mts.v2;

import mobtalkerscript.mts.v2.value.*;

/**
 * Thrown when the execution of script code causes an exception.
 */
@SuppressWarnings( "serial" )
public class ScriptRuntimeException extends RuntimeException
{
    private static Object[] convert( MtsValue[] args )
    {
        Object[] sArgs = new Object[args.length];
        for ( int i = 0; i < args.length; i++ )
        {
            sArgs[i] = args[i].toStringMts().toJava();
        }
        return sArgs;
    }
    
    public ScriptRuntimeException( String msg )
    {
        super( msg );
    }
    
    public ScriptRuntimeException( Exception parent )
    {
        super( parent );
    }
    
    public ScriptRuntimeException( String msg, Object... args )
    {
        super( String.format( msg, args ) );
    }
    
    public ScriptRuntimeException( String msg, Exception parent, Object... args )
    {
        super( String.format( msg, args ), parent );
    }
    
    public ScriptRuntimeException( MtsValue msg )
    {
        super( msg.toStringMts().toJava() );
    }
    
    public ScriptRuntimeException( String msg, MtsValue... args )
    {
        this( msg, convert( args ) );
    }
    
    public ScriptRuntimeException( String msg, Exception parent, MtsValue... args )
    {
        this( msg, parent, convert( args ) );
    }
    
}
