package mobtalkerscript.util.logging;

import java.util.logging.*;

public final class MtsLog
{
    private static Logger LoggerParent;
    public static MtsLog CompilerLog;
    public static MtsLog EngineLog;
    
    // ========================================
    
    public static void setLogger( Logger logger, boolean configure )
    {
        LoggerParent = logger;
        
        if ( configure )
        {
            LoggerParent.setUseParentHandlers( false );
            
            ConsoleHandler h = new ConsoleHandler();
            h.setLevel( Level.ALL );
            h.setFormatter( new LogFormatter() );
            
            LoggerParent.addHandler( h );
        }
        
        CompilerLog = new MtsLog( Logger.getLogger( logger.getName() + ".Compiler" ) );
        EngineLog = new MtsLog( Logger.getLogger( logger.getName() + ".Engine" ) );
    }
    
    // ========================================
    
    private final Logger _log;
    
    // ========================================
    
    public MtsLog( Logger logger )
    {
        _log = logger;
    }
    
    // ========================================
    
    public void setLevel( Level level )
    {
        _log.setLevel( level );
    }
    
    // ========================================
    
    public boolean isInfoEnabled()
    {
        return _log.isLoggable( Level.INFO );
    }
    
    public boolean isConfigEnabled()
    {
        return _log.isLoggable( Level.CONFIG );
    }
    
    public boolean isFineEnabled()
    {
        return _log.isLoggable( Level.FINE );
    }
    
    public boolean isFinerEnabled()
    {
        return _log.isLoggable( Level.FINER );
    }
    
    public boolean isFinestEnabled()
    {
        return _log.isLoggable( Level.FINEST );
    }
    
    // ========================================
    
    public void log( Level level, Object obj )
    {
        if ( _log.isLoggable( level ) )
            _log.log( level, java.util.Objects.toString( obj ) );
    }
    
    public void log( Level level, String msg, Object... data )
    {
        if ( _log.isLoggable( level ) )
            _log.log( level, String.format( msg, data ) );
    }
    
    public void log( Level level, Throwable ex, String msg, Object... data )
    {
        if ( _log.isLoggable( level ) )
            _log.log( level, String.format( msg, data ), ex );
    }
    
    // ========================================
    
    public void severe( Object obj )
    {
        log( Level.SEVERE, obj );
    }
    
    public void severe( String msg, Object... data )
    {
        log( Level.SEVERE, msg, data );
    }
    
    public void severe( String msg, Throwable ex, Object... data )
    {
        log( Level.SEVERE, msg, ex, data );
    }
    
    // ========================================
    
    public void warning( Object obj )
    {
        log( Level.WARNING, obj );
    }
    
    public void warning( String msg, Object... data )
    {
        log( Level.WARNING, msg, data );
    }
    
    public void warning( String msg, Throwable ex, Object... data )
    {
        log( Level.WARNING, msg, ex, data );
    }
    
    // ========================================
    
    public void info( String msg, Object... data )
    {
        log( Level.INFO, msg, data );
    }
    
    // ========================================
    
    public void config( String msg, Object... data )
    {
        log( Level.CONFIG, msg, data );
    }
    
    // ========================================
    
    public void fine( String msg, Object... data )
    {
        log( Level.FINE, msg, data );
    }
    
    // ========================================
    
    public void finer( String msg, Object... data )
    {
        log( Level.FINER, msg, data );
    }
    
    // ========================================
    
    public void finest( String msg, Object... data )
    {
        log( Level.FINEST, msg, data );
    }
}
