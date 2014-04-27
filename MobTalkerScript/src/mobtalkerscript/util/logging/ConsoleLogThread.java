package mobtalkerscript.util.logging;

import java.util.concurrent.*;
import java.util.logging.*;

final class ConsoleLogThread implements Runnable
{
    static ConsoleHandler wrappedHandler = new ConsoleHandler();
    static LinkedBlockingQueue<LogRecord> recordQueue = new LinkedBlockingQueue<LogRecord>();
    
    @Override
    public void run()
    {
        do
        {
            LogRecord lr;
            try
            {
                lr = recordQueue.take();
                wrappedHandler.publish( lr );
            }
            catch ( InterruptedException e )
            {
                e.printStackTrace( MTSLog._errCache );
                Thread.interrupted();
                // Stupid
            }
        }
        while ( true );
    }
}