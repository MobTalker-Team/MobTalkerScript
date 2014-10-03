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

import java.util.*;

import org.junit.*;

public class MtsTableTest
{
    private MtsTable t;
    
    @Before
    public void before()
    {
        t = new MtsTable( 0, 0 );
    }
    
    @Test
    public void testListReplace()
    {
        t.set( valueOf( 1 ), valueOf( "a" ), true );
        t.set( valueOf( 2 ), valueOf( "b" ), true );
        t.set( valueOf( 3 ), valueOf( "c" ), true );
        
        assertTrue( t.containsKey( valueOf( 1 ) ) );
        assertTrue( t.containsKey( valueOf( 2 ) ) );
        assertTrue( t.containsKey( valueOf( 3 ) ) );
        assertEquals( t.listSize(), 3 );
        assertEquals( t.tableSize(), 0 );
        
        t.set( valueOf( 1 ), valueOf( "D" ), true );
        t.set( valueOf( 2 ), valueOf( "E" ), true );
        
        assertTrue( t.containsKey( valueOf( 1 ) ) );
        assertTrue( t.containsKey( valueOf( 2 ) ) );
        assertTrue( t.containsKey( valueOf( 3 ) ) );
        assertEquals( t.listSize(), 3 );
        assertEquals( t.tableSize(), 0 );
        
        assertEquals( t.get( valueOf( 1 ), true ), valueOf( "D" ) );
        assertEquals( t.get( valueOf( 2 ), true ), valueOf( "E" ) );
        assertEquals( t.get( valueOf( 3 ), true ), valueOf( "c" ) );
    }
    
    @Test
    public void testHashGetSet()
    {
        // Hash part
        t.set( valueOf( "a" ), valueOf( "foo" ) );
        
        assertTrue( t.containsKey( valueOf( "a" ) ) );
        assertEquals( 1, t.count() );
        assertEquals( valueOf( "foo" ), t.get( valueOf( "a" ) ) );
        assertEquals( valueOf( "foo" ), t.get( "a" ) );
        
        t.set( valueOf( "a" ), valueOf( "bar" ) );
        assertEquals( 1, t.count() );
        assertEquals( valueOf( "bar" ), t.get( valueOf( "a" ) ) );
        assertEquals( valueOf( "bar" ), t.get( "a" ) );
        
        Random rnd = new Random();
        for ( int i = 0; i < 100; i++ )
        {
            t.set( valueOf( "" + ( (char) i + 65 ) ), valueOf( rnd.nextInt() ) );
        }
        assertEquals( 101, t.count() );
        for ( int i = 0; i < 100; i++ )
        {
            assertTrue( t.containsKey( valueOf( "" + ( (char) i + 65 ) ) ) );
        }
        
        t.set( valueOf( "b" ), valueOf( "lorom" ) );
        assertEquals( 102, t.count() );
        assertTrue( t.containsKey( valueOf( "b" ) ) );
        assertEquals( valueOf( "lorom" ), t.get( valueOf( "b" ) ) );
        
        t.set( valueOf( "b" ), NIL );
        assertEquals( 101, t.count() );
        assertFalse( t.containsKey( valueOf( "b" ) ) );
        assertEquals( NIL, t.get( valueOf( "b" ) ) );
        t.clear();
    }
    
    @Test
    public void testListGetSet()
    {
        // List part
        t.set( ONE, valueOf( "a" ) );
        assertTrue( t.containsKey( ONE ) );
        assertEquals( 1, t.listSize() );
        
        t.set( valueOf( 2 ), valueOf( "b" ) );
        assertTrue( t.containsKey( valueOf( 2 ) ) );
        assertEquals( 2, t.listSize() );
        
        t.set( valueOf( 3 ), TRUE );
        t.set( valueOf( 4 ), TRUE );
        t.set( valueOf( 5 ), TRUE );
        t.set( valueOf( 6 ), TRUE );
        assertEquals( 6, t.listSize() );
        
        t.set( valueOf( 6 ), NIL );
        assertFalse( t.containsKey( valueOf( 6 ) ) );
        assertEquals( 5, t.listSize() );
        
        t.set( valueOf( 3 ), NIL );
        assertEquals( 2, t.listSize() );
        assertTrue( t.containsKey( valueOf( 5 ) ) );
        
        t.set( valueOf( 3 ), TRUE );
        assertEquals( 5, t.listSize() );
        assertTrue( t.containsKey( valueOf( 5 ) ) );
    }
    
//    @Test
    public void testPerformance()
    {
        for ( int i = -0xFFFFF; i < 0x1FFFFF; i++ )
        {
            valueOf( i );
        }
        
        long start, elapsed, best;
        
        best = Long.MAX_VALUE;
        for ( int run = 0; run < 10; run++ )
        {
            HashMap<MtsValue, MtsValue> map = new HashMap<MtsValue, MtsValue>();
            
            start = System.nanoTime();
            for ( int i = 2; i < 0x1FFFFF; i++ )
            {
                map.put( valueOf( i ), valueOf( i ) );
            }
            elapsed = System.nanoTime() - start;
            best = elapsed < best ? elapsed : best;
        }
        System.out.println( "HashMap 1st runs: " + ( best / ( 1000 * 1000 ) ) + "ms" );
        
        best = Long.MAX_VALUE;
        for ( int run = 0; run < 10; run++ )
        {
            MtsTable table = new MtsTable( 8, 8 );
            start = System.nanoTime();
            for ( int i = 2; i < 0x1FFFFF; i++ )
            {
                table.set( valueOf( i ), valueOf( i ) );
            }
            elapsed = System.nanoTime() - start;
            best = elapsed < best ? elapsed : best;
        }
        System.out.println( "Table 1st runs: " + ( best / ( 1000 * 1000 ) ) + "ms" );
        
        // 2nd runs
        best = Long.MAX_VALUE;
        for ( int run = 0; run < 10; run++ )
        {
            HashMap<MtsValue, MtsValue> map = new HashMap<MtsValue, MtsValue>();
            
            start = System.nanoTime();
            for ( int i = -0xFFFFF; i < 0xFFFFF; i++ )
            {
                map.put( valueOf( i ), valueOf( i ) );
            }
            elapsed = System.nanoTime() - start;
            best = elapsed < best ? elapsed : best;
        }
        System.out.println( "HashMap 2st runs: " + ( best / ( 1000 * 1000 ) ) + "ms" );
        
        best = Long.MAX_VALUE;
        for ( int run = 0; run < 10; run++ )
        {
            MtsTable table = new MtsTable( 8, 8 );
            start = System.nanoTime();
            for ( int i = -0xFFFFF; i < 0xFFFFF; i++ )
            {
                table.set( valueOf( i ), valueOf( i ) );
            }
            elapsed = System.nanoTime() - start;
            best = elapsed < best ? elapsed : best;
        }
        System.out.println( "Table 2st runs: " + ( best / ( 1000 * 1000 ) ) + "ms" );
    }
}
