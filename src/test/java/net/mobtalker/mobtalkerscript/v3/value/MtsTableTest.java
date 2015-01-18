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
        t.set( MtsNumber.of( 1 ), MtsString.of( "a" ), true );
        t.set( MtsNumber.of( 2 ), MtsString.of( "b" ), true );
        t.set( MtsNumber.of( 3 ), MtsString.of( "c" ), true );
        
        assertTrue( t.containsKey( MtsNumber.of( 1 ) ) );
        assertTrue( t.containsKey( MtsNumber.of( 2 ) ) );
        assertTrue( t.containsKey( MtsNumber.of( 3 ) ) );
        assertEquals( t.list().size(), 3 );
        assertEquals( t.map().size(), 0 );
        
        t.set( MtsNumber.of( 1 ), MtsString.of( "D" ), true );
        t.set( MtsNumber.of( 2 ), MtsString.of( "E" ), true );
        
        assertTrue( t.containsKey( MtsNumber.of( 1 ) ) );
        assertTrue( t.containsKey( MtsNumber.of( 2 ) ) );
        assertTrue( t.containsKey( MtsNumber.of( 3 ) ) );
        assertEquals( t.list().size(), 3 );
        assertEquals( t.map().size(), 0 );
        
        assertEquals( MtsString.of( "D" ), t.get( MtsNumber.of( 1 ), true ) );
        assertEquals( MtsString.of( "E" ), t.get( MtsNumber.of( 2 ), true ) );
        assertEquals( MtsString.of( "c" ), t.get( MtsNumber.of( 3 ), true ) );
    }
    
    @Test
    public void testHashGetSet()
    {
        // Hash part
        t.set( MtsString.of( "a" ), MtsString.of( "foo" ) );
        
        assertTrue( t.containsKey( MtsString.of( "a" ) ) );
        assertEquals( 1, t.size() );
        assertEquals( MtsString.of( "foo" ), t.get( MtsString.of( "a" ) ) );
        assertEquals( MtsString.of( "foo" ), t.get( "a" ) );
        
        t.set( MtsString.of( "a" ), MtsString.of( "bar" ) );
        assertEquals( 1, t.size() );
        assertEquals( MtsString.of( "bar" ), t.get( MtsString.of( "a" ) ) );
        assertEquals( MtsString.of( "bar" ), t.get( "a" ) );
        
        Random rnd = new Random();
        for ( int i = 0; i < 100; i++ )
        {
            t.set( MtsString.of( "" + ( (char) i + 65 ) ), MtsNumber.of( rnd.nextInt() ) );
        }
        assertEquals( 101, t.size() );
        for ( int i = 0; i < 100; i++ )
        {
            assertTrue( t.containsKey( MtsString.of( "" + ( (char) i + 65 ) ) ) );
        }
        
        t.set( MtsString.of( "b" ), MtsString.of( "lorom" ) );
        assertEquals( 102, t.size() );
        assertTrue( t.containsKey( MtsString.of( "b" ) ) );
        assertEquals( MtsString.of( "lorom" ), t.get( MtsString.of( "b" ) ) );
        
        t.set( MtsString.of( "b" ), Nil );
        assertEquals( 101, t.size() );
        assertFalse( t.containsKey( MtsString.of( "b" ) ) );
        assertEquals( Nil, t.get( MtsString.of( "b" ) ) );
        t.clear();
    }
    
    @Test
    public void testListGetSet()
    {
        // List part
        t.set( MtsNumber.One, MtsString.of( "a" ) );
        assertTrue( t.containsKey( MtsNumber.One ) );
        assertEquals( 1, t.list().size() );
        
        t.set( MtsNumber.of( 2 ), MtsString.of( "b" ) );
        assertTrue( t.containsKey( MtsNumber.of( 2 ) ) );
        assertEquals( 2, t.list().size() );
        
        t.set( MtsNumber.of( 3 ), MtsBoolean.True );
        t.set( MtsNumber.of( 4 ), MtsBoolean.True );
        t.set( MtsNumber.of( 5 ), MtsBoolean.True );
        t.set( MtsNumber.of( 6 ), MtsBoolean.True );
        assertEquals( 6, t.list().size() );
        
        t.set( MtsNumber.of( 6 ), Nil );
        assertFalse( t.containsKey( MtsNumber.of( 6 ) ) );
        assertEquals( 5, t.list().size() );
        
        t.set( MtsNumber.of( 3 ), Nil );
        assertEquals( 2, t.list().size() );
        assertTrue( t.containsKey( MtsNumber.of( 5 ) ) );
        
        t.set( MtsNumber.of( 3 ), MtsBoolean.True );
        assertEquals( 5, t.list().size() );
        assertTrue( t.containsKey( MtsNumber.of( 5 ) ) );
    }
    
//    @Test
    public void testPerformance()
    {
        for ( int i = 0; i < 0xFFFFF; i++ )
        {
            MtsNumber.of( i );
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
                map.put( MtsNumber.of( x ), MtsNumber.of( x ) );
            }
            for ( int i = 0; i < 0xFFFFF; i++ )
            {
                int x = random.nextInt( 0xFFFF );
                map.remove( MtsNumber.of( x ) );
            }
            for ( int i = 0; i < 0xFFFFF; i++ )
            {
                int x = random.nextInt( 0xFFFF );
                map.put( MtsNumber.of( x ), MtsNumber.of( x ) );
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
                map.put( MtsNumber.of( x ), MtsNumber.of( x ) );
            }
            for ( int i = 0; i < 0xFFFFF; i++ )
            {
                int x = random.nextInt( 0xFFFF );
                map.remove( MtsNumber.of( x ) );
            }
            for ( int i = 0; i < 0xFFFFF; i++ )
            {
                int x = random.nextInt( 0xFFFF );
                map.put( MtsNumber.of( x ), MtsNumber.of( x ) );
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
                table.set( MtsNumber.of( x ), MtsNumber.of( x ) );
            }
            for ( int i = 0; i < 0xFFFFF; i++ )
            {
                int x = random.nextInt( 0xFFFF );
                table.set( MtsNumber.of( x ), Nil );
            }
            for ( int i = 0; i < 0xFFFFF; i++ )
            {
                int x = random.nextInt( 0xFFFF );
                table.set( MtsNumber.of( x ), MtsNumber.of( x ) );
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
                table.set( MtsNumber.of( x ), MtsNumber.of( x ) );
            }
            for ( int i = 0; i < 0xFFFFF; i++ )
            {
                int x = random.nextInt( 0xFFFF );
                table.set( MtsNumber.of( x ), Nil );
            }
            for ( int i = 0; i < 0xFFFFF; i++ )
            {
                int x = random.nextInt( 0xFFFF );
                table.set( MtsNumber.of( x ), MtsNumber.of( x ) );
            }
            elapsed = System.nanoTime() - start;
            best = elapsed < best ? elapsed : best;
            worst = elapsed > worst ? elapsed : worst;
        }
        System.out.println( "Table: " + ( best / ( 1000 * 1000 ) ) + "ms - " + ( worst / ( 1000 * 1000 ) ) + "ms" );
    }
}
