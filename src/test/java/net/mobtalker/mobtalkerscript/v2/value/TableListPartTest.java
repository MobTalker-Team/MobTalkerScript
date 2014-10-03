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
package net.mobtalker.mobtalkerscript.v2.value;

import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;
import static org.junit.Assert.*;

import org.junit.*;

public class TableListPartTest
{
    
    private TableListPart _list;
    
    @Before
    public void setUp() throws Exception
    {
        _list = new TableListPart( 0 );
    }
    
    @Test
    public void testIsValidKey()
    {
        assertFalse( TableListPart.isValidKey( TRUE ) );
        assertFalse( TableListPart.isValidKey( FALSE ) );
        assertFalse( TableListPart.isValidKey( NIL ) );
        
        assertFalse( TableListPart.isValidKey( valueOf( 0.1 ) ) );
        assertFalse( TableListPart.isValidKey( valueOf( 1.34 ) ) );
        
        assertFalse( TableListPart.isValidKey( valueOf( 0 ) ) );
        assertFalse( TableListPart.isValidKey( valueOf( -1 ) ) );
        assertFalse( TableListPart.isValidKey( valueOf( Integer.MIN_VALUE ) ) );
        
        assertTrue( TableListPart.isValidKey( valueOf( 1 ) ) );
        assertTrue( TableListPart.isValidKey( valueOf( 2 ) ) );
        assertTrue( TableListPart.isValidKey( valueOf( Integer.MAX_VALUE ) ) );
    }
    
    @Test
    public void testLength()
    {
        assertEquals( 0, _list.length() );
        
        _list.add( valueOf( "a" ) );
        assertEquals( 1, _list.length() );
        
        _list.add( valueOf( "bc" ) );
        _list.add( valueOf( "def" ) );
        _list.add( valueOf( "gh" ) );
        assertEquals( 4, _list.length() );
        
        _list.removeLast();
        assertEquals( 3, _list.length() );
        
        _list.insert( 2, valueOf( "ijkl" ) );
        assertEquals( 4, _list.length() );
        
        _list.remove( 1 );
        assertEquals( 3, _list.length() );
        
        _list.clear();
        assertEquals( 0, _list.length() );
    }
    
    @Test
    public void testContains()
    {
        assertFalse( _list.contains( valueOf( "foo" ) ) );
        assertFalse( _list.contains( 0 ) );
        assertFalse( _list.contains( ZERO ) );
        assertFalse( _list.contains( 1 ) );
        assertFalse( _list.contains( ONE ) );
        
        _list.add( valueOf( "bc" ) );
        assertTrue( _list.contains( 0 ) );
        assertTrue( _list.contains( ONE ) );
        
        _list.removeLast();
        assertFalse( _list.contains( 0 ) );
        assertFalse( _list.contains( ONE ) );
        
        _list.add( valueOf( "def" ) );
        _list.insert( 1, valueOf( "ijkl" ) );
        assertTrue( _list.contains( 1 ) );
        assertTrue( _list.contains( valueOf( 2 ) ) );
        
        _list.add( valueOf( "gh" ) );
        _list.remove( 1 );
        assertTrue( _list.contains( 1 ) );
        assertTrue( _list.contains( valueOf( 2 ) ) );
        assertFalse( _list.contains( 2 ) );
        assertFalse( _list.contains( valueOf( 3 ) ) );
        
        _list.clear();
        assertFalse( _list.contains( 0 ) );
        assertFalse( _list.contains( ZERO ) );
        assertFalse( _list.contains( 1 ) );
        assertFalse( _list.contains( ONE ) );
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
        
        _list.insert( 2, valueOf( "ijkl" ) );
        _list.add( valueOf( "mno" ) );
        assertEquals( valueOf( "mno" ), _list.get( 4 ) );
    }
    
    @Test( expected = ArrayIndexOutOfBoundsException.class )
    public void testInsert()
    {
        _list.insert( 0, valueOf( "a" ) );
        assertEquals( valueOf( "a" ), _list.get( 0 ) );
        
        _list.add( valueOf( "bc" ) );
        _list.add( valueOf( "def" ) );
        
        _list.insert( 2, valueOf( "ijkl" ) );
        assertEquals( valueOf( "ijkl" ), _list.get( 2 ) );
        
        _list.remove( 2 );
        _list.insert( 2, valueOf( "gh" ) );
        assertEquals( valueOf( "gh" ), _list.get( 2 ) );
        
        _list.insert( _list.length(), valueOf( "mno" ) );
        assertEquals( valueOf( "mno" ), _list.get( 4 ) );
        
        _list.insert( _list.length() + 1, valueOf( "pq" ) );
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
        
        assertEquals( 4, _list.length() );
        _list.set( 3, NIL );
        assertEquals( 3, _list.length() );
        
        _list.set( 1, NIL );
        assertEquals( 1, _list.length() );
        
        _list.set( 1, ONE );
    }
    
}
