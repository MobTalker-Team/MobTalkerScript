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
package net.mobtalker.mobtalkerscript.v3.lib;

import static net.mobtalker.mobtalkerscript.v3.value.MtsNumber.*;
import static net.mobtalker.mobtalkerscript.v3.value.MtsValue.*;
import static org.junit.Assert.*;
import net.mobtalker.mobtalkerscript.v3.value.MtsNumber;

import org.junit.Test;

public class MtsMathLibTest
{
    
    @Test
    public void testRandom()
    {
        // [0,1)
        for ( int i = 0; i < 1e6; i++ )
        {
            MtsNumber result = MtsMathLib.random( Nil, Nil );
            assertTrue( result.compareTo( Zero ) >= 0 );
            assertTrue( result.compareTo( One ) < 0 );
        }
        
        // [1,m]
        for ( int i = 1; i < 1e6; i++ )
        {
            MtsNumber max = MtsNumber.of( i );
            
            MtsNumber result = MtsMathLib.random( max, Nil );
            assertTrue( result.compareTo( One ) >= 0 );
            assertTrue( result.compareTo( max ) <= 0 );
        }
        
        // [m,n]
        {
            MtsNumber i = MtsNumber.of( Integer.MIN_VALUE );
            MtsNumber result = MtsMathLib.random( i, i );
            assertTrue( result.compareTo( i ) >= 0 );
            assertTrue( result.compareTo( i ) <= 0 );
        }
        {
            MtsNumber i = MtsNumber.of( Integer.MAX_VALUE );
            MtsNumber result = MtsMathLib.random( i, i );
            assertTrue( result.compareTo( i ) >= 0 );
            assertTrue( result.compareTo( i ) <= 0 );
        }
        for ( int i = 0; i < 1e6; i++ )
        {
            MtsNumber min = MtsNumber.of( 0 );
            MtsNumber max = MtsNumber.of( i );
            
            MtsNumber result = MtsMathLib.random( min, max );
            assertTrue( result.compareTo( min ) >= 0 );
            assertTrue( result.compareTo( max ) <= 0 );
        }
        for ( int i = 0; i < 1e6; i++ )
        {
            MtsNumber min = MtsNumber.of( -i );
            MtsNumber max = MtsNumber.of( 0 );
            
            MtsNumber result = MtsMathLib.random( min, max );
            assertTrue( result.compareTo( min ) >= 0 );
            assertTrue( result.compareTo( max ) <= 0 );
        }
    }
    
}
