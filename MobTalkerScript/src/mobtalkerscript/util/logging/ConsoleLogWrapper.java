package mobtalkerscript.util.logging;

import java.util.logging.*;

final class ConsoleLogWrapper extends Handler
{
    @Override
    public void publish( LogRecord record )
    {
        boolean currInt = Thread.interrupted();
        try
        {
            ConsoleLogThread.recordQueue.put( record );
        }
        catch ( InterruptedException e )
        {
            e.printStackTrace( MTSLog._errCache );
        }
        if ( currInt )
        {
            Thread.currentThread().interrupt();
        }
    }
    
    @Override
    public void flush()
    {}
    
    @Override
    public void close() throws SecurityException
    {}
    
}