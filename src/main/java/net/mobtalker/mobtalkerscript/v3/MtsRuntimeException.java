/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
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
