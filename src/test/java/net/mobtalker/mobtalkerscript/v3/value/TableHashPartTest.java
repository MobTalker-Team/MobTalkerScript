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
package net.mobtalker.mobtalkerscript.v3.value;

import static net.mobtalker.mobtalkerscript.v3.value.MtsValue.*;
import static org.junit.Assert.*;
import net.mobtalker.mobtalkerscript.v3.*;
import net.mobtalker.mobtalkerscript.v3.value.*;
import net.mobtalker.mobtalkerscript.v3.value.MtsTable.Entry;

import org.junit.*;

public class TableHashPartTest
{
    private TableHashPart _map;
    
    @Before
    public void setUp() throws Exception
    {
        _map = new TableHashPart( 0 );
    }
    
    @Test( expected = ScriptRuntimeException.class )
    public void testSetGet()
    {
        assertEquals( NIL, _map.get( valueOf( "z" ) ) );
        
        _map.set( valueOf( "a" ), valueOf( "foo" ) );
        assertEquals( 1, _map.count() );
        assertEquals( valueOf( "foo" ), _map.get( valueOf( "a" ) ) );
        
        _map.set( valueOf( "b" ), valueOf( "bar" ) );
        _map.set( valueOf( "c" ), valueOf( "caz" ) );
        assertEquals( 3, _map.count() );
        assertEquals( valueOf( "caz" ), _map.get( valueOf( "c" ) ) );
        assertEquals( valueOf( "bar" ), _map.get( valueOf( "b" ) ) );
        
        _map.set( valueOf( "b" ), NIL );
        assertEquals( 2, _map.count() );
        assertEquals( NIL, _map.get( valueOf( "b" ) ) );
        
        _map.set( valueOf( "a" ), valueOf( "abraz" ) );
        assertEquals( 2, _map.count() );
        assertEquals( valueOf( "abraz" ), _map.get( valueOf( "a" ) ) );
        
        _map.set( NIL, valueOf( "z" ) );
    }
    
    @Test
    public void testCount()
    {
        assertEquals( 0, _map.count() );
        
        _map.set( valueOf( "a" ), valueOf( "foo" ) );
        assertEquals( 1, _map.count() );
        
        _map.set( valueOf( "b" ), valueOf( "bar" ) );
        _map.set( valueOf( "c" ), valueOf( "caz" ) );
        assertEquals( 3, _map.count() );
        
        _map.set( valueOf( "b" ), NIL );
        assertEquals( 2, _map.count() );
        
        _map.clear();
        assertEquals( 0, _map.count() );
    }
    
    @Test
    public void testGetFirst()
    {
        assertNull( _map.getFirst() );
        
        _map.set( valueOf( "a" ), valueOf( "foo" ) );
        assertNotNull( _map.getFirst() );
        
        _map.set( valueOf( "b" ), valueOf( "bar" ) );
        _map.set( valueOf( "c" ), valueOf( "caz" ) );
        assertNotNull( _map.getFirst() );
        
        _map.set( valueOf( "b" ), NIL );
        assertNotNull( _map.getFirst() );
        
        _map.clear();
        assertNull( _map.getFirst() );
    }
    
    @Test
    public void testGetEntryAfter()
    {
        assertNull( _map.getNext( NIL ) );
        
        _map.set( valueOf( "Lorem ipsum dolor sit amet," ), //
                  valueOf( "a" ) );
        _map.set( valueOf( " consectetuer adipiscing elit," ), //
                  valueOf( "b" ) );
        _map.set( valueOf( " sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat." ), //
                  valueOf( "c" ) );
        _map.set( valueOf( " Ut wisi enim ad minim veniam," ), //
                  valueOf( "d" ) );
        _map.set( valueOf( " quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat." ), //
                  valueOf( "e" ) );
        
        MtsValue lastKey = NIL;
        for ( int i = 0; i < 5; i++ )
        {
            Entry next = _map.getNext( lastKey );
            assertNotNull( next );
            assertNotEquals( lastKey, next.key );
            lastKey = next.key;
        }
        assertNull( _map.getNext( lastKey ) );
    }
    
}
