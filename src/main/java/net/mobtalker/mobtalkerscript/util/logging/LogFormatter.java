package net.mobtalker.mobtalkerscript.util.logging;

import java.io.*;
import java.util.logging.*;

import com.google.common.base.*;

/**
 * "Inspired" by FmlLogFormatter. (More like exact copy).
 */
final class LogFormatter extends Formatter
{
    static final String LINE_SEPARATOR = System.getProperty( "line.separator" );
    
//        private SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
    
    @Override
    public String format( LogRecord record )
    {
        StringBuilder msg = new StringBuilder();
//            msg.append( this.dateFormat.format( Long.valueOf( record.getMillis() ) ) );
        
        Level lvl = record.getLevel();
        String name = lvl.getLocalizedName();
        if ( name == null )
        {
            name = lvl.getName();
        }
        
        if ( ( name != null ) && ( name.length() > 0 ) )
        {
            msg.append( Strings.padEnd( "[" + name + "] ", 9, ' ' ) );
        }
        else
        {
            msg.append( " " );
        }
        
        if ( record.getLoggerName() != null )
        {
            msg.append( "[" + record.getLoggerName() + "] " );
        }
        else
        {
            msg.append( "[] " );
        }
        msg.append( formatMessage( record ) );
        msg.append( LINE_SEPARATOR );
        Throwable thr = record.getThrown();
        
        if ( thr != null )
        {
            StringWriter thrDump = new StringWriter();
            thr.printStackTrace( new PrintWriter( thrDump ) );
            msg.append( thrDump.toString() );
        }
        
        return msg.toString();
    }
}