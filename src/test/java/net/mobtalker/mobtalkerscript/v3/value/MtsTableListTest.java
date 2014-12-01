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

import org.junit.*;

public class MtsTableListTest
{
    private MtsTableList _list;
    
    @Before
    public void setUp() throws Exception
    {
        _list = new MtsTableList( 0 );
    }
    
    @Test
    public void testLength()
    {
        assertEquals( 0, _list.size() );
        
        _list.add( valueOf( "a" ) );
        assertEquals( 1, _list.size() );
        
        _list.add( valueOf( "bc" ) );
        _list.add( valueOf( "def" ) );
        _list.add( valueOf( "gh" ) );
        assertEquals( 4, _list.size() );
        
        _list.removeLast();
        assertEquals( 3, _list.size() );
        
        _list.add( 2, valueOf( "ijkl" ) );
        assertEquals( 4, _list.size() );
        
        _list.remove( 1 );
        assertEquals( 3, _list.size() );
        
        _list.clear();
        assertEquals( 0, _list.size() );
    }
    
    @Test
    public void testContains()
    {
        assertFalse( _list.contains( valueOf( "foo" ) ) );
        assertFalse( _list.canGetOrRemoveAt( 0 ) );
        assertFalse( _list.contains( ZERO ) );
        assertFalse( _list.canGetOrRemoveAt( 1 ) );
        assertFalse( _list.contains( ONE ) );
        assertFalse( _list.canGetOrRemoveAt( ONE ) );
        
        _list.add( valueOf( "bc" ) );
        assertTrue( _list.canGetOrRemoveAt( 0 ) );
        assertTrue( _list.canGetOrRemoveAt( ONE ) );
        assertTrue( _list.contains( valueOf( "bc" ) ) );
        
        _list.removeLast();
        assertFalse( _list.canGetOrRemoveAt( 0 ) );
        assertFalse( _list.canGetOrRemoveAt( ONE ) );
        assertFalse( _list.contains( valueOf( "bc" ) ) );
        
        _list.add( valueOf( "def" ) );
        _list.add( 1, valueOf( "ijkl" ) );
        assertTrue( _list.canGetOrRemoveAt( 1 ) );
        assertTrue( _list.canGetOrRemoveAt( valueOf( 2 ) ) );
        assertTrue( _list.contains( valueOf( "ijkl" ) ) );
        
        _list.add( valueOf( "gh" ) );
        _list.remove( 1 );
        assertTrue( _list.canGetOrRemoveAt( 1 ) );
        assertTrue( _list.canGetOrRemoveAt( valueOf( 2 ) ) );
        assertFalse( _list.canGetOrRemoveAt( 2 ) );
        assertFalse( _list.canGetOrRemoveAt( valueOf( 3 ) ) );
        assertTrue( _list.contains( valueOf( "gh" ) ) );
        assertFalse( _list.contains( valueOf( "ijkl" ) ) );
        
        _list.clear();
        assertFalse( _list.canGetOrRemoveAt( 0 ) );
        assertFalse( _list.canGetOrRemoveAt( ZERO ) );
        assertFalse( _list.canGetOrRemoveAt( 1 ) );
        assertFalse( _list.canGetOrRemoveAt( ONE ) );
        assertFalse( _list.contains( valueOf( "gh" ) ) );
        assertFalse( _list.contains( valueOf( "def" ) ) );
        assertFalse( _list.contains( valueOf( "ijkl" ) ) );
        assertFalse( _list.contains( valueOf( "bc" ) ) );
    }
    
    @Test
    public void testAdd()
    {
        _list.add( valueOf( "a" ) );
        assertEquals( valueOf( "a" ), _list.get( 0 ) );
        
        _list.add( valueOf( "bc" ) );
        _list.add( valueOf( "def" ) );
        assertEquals( valueOf( "def" ), _list.get( 2 ) );
        
        _list.removeLast();
        _list.add( valueOf( "gh" ) );
        assertEquals( valueOf( "gh" ), _list.get( 2 ) );
        
        _list.add( 2, valueOf( "ijkl" ) );
        _list.add( valueOf( "mno" ) );
        assertEquals( valueOf( "mno" ), _list.get( 4 ) );
    }
    
    @Test( expected = ArrayIndexOutOfBoundsException.class )
    public void testInsert()
    {
        _list.add( 0, valueOf( "a" ) );
        assertEquals( valueOf( "a" ), _list.get( 0 ) );
        
        _list.add( valueOf( "bc" ) );
        _list.add( valueOf( "def" ) );
        
        _list.add( 2, valueOf( "ijkl" ) );
        assertEquals( valueOf( "ijkl" ), _list.get( 2 ) );
        
        _list.remove( 2 );
        _list.add( 2, valueOf( "gh" ) );
        assertEquals( valueOf( "gh" ), _list.get( 2 ) );
        
        _list.add( _list.size(), valueOf( "mno" ) );
        assertEquals( valueOf( "mno" ), _list.get( 4 ) );
        
        _list.add( _list.size() + 1, valueOf( "pq" ) );
    }
    
    @Test( expected = ArrayIndexOutOfBoundsException.class )
    public void testSet()
    {
        _list.add( valueOf( "a" ) );
        _list.add( valueOf( "bc" ) );
        _list.add( valueOf( "def" ) );
        _list.add( valueOf( "gh" ) );
        
        _list.set( 0, valueOf( "ijk" ) );
        assertEquals( valueOf( "ijk" ), _list.get( 0 ) );
        
        _list.set( 1, valueOf( "lm" ) );
        assertEquals( valueOf( "lm" ), _list.get( 1 ) );
        
        _list.set( 3, valueOf( "opq" ) );
        assertEquals( valueOf( "opq" ), _list.get( 3 ) );
        
        assertEquals( 4, _list.size() );
        _list.set( 3, NIL );
        assertEquals( 3, _list.size() );
        
        _list.set( 1, NIL );
        assertEquals( 1, _list.size() );
        
        _list.set( 1, ONE );
    }
    
}
