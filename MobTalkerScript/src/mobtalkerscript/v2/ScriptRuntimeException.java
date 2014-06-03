package mobtalkerscript.v2;

import java.util.*;

import com.google.common.collect.*;

/**
 * Thrown when the execution of script code causes an exception.
 */
@SuppressWarnings( "serial" )
public class ScriptRuntimeException extends RuntimeException
{
//    private static Object[] convert( MtsValue[] args )
//    {
//        Object[] sArgs = new Object[args.length];
//        for ( int i = 0; i < args.length; i++ )
//        {
//            sArgs[i] = args[i].toStringMts().toJava();
//        }
//        return sArgs;
//    }
    
    // ========================================
    
    private final int _level;
    private final List<MtsStackTraceElement> _stackTrace;
    
    {
        _stackTrace = Lists.newArrayList();
    }
    
    // ========================================
    
    public ScriptRuntimeException( String msg )
    {
        this( 0, msg );
    }
    
    public ScriptRuntimeException( int level, String msg )
    {
        super( msg );
        _level = level;
    }
    
    public ScriptRuntimeException( String msg, Object... args )
    {
        this( 0, String.format( msg, args ) );
    }
    
    public ScriptRuntimeException( int level, String msg, Object... args )
    {
        this( level, String.format( msg, args ) );
    }
    
    // ========================================
    
    public int getLevel()
    {
        return _level;
    }
    
    public void addStackTraceElement( MtsStackTraceElement e )
    {
        _stackTrace.add( e );
    }
    
    public String createStackTrace()
    {
        StringBuilder s = new StringBuilder();
        
        s.append( "Exception: " ).append( getMessage() ).append( "\n" );
        s.append( "Stack traceback:" );
        
        for ( int i = _level; i < _stackTrace.size(); i++ )
        {
            s.append( "\n\t" ).append( _stackTrace.get( i ).toString() );
        }
        
        return s.toString();
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return createStackTrace();
    }
    
}
