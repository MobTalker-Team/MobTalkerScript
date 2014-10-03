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
package net.mobtalker.mobtalkerscript.v2.lib;

import static net.mobtalker.mobtalkerscript.v2.MtsCheck.*;
import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;
import net.mobtalker.mobtalkerscript.v2.BadArgumentException;
import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.userdata.*;

public final class MtsMathLib
{
    @MtsNativeField
    public static final MtsNumber PI = valueOf( Math.PI );
    
    @MtsNativeField
    public static final MtsNumber E = valueOf( Math.E );
    
    @MtsNativeField
    public static final MtsNumber Epsilon = valueOf( Double.longBitsToDouble( 0x3ca0000000000000L ) );
    
    // ========================================
    
    @MtsNativeFunction
    public static MtsNumber abs( MtsValue arg1 )
    {
        return valueOf( Math.abs( checkNumber( arg1, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber ceil( MtsValue arg1 )
    {
        return valueOf( Math.ceil( checkNumber( arg1, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber cos( MtsValue arg1 )
    {
        return valueOf( Math.cos( checkNumber( arg1, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber cosH( MtsValue arg1 )
    {
        return valueOf( Math.cosh( checkNumber( arg1, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber floor( MtsValue arg1 )
    {
        return valueOf( Math.floor( checkNumber( arg1, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber log( MtsValue arg1 )
    {
        return valueOf( Math.log( checkNumber( arg1, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber log10( MtsValue arg1 )
    {
        return valueOf( Math.log10( checkNumber( arg1, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber max( MtsVarArgs args )
    {
        if ( args.isEmpty() )
            throw new BadArgumentException( "got no value" );
        
        double result = checkNumber( args, 0 );
        
        for ( int i = 1; i < args.count(); i++ )
        {
            result = Math.max( result, checkNumber( args, i ) );
        }
        
        return valueOf( result );
    }
    
    @MtsNativeFunction
    public static MtsNumber min( MtsVarArgs args )
    {
        if ( args.isEmpty() )
            throw new BadArgumentException( "got no value" );
        
        double result = checkNumber( args, 0 );
        
        for ( int i = 1; i < args.count(); i++ )
        {
            result = Math.min( result, checkNumber( args, i ) );
        }
        
        return valueOf( result );
    }
    
    @MtsNativeFunction
    public static MtsNumber pow( MtsValue arg1, MtsValue arg2 )
    {
        return valueOf( Math.pow( checkNumber( arg1, 0 ), checkNumber( arg2, 1 ) ) );
    }
    
    private static final java.util.Random _rnd = new java.util.Random();
    
    @MtsNativeFunction
    public static MtsNumber random( MtsValue argMin, MtsValue argMax )
    {
        if ( argMax.isNil() )
        {
            if ( argMin.isNil() )
                return random();
            return random( argMin );
        }
        
        int a = checkInteger( argMin, 0 );
        int b = checkInteger( argMax, 1 );
        return valueOf( a + _rnd.nextInt( b ) );
    }
    
    private static MtsNumber random( MtsValue arg1 )
    {
        return valueOf( _rnd.nextInt( checkInteger( arg1, 0 ) ) );
    }
    
    private static MtsNumber random()
    {
        return valueOf( _rnd.nextDouble() );
    }
    
    @MtsNativeFunction
    public static MtsNumber round( MtsValue arg1 )
    {
        return valueOf( Math.round( checkNumber( arg1, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber sin( MtsValue arg1 )
    {
        return valueOf( Math.sin( checkNumber( arg1, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber sinH( MtsValue arg1 )
    {
        return valueOf( Math.sinh( checkNumber( arg1, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber sign( MtsValue arg1 )
    {
        return valueOf( Math.signum( checkNumber( arg1, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber sqrt( MtsValue arg1 )
    {
        return valueOf( Math.sqrt( checkNumber( arg1, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber tan( MtsValue arg1 )
    {
        return valueOf( Math.tan( checkNumber( arg1, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber tanH( MtsValue arg1 )
    {
        return valueOf( Math.tanh( checkNumber( arg1, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber toDegrees( MtsValue arg1 )
    {
        return valueOf( Math.toDegrees( checkNumber( arg1, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsNumber toRadians( MtsValue arg1 )
    {
        return valueOf( Math.toRadians( checkNumber( arg1, 0 ) ) );
    }
}
