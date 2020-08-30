/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3;

/**
 * Thrown when a parser encounters an error
 */
@SuppressWarnings( "serial" )
public class MtsParserException extends RuntimeException
{
    
    public MtsParserException( String msg )
    {
        super( msg );
    }
    
    public MtsParserException( Exception parent )
    {
        super( parent );
    }
    
    public MtsParserException( String msg, Object... args )
    {
        super( String.format( msg, args ) );
    }
    
    public MtsParserException( String msg, Exception parent, Object... args )
    {
        super( String.format( msg, args ), parent );
    }
    
}
