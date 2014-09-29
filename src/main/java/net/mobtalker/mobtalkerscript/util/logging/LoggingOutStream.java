/*
 * Copyright (C) 2013-2014 Chimaine
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mobtalker.mobtalkerscript.util.logging;

import java.io.*;
import java.util.logging.*;

final class LoggingOutStream extends ByteArrayOutputStream
{
    private Logger log;
    private StringBuilder currentMessage;
    
    // ========================================
    
    public LoggingOutStream( Logger log )
    {
        this.log = log;
        currentMessage = new StringBuilder();
    }
    
    // ========================================
    
    @Override
    public void flush() throws IOException
    {
        String record;
        synchronized ( MtsLog.class )
        {
            super.flush();
            record = this.toString();
            super.reset();
            
            currentMessage.append( record.replace( LogFormatter.LINE_SEPARATOR, "\n" ) );
            // Are we longer than just the line separator?
            int lastIdx = -1;
            int idx = currentMessage.indexOf( "\n", lastIdx + 1 );
            while ( idx >= 0 )
            {
                log.log( Level.INFO, currentMessage.substring( lastIdx + 1, idx ) );
                lastIdx = idx;
                idx = currentMessage.indexOf( "\n", lastIdx + 1 );
            }
            if ( lastIdx >= 0 )
            {
                String rem = currentMessage.substring( lastIdx + 1 );
                currentMessage.setLength( 0 );
                currentMessage.append( rem );
            }
        }
    }
}