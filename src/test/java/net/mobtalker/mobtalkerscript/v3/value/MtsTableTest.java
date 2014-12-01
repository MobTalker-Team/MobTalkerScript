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
        assertEquals( t.list().size(), 3 );
        assertEquals( t.map().size(), 0 );
        
        t.set( valueOf( 1 ), valueOf( "D" ), true );
        t.set( valueOf( 2 ), valueOf( "E" ), true );
        
        assertTrue( t.containsKey( valueOf( 1 ) ) );
        assertTrue( t.containsKey( valueOf( 2 ) ) );
        assertTrue( t.containsKey( valueOf( 3 ) ) );
        assertEquals( t.list().size(), 3 );
        assertEquals( t.map().size(), 0 );
        
        assertEquals( valueOf( "D" ), t.get( valueOf( 1 ), true ) );
        assertEquals( valueOf( "E" ), t.get( valueOf( 2 ), true ) );
        assertEquals( valueOf( "c" ), t.get( valueOf( 3 ), true ) );
    }
    
    @Test
    public void testHashGetSet()
    {
        // Hash part
        t.set( valueOf( "a" ), valueOf( "foo" ) );
        
        assertTrue( t.containsKey( valueOf( "a" ) ) );
        assertEquals( 1, t.size() );
        assertEquals( valueOf( "foo" ), t.get( valueOf( "a" ) ) );
        assertEquals( valueOf( "foo" ), t.get( "a" ) );
        
        t.set( valueOf( "a" ), valueOf( "bar" ) );
        assertEquals( 1, t.size() );
        assertEquals( valueOf( "bar" ), t.get( valueOf( "a" ) ) );
        assertEquals( valueOf( "bar" ), t.get( "a" ) );
        
        Random rnd = new Random();
        for ( int i = 0; i < 100; i++ )
        {
            t.set( valueOf( "" + ( (char) i + 65 ) ), valueOf( rnd.nextInt() ) );
        }
        assertEquals( 101, t.size() );
        for ( int i = 0; i < 100; i++ )
        {
            assertTrue( t.containsKey( valueOf( "" + ( (char) i + 65 ) ) ) );
        }
        
        t.set( valueOf( "b" ), valueOf( "lorom" ) );
        assertEquals( 102, t.size() );
        assertTrue( t.containsKey( valueOf( "b" ) ) );
        assertEquals( valueOf( "lorom" ), t.get( valueOf( "b" ) ) );
        
        t.set( valueOf( "b" ), NIL );
        assertEquals( 101, t.size() );
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
        assertEquals( 1, t.list().size() );
        
        t.set( valueOf( 2 ), valueOf( "b" ) );
        assertTrue( t.containsKey( valueOf( 2 ) ) );
        assertEquals( 2, t.list().size() );
        
        t.set( valueOf( 3 ), TRUE );
        t.set( valueOf( 4 ), TRUE );
        t.set( valueOf( 5 ), TRUE );
        t.set( valueOf( 6 ), TRUE );
        assertEquals( 6, t.list().size() );
        
        t.set( valueOf( 6 ), NIL );
        assertFalse( t.containsKey( valueOf( 6 ) ) );
        assertEquals( 5, t.list().size() );
        
        t.set( valueOf( 3 ), NIL );
        assertEquals( 2, t.list().size() );
        assertTrue( t.containsKey( valueOf( 5 ) ) );
        
        t.set( valueOf( 3 ), TRUE );
        assertEquals( 5, t.list().size() );
        assertTrue( t.containsKey( valueOf( 5 ) ) );
    }
    
    @Test
    public void testPerformance()
    {
        for ( int i = 0; i < 0xFFFFF; i++ )
        {
            valueOf( i );
        }
        
        Random random;
        long start, elapsed, best, worst;
        
        random = new Random( 25051988 );
        for ( int run = 0; run < 50; run++ )
        {
            HashMap<MtsValue, MtsValue> map = new HashMap<MtsValue, MtsValue>();
            
            for ( int i = 0; i < 0xFFFFF; i++ )
            {
                int x = random.nextInt( 0xFFFF );
                map.put( valueOf( x ), valueOf( x ) );
            }
            for ( int i = 0; i < 0xFFFFF; i++ )
            {
                int x = random.nextInt( 0xFFFF );
                map.remove( valueOf( x ) );
            }
            for ( int i = 0; i < 0xFFFFF; i++ )
            {
                int x = random.nextInt( 0xFFFF );
                map.put( valueOf( x ), valueOf( x ) );
            }
        }
        
        best = Long.MAX_VALUE;
        worst = Long.MIN_VALUE;
        random = new Random( 25051988 );
        for ( int run = 0; run < 50; run++ )
        {
            HashMap<MtsValue, MtsValue> map = new HashMap<MtsValue, MtsValue>();
            
            start = System.nanoTime();
            for ( int i = 0; i < 0xFFFFF; i++ )
            {
                int x = random.nextInt( 0xFFFF );
                map.put( valueOf( x ), valueOf( x ) );
            }
            for ( int i = 0; i < 0xFFFFF; i++ )
            {
                int x = random.nextInt( 0xFFFF );
                map.remove( valueOf( x ) );
            }
            for ( int i = 0; i < 0xFFFFF; i++ )
            {
                int x = random.nextInt( 0xFFFF );
                map.put( valueOf( x ), valueOf( x ) );
            }
            elapsed = System.nanoTime() - start;
            best = elapsed < best ? elapsed : best;
            worst = elapsed > worst ? elapsed : worst;
        }
        System.out.println( "HashMap: " + ( best / ( 1000 * 1000 ) ) + "ms - " + ( worst / ( 1000 * 1000 ) ) + "ms" );
        
        random = new Random( 25051988 );
        for ( int run = 0; run < 50; run++ )
        {
            MtsTable table = new MtsTable( 8, 8 );
            start = System.nanoTime();
            for ( int i = 0; i < 0xFFFFF; i++ )
            {
                int x = random.nextInt( 0xFFFF );
                table.set( valueOf( x ), valueOf( x ) );
            }
            for ( int i = 0; i < 0xFFFFF; i++ )
            {
                int x = random.nextInt( 0xFFFF );
                table.set( valueOf( x ), NIL );
            }
            for ( int i = 0; i < 0xFFFFF; i++ )
            {
                int x = random.nextInt( 0xFFFF );
                table.set( valueOf( x ), valueOf( x ) );
            }
        }
        
        best = Long.MAX_VALUE;
        worst = Long.MIN_VALUE;
        random = new Random( 25051988 );
        for ( int run = 0; run < 50; run++ )
        {
            MtsTable table = new MtsTable( 8, 8 );
            
            start = System.nanoTime();
            for ( int i = 0; i < 0xFFFFF; i++ )
            {
                int x = random.nextInt( 0xFFFF );
                table.set( valueOf( x ), valueOf( x ) );
            }
            for ( int i = 0; i < 0xFFFFF; i++ )
            {
                int x = random.nextInt( 0xFFFF );
                table.set( valueOf( x ), NIL );
            }
            for ( int i = 0; i < 0xFFFFF; i++ )
            {
                int x = random.nextInt( 0xFFFF );
                table.set( valueOf( x ), valueOf( x ) );
            }
            elapsed = System.nanoTime() - start;
            best = elapsed < best ? elapsed : best;
            worst = elapsed > worst ? elapsed : worst;
        }
        System.out.println( "Table: " + ( best / ( 1000 * 1000 ) ) + "ms - " + ( worst / ( 1000 * 1000 ) ) + "ms" );
    }
}
