package mobtalkerscript.util.logging;

import java.io.*;
import java.util.logging.*;

public final class MTSLog
{
    private static Logger _log;
    
    public static void setLogger( Logger log )
    {
        _log = log;
        _configured = false;
        configureLogging();
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
        _log.log( level, java.util.Objects.toString( obj ) );
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
    
    // ========================================
    
    private static boolean _configured;
    
    private static Thread _consoleLogThread;
    static PrintStream _errCache;
    private static LogFormatter _formatter;
    
    private MTSLog()
    {}
    
    @SuppressWarnings( "resource" )
    private static void configureLogging()
    {
        if ( _configured )
            return;
        
        LogManager.getLogManager().reset();
        Logger globalLogger = Logger.getLogger( Logger.GLOBAL_LOGGER_NAME );
        globalLogger.setLevel( Level.OFF );
        
        Logger stdOut = Logger.getLogger( "STDOUT" );
        stdOut.setParent( _log );
        Logger stdErr = Logger.getLogger( "STDERR" );
        stdErr.setParent( _log );
        
        _log.setLevel( Level.ALL );
        _log.setUseParentHandlers( false );
        
        _consoleLogThread = new Thread( new ConsoleLogThread() );
        _consoleLogThread.setDaemon( true );
        _consoleLogThread.start();
        _formatter = new LogFormatter();
        
        resetLoggingHandlers();
        
        // Set system out to a log stream
        _errCache = System.err;
        
        System.setOut( new PrintStream( new LoggingOutStream( stdOut ), true ) );
        System.setErr( new PrintStream( new LoggingOutStream( stdErr ), true ) );
        
        _configured = true;
    }
    
    private static void resetLoggingHandlers()
    {
//        ConsoleLogThread.wrappedHandler.setLevel( Level.parse( System.getProperty( "fml.log.level", "INFO" ) ) );
        ConsoleLogThread.wrappedHandler.setLevel( Level.ALL );
        
        // Console handler captures the normal stderr before it gets replaced
        _log.addHandler( new ConsoleLogWrapper() );
        ConsoleLogThread.wrappedHandler.setFormatter( _formatter );
    }
    
}
