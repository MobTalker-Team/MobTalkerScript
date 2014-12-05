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

import com.google.common.base.Strings;

/**
 * "Inspired" by FmlLogFormatter (by cpw).
 */
/* package */final class LogFormatter extends Formatter
{
    static final String LINE_SEPARATOR = System.getProperty( "line.separator" );
    
    private final StringBuilder buffer = new StringBuilder( 500 );
    
    @Override
    public String format( LogRecord record )
    {
        StringBuilder msg = buffer;
        msg.setLength( 0 );
        
        Level lvl = record.getLevel();
        String name = lvl.getLocalizedName();
        if ( name == null )
        {
            name = lvl.getName();
        }
        
        if ( ( name != null ) && ( name.length() > 0 ) )
        {
            msg.append( '[' )
               .append( Strings.padEnd( name + "] ", 8, ' ' ) );
        }
        else
        {
            msg.append( ' ' );
        }
        
        if ( record.getLoggerName() != null )
        {
            msg.append( '[' )
               .append( record.getLoggerName() )
               .append( "] " );
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