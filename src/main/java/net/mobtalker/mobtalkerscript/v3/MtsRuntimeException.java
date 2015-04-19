/*
 * Copyright (C) 2013-2015 Chimaine
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
package net.mobtalker.mobtalkerscript.v3;

import java.util.List;

import net.mobtalker.mobtalkerscript.v3.compiler.SourcePosition;

import com.google.common.collect.Lists;

/**
 * Thrown when the execution of script code causes an exception.
 */
@SuppressWarnings( "serial" )
public class MtsRuntimeException extends RuntimeException
{
    private final int _level;
    private final List<MtsStackTraceElement> _stackTrace;
    
    {
        _stackTrace = Lists.newArrayList();
    }
    
    // ========================================
    
    public MtsRuntimeException( String msg )
    {
        this( 0, msg );
    }
    
    public MtsRuntimeException( int level, String msg )
    {
        super( msg );
        _level = Math.max( level, 0 );
    }
    
    public MtsRuntimeException( String msg, Object... args )
    {
        this( 0, String.format( msg, args ) );
    }
    
    public MtsRuntimeException( int level, String msg, Object... args )
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
    
    public void addStackTraceElement( String source, SourcePosition position, String function )
    {
        _stackTrace.add( new MtsStackTraceElement.Compiled( source, position, function ) );
    }
    
    public void addStackTraceElement( String function )
    {
        _stackTrace.add( new MtsStackTraceElement.Native( function ) );
    }
    
    public String createStackTrace()
    {
        StringBuilder s = new StringBuilder();
        
        s.append( "Exception: " ).append( getMessage() ).append( "\n" );
        s.append( "Stack traceback:" );
        
        for ( int i = _level; i < _stackTrace.size(); i++ )
        {
            s.append( "\n  " ).append( _stackTrace.get( i ).toString() );
        }
        
        return s.toString();
    }
    
    public List<MtsStackTraceElement> getStackTraceElements()
    {
        return _stackTrace;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return createStackTrace();
    }
    
}
