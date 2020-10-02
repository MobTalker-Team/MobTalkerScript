/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3;

import net.mobtalker.mobtalkerscript.v3.value.MtsType;

public class MtsArgumentException extends MtsRuntimeException
{
    public MtsArgumentException( String msg, Object... args )
    {
        super( msg, args );
    }
    
    public MtsArgumentException( int argIndex, String msg, Object... args )
    {
        super( "bad argument #" + ( argIndex + 1 ) + " (" + msg + ")", args );
    }
    
    public MtsArgumentException( MtsType expected, MtsType actual )
    {
        super( "bad argument (%s expected, got %s)", expected, actual );
    }
    
    public MtsArgumentException( int argIndex, MtsType expected, MtsType actual )
    {
        super( "bad argument #%d (%s expected, got %s)", argIndex + 1, expected, actual );
    }
    
    public MtsArgumentException( int argIndex, MtsType expectedA, MtsType expectedB, MtsType actual )
    {
        super( "bad argument #%d (%s or %s expected, got %s", argIndex + 1, expectedA, expectedB, actual );
    }
}
