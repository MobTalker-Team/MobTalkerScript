/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.lib;

import static net.mobtalker.mobtalkerscript.v3.MtsCheck.*;
import net.mobtalker.mobtalkerscript.v3.MtsArgumentException;
import net.mobtalker.mobtalkerscript.v3.value.*;
import net.mobtalker.mobtalkerscript.v3.value.userdata.*;

public final class MtsMathLib
{
    @MtsNativeField
    public static final MtsNumber PI = MtsNumber.of( Math.PI );
    
    @MtsNativeField
    public static final MtsNumber E = MtsNumber.of( Math.E );
    
    @MtsNativeField
    public static final MtsNumber Epsilon = MtsNumber.of( Double.longBitsToDouble( 0x3ca0000000000000L ) );
    
    // ========================================
    
    @MtsNativeFunction
    public static MtsNumber abs( MtsValue arg )
    {
        return MtsNumber.of( Math.abs( checkNumber( arg, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber ceil( MtsValue arg )
    {
        return MtsNumber.of( Math.ceil( checkNumber( arg, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber cos( MtsValue arg )
    {
        return MtsNumber.of( Math.cos( checkNumber( arg, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber cosH( MtsValue arg )
    {
        return MtsNumber.of( Math.cosh( checkNumber( arg, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber floor( MtsValue arg )
    {
        return MtsNumber.of( Math.floor( checkNumber( arg, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber log( MtsValue arg )
    {
        return MtsNumber.of( Math.log( checkNumber( arg, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber log10( MtsValue arg )
    {
        return MtsNumber.of( Math.log10( checkNumber( arg, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber max( MtsVarargs args )
    {
        if ( args.isEmpty() )
            throw new MtsArgumentException( "got no value" );
        
        double result = checkNumber( args, 0 );
        
        for ( int i = 1; i < args.count(); i++ )
        {
            result = Math.max( result, checkNumber( args, i ) );
        }
        
        return MtsNumber.of( result );
    }
    
    @MtsNativeFunction
    public static MtsNumber min( MtsVarargs args )
    {
        if ( args.isEmpty() )
            throw new MtsArgumentException( "got no value" );
        
        double result = checkNumber( args, 0 );
        
        for ( int i = 1; i < args.count(); i++ )
        {
            result = Math.min( result, checkNumber( args, i ) );
        }
        
        return MtsNumber.of( result );
    }
    
    @MtsNativeFunction
    public static MtsNumber pow( MtsValue arg, MtsValue arg2 )
    {
        return MtsNumber.of( Math.pow( checkNumber( arg, 0 ), checkNumber( arg2, 1 ) ) );
    }
    
    private static final java.util.Random _rnd = new java.util.Random();
    
    @MtsNativeFunction
    public static MtsNumber random( MtsValue arg, MtsValue arg2 )
    {
        if ( arg2.isNil() )
        {
            if ( arg.isNil() )
                return MtsNumber.of( _rnd.nextDouble() );
            
            return MtsNumber.of( 1 + _rnd.nextInt( checkIntegerWithMinimum( arg, 0, 1 ) ) );
        }
        
        int min = checkInteger( arg, 0 );
        int max = checkInteger( arg2, 1 );
        int interval = ( max - min ) + 1; // TODO This is not overflow safe (interval >= Integer.MAX_VALUE)
        
        if ( interval <= 0 )
            throw new MtsArgumentException( "interval [%s,%s] is empty", min, max );
        if ( interval >= Integer.MAX_VALUE )
            throw new MtsArgumentException( "interval [%s,%s] is too large", min, max );
        
        return MtsNumber.of( min + _rnd.nextInt( interval ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber round( MtsValue arg )
    {
        return MtsNumber.of( Math.round( checkNumber( arg, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber sin( MtsValue arg )
    {
        return MtsNumber.of( Math.sin( checkNumber( arg, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber sinH( MtsValue arg )
    {
        return MtsNumber.of( Math.sinh( checkNumber( arg, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber sign( MtsValue arg )
    {
        return MtsNumber.of( Math.signum( checkNumber( arg, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber sqrt( MtsValue arg )
    {
        return MtsNumber.of( Math.sqrt( checkNumber( arg, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber tan( MtsValue arg )
    {
        return MtsNumber.of( Math.tan( checkNumber( arg, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber tanH( MtsValue arg )
    {
        return MtsNumber.of( Math.tanh( checkNumber( arg, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber toDegrees( MtsValue arg )
    {
        return MtsNumber.of( Math.toDegrees( checkNumber( arg, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber toRadians( MtsValue arg )
    {
        return MtsNumber.of( Math.toRadians( checkNumber( arg, 0 ) ) );
    }
}
