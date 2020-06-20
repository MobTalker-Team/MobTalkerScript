/*
 * Copyright (C) 2013-2020 Chimaine, MobTalkerScript contributors
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

import static net.mobtalker.mobtalkerscript.v3.value.MtsValue.*;
import static org.junit.Assert.*;
import net.mobtalker.mobtalkerscript.v3.value.*;

import org.junit.Test;

public class MtsTableLibTest
{
    
    @Test
    public void testRandom()
    {
        assertEquals( Nil, MtsTableLib.random( MtsVarargs.Empty ) );
        
        MtsString a = MtsString.of( "A" );
        MtsString b = MtsString.of( "B" );
        MtsString c = MtsString.of( "C" );
        MtsString d = MtsString.of( "D" );
        
        for ( int i = 0; i < 1e6; i++ )
        {
            MtsValue result = MtsTableLib.random( MtsVarargs.of( a, b, c, d ) );
            assertTrue( "Got " + result.toString(),
                        result.equals( a ) || result.equals( b ) || result.equals( c ) || result.equals( d ) );
        }
        
        for ( int i = 0; i < 1e6; i++ )
        {
            MtsTable t = new MtsTable();
            t.set( MtsNumber.of( 1 ), a );
            t.set( MtsNumber.of( 2 ), b );
            t.set( MtsNumber.of( 3 ), c );
            t.set( MtsNumber.of( 4 ), d );
            
            MtsValue result = MtsTableLib.random( MtsVarargs.of( t ) );
            assertTrue( "Got " + result.toString(),
                        result.equals( a ) || result.equals( b ) || result.equals( c ) || result.equals( d ) );
        }
    }
    
}
