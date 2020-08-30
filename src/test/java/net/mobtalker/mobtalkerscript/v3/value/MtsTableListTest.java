/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
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
        
        _list.add( MtsString.of( "a" ) );
        assertEquals( 1, _list.size() );
        
        _list.add( MtsString.of( "bc" ) );
        _list.add( MtsString.of( "def" ) );
        _list.add( MtsString.of( "gh" ) );
        assertEquals( 4, _list.size() );
        
        _list.removeLast();
        assertEquals( 3, _list.size() );
        
        _list.add( 2, MtsString.of( "ijkl" ) );
        assertEquals( 4, _list.size() );
        
        _list.remove( 1 );
        assertEquals( 3, _list.size() );
        
        _list.clear();
        assertEquals( 0, _list.size() );
    }
    
    @Test
    public void testContains()
    {
        assertFalse( _list.contains( MtsString.of( "foo" ) ) );
        assertFalse( _list.canGetOrRemoveAt( 0 ) );
        assertFalse( _list.contains( MtsNumber.Zero ) );
        assertFalse( _list.canGetOrRemoveAt( 1 ) );
        assertFalse( _list.contains( MtsNumber.One ) );
        assertFalse( _list.canGetOrRemoveAt( MtsNumber.One ) );
        
        _list.add( MtsString.of( "bc" ) );
        assertTrue( _list.canGetOrRemoveAt( 0 ) );
        assertTrue( _list.canGetOrRemoveAt( MtsNumber.One ) );
        assertTrue( _list.contains( MtsString.of( "bc" ) ) );
        
        _list.removeLast();
        assertFalse( _list.canGetOrRemoveAt( 0 ) );
        assertFalse( _list.canGetOrRemoveAt( MtsNumber.One ) );
        assertFalse( _list.contains( MtsString.of( "bc" ) ) );
        
        _list.add( MtsString.of( "def" ) );
        _list.add( 1, MtsString.of( "ijkl" ) );
        assertTrue( _list.canGetOrRemoveAt( 1 ) );
        assertTrue( _list.canGetOrRemoveAt( MtsNumber.of( 2 ) ) );
        assertTrue( _list.contains( MtsString.of( "ijkl" ) ) );
        
        _list.add( MtsString.of( "gh" ) );
        _list.remove( 1 );
        assertTrue( _list.canGetOrRemoveAt( 1 ) );
        assertTrue( _list.canGetOrRemoveAt( MtsNumber.of( 2 ) ) );
        assertFalse( _list.canGetOrRemoveAt( 2 ) );
        assertFalse( _list.canGetOrRemoveAt( MtsNumber.of( 3 ) ) );
        assertTrue( _list.contains( MtsString.of( "gh" ) ) );
        assertFalse( _list.contains( MtsString.of( "ijkl" ) ) );
        
        _list.clear();
        assertFalse( _list.canGetOrRemoveAt( 0 ) );
        assertFalse( _list.canGetOrRemoveAt( MtsNumber.Zero ) );
        assertFalse( _list.canGetOrRemoveAt( 1 ) );
        assertFalse( _list.canGetOrRemoveAt( MtsNumber.One ) );
        assertFalse( _list.contains( MtsString.of( "gh" ) ) );
        assertFalse( _list.contains( MtsString.of( "def" ) ) );
        assertFalse( _list.contains( MtsString.of( "ijkl" ) ) );
        assertFalse( _list.contains( MtsString.of( "bc" ) ) );
    }
    
    @Test
    public void testAdd()
    {
        _list.add( MtsString.of( "a" ) );
        assertEquals( MtsString.of( "a" ), _list.get( 0 ) );
        
        _list.add( MtsString.of( "bc" ) );
        _list.add( MtsString.of( "def" ) );
        assertEquals( MtsString.of( "def" ), _list.get( 2 ) );
        
        _list.removeLast();
        _list.add( MtsString.of( "gh" ) );
        assertEquals( MtsString.of( "gh" ), _list.get( 2 ) );
        
        _list.add( 2, MtsString.of( "ijkl" ) );
        _list.add( MtsString.of( "mno" ) );
        assertEquals( MtsString.of( "mno" ), _list.get( 4 ) );
    }
    
    @Test( expected = ArrayIndexOutOfBoundsException.class )
    public void testInsert()
    {
        _list.add( 0, MtsString.of( "a" ) );
        assertEquals( MtsString.of( "a" ), _list.get( 0 ) );
        
        _list.add( MtsString.of( "bc" ) );
        _list.add( MtsString.of( "def" ) );
        
        _list.add( 2, MtsString.of( "ijkl" ) );
        assertEquals( MtsString.of( "ijkl" ), _list.get( 2 ) );
        
        _list.remove( 2 );
        _list.add( 2, MtsString.of( "gh" ) );
        assertEquals( MtsString.of( "gh" ), _list.get( 2 ) );
        
        _list.add( _list.size(), MtsString.of( "mno" ) );
        assertEquals( MtsString.of( "mno" ), _list.get( 4 ) );
        
        _list.add( _list.size() + 1, MtsString.of( "pq" ) );
    }
    
    @Test( expected = ArrayIndexOutOfBoundsException.class )
    public void testSet()
    {
        _list.add( MtsString.of( "a" ) );
        _list.add( MtsString.of( "bc" ) );
        _list.add( MtsString.of( "def" ) );
        _list.add( MtsString.of( "gh" ) );
        
        _list.set( 0, MtsString.of( "ijk" ) );
        assertEquals( MtsString.of( "ijk" ), _list.get( 0 ) );
        
        _list.set( 1, MtsString.of( "lm" ) );
        assertEquals( MtsString.of( "lm" ), _list.get( 1 ) );
        
        _list.set( 3, MtsString.of( "opq" ) );
        assertEquals( MtsString.of( "opq" ), _list.get( 3 ) );
        
        assertEquals( 4, _list.size() );
        _list.set( 3, Nil );
        assertEquals( 3, _list.size() );
        
        _list.set( 1, Nil );
        assertEquals( 1, _list.size() );
        
        _list.set( 1, MtsNumber.One );
    }
    
}
