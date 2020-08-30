/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3;

/**
 * Thrown when a script engine encounters an internal error.
 */
@SuppressWarnings( "serial" )
public class MtsEngineException extends RuntimeException
{
    
    public MtsEngineException( String msg )
    {
        super( msg );
    }
    
    public MtsEngineException( Exception parent )
    {
        super( parent );
    }
    
    public MtsEngineException( String msg, Object... args )
    {
        super( String.format( msg, args ) );
    }
    
    public MtsEngineException( String msg, Exception parent, Object... args )
    {
        super( String.format( msg, args ), parent );
    }
    
}
