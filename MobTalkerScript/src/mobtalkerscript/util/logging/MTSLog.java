package mobtalkerscript.util.logging;

import java.util.*;
import java.util.logging.*;

public class MTSLog
{
    
    private static final Logger _log;
    
    static
    {
        _log = Logger.getLogger( "MobTalkerScript" );
        _log.setUseParentHandlers( false );
    }
    
    public static Logger getLogger()
    {
        return _log;
    }
    
    // ========================================
    
    public static boolean isFineEnabled()
    {
        return _log.isLoggable( Level.FINE );
    }
    
    public static boolean isFinerEnabled()
    {
        return _log.isLoggable( Level.FINER );
    }
    
    public static boolean isFinestEnabled()
    {
        return _log.isLoggable( Level.FINEST );
    }
    
    // ========================================
    
    public static void log( Level level, Object obj )
    {
        _log.log( level, Objects.toString( obj ) );
    }
    
    public static void log( Level level, String msg, Object... data )
    {
        _log.log( level, String.format( msg, data ) );
    }
    
    public static void log( Level level, Throwable ex, String msg, Object... data )
    {
        _log.log( level, String.format( msg, data ), ex );
    }
    
    // ========================================
    
    public static void severe( Object obj )
    {
        log( Level.SEVERE, obj );
    }
    
    public static void severe( String msg, Object... data )
    {
        log( Level.SEVERE, msg, data );
    }
    
    public static void severe( String msg, Throwable ex, Object... data )
    {
        log( Level.SEVERE, msg, ex, data );
    }
    
    // ========================================
    
    public static void warning( Object obj )
    {
        log( Level.WARNING, obj );
    }
    
    public static void warning( String msg, Object... data )
    {
        log( Level.WARNING, msg, data );
    }
    
    public static void warning( String msg, Throwable ex, Object... data )
    {
        log( Level.WARNING, msg, ex, data );
    }
    
    // ========================================
    
    public static void info( String msg, Object... data )
    {
        log( Level.INFO, msg, data );
    }
    
    // ========================================
    
    public static void config( String msg, Object... data )
    {
        log( Level.CONFIG, msg, data );
    }
    
    // ========================================
    
    public static void fine( String msg, Object... data )
    {
        if ( isFineEnabled() )
        {
            log( Level.FINE, msg, data );
        }
    }
    
    // ========================================
    
    public static void finer( String msg, Object... data )
    {
        if ( isFinerEnabled() )
        {
            log( Level.FINER, msg, data );
        }
    }
    
    // ========================================
    
    public static void finest( String msg, Object... data )
    {
        if ( isFinestEnabled() )
        {
            log( Level.FINEST, msg, data );
        }
    }
    
}
