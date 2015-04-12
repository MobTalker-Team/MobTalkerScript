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
package net.mobtalker.mobtalkerscript.v3.value;

import static net.mobtalker.mobtalkerscript.v3.value.MtsValue.*;
import static org.junit.Assert.*;

import java.util.Map;

import net.mobtalker.mobtalkerscript.v3.MtsRuntimeException;

import org.junit.*;

public class MtsTableMapTest
{
    private MtsTableMap _map;
    
    @Before
    public void setUp() throws Exception
    {
        _map = new MtsTableMap( 0 );
    }
    
    @Test( expected = MtsRuntimeException.class )
    public void testSetGet()
    {
        assertEquals( Nil, _map.get( MtsString.of( "z" ) ) );
        
        _map.put( MtsString.of( "a" ), MtsString.of( "foo" ) );
        assertEquals( 1, _map.size() );
        assertEquals( MtsString.of( "foo" ), _map.get( MtsString.of( "a" ) ) );
        
        _map.put( MtsString.of( "b" ), MtsString.of( "bar" ) );
        _map.put( MtsString.of( "c" ), MtsString.of( "caz" ) );
        assertEquals( 3, _map.size() );
        assertEquals( MtsString.of( "caz" ), _map.get( MtsString.of( "c" ) ) );
        assertEquals( MtsString.of( "bar" ), _map.get( MtsString.of( "b" ) ) );
        
        _map.put( MtsString.of( "b" ), Nil );
        assertEquals( 2, _map.size() );
        assertEquals( Nil, _map.get( MtsString.of( "b" ) ) );
        
        _map.put( MtsString.of( "a" ), MtsString.of( "abraz" ) );
        assertEquals( 2, _map.size() );
        assertEquals( MtsString.of( "abraz" ), _map.get( MtsString.of( "a" ) ) );
        
        _map.put( Nil, MtsString.of( "z" ) );
    }
    
    @Test
    public void testCount()
    {
        assertEquals( 0, _map.size() );
        
        _map.put( MtsString.of( "a" ), MtsString.of( "foo" ) );
        assertEquals( 1, _map.size() );
        
        _map.put( MtsString.of( "b" ), MtsString.of( "bar" ) );
        _map.put( MtsString.of( "c" ), MtsString.of( "caz" ) );
        assertEquals( 3, _map.size() );
        
        _map.put( MtsString.of( "b" ), Nil );
        assertEquals( 2, _map.size() );
        
        _map.clear();
        assertEquals( 0, _map.size() );
    }
    
    @Test
    public void testGetFirst()
    {
        assertNull( _map.getFirst() );
        
        _map.put( MtsString.of( "a" ), MtsString.of( "foo" ) );
        assertNotNull( _map.getFirst() );
        
        _map.put( MtsString.of( "b" ), MtsString.of( "bar" ) );
        _map.put( MtsString.of( "c" ), MtsString.of( "caz" ) );
        assertNotNull( _map.getFirst() );
        
        _map.put( MtsString.of( "b" ), Nil );
        assertNotNull( _map.getFirst() );
        
        _map.clear();
        assertNull( _map.getFirst() );
    }
    
    @Test
    public void testGetEntryAfter()
    {
        assertNull( _map.getNext( Nil ) );
        
        _map.put( MtsString.of( "Lorem ipsum dolor sit amet," ), //
                  MtsString.of( "a" ) );
        _map.put( MtsString.of( " consectetuer adipiscing elit," ), //
                  MtsString.of( "b" ) );
        _map.put( MtsString.of( " sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat." ), //
                  MtsString.of( "c" ) );
        _map.put( MtsString.of( " Ut wisi enim ad minim veniam," ), //
                  MtsString.of( "d" ) );
        _map.put( MtsString.of( " quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat." ), //
                  MtsString.of( "e" ) );
        
        MtsValue lastKey = Nil;
        for ( int i = 0; i < 5; i++ )
        {
            Map.Entry<MtsValue, MtsValue> next = _map.getNext( lastKey );
            assertNotNull( next );
            assertNotEquals( lastKey, next.getKey() );
            lastKey = next.getKey();
        }
        assertNull( _map.getNext( lastKey ) );
    }
    
}
