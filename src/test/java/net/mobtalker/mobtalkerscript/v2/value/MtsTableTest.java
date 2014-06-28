package mobtalkerscript.v2.value;

import static mobtalkerscript.v2.value.MtsValue.*;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

public class MtsTableTest
{
    private MtsTable _table;
    
    @Before
    public void before()
    {
        _table = new MtsTable( 0, 0 );
    }
    
    @Test
    public void testHashGetSet()
    {
        // Hash part
        _table.set( valueOf( "a" ), valueOf( "foo" ) );
        
        assertTrue( _table.containsKey( valueOf( "a" ) ) );
        assertEquals( 1, _table.count() );
        assertEquals( valueOf( "foo" ), _table.get( valueOf( "a" ) ) );
        assertEquals( valueOf( "foo" ), _table.get( "a" ) );
        
        _table.set( valueOf( "a" ), valueOf( "bar" ) );
        assertEquals( 1, _table.count() );
        assertEquals( valueOf( "bar" ), _table.get( valueOf( "a" ) ) );
        assertEquals( valueOf( "bar" ), _table.get( "a" ) );
        
        Random rnd = new Random();
        for ( int i = 0; i < 100; i++ )
        {
            _table.set( valueOf( "" + ( (char) i + 65 ) ), valueOf( rnd.nextInt() ) );
        }
        assertEquals( 101, _table.count() );
        for ( int i = 0; i < 100; i++ )
        {
            assertTrue( _table.containsKey( valueOf( "" + ( (char) i + 65 ) ) ) );
        }
        
        _table.set( valueOf( "b" ), valueOf( "lorom" ) );
        assertEquals( 102, _table.count() );
        assertTrue( _table.containsKey( valueOf( "b" ) ) );
        assertEquals( valueOf( "lorom" ), _table.get( valueOf( "b" ) ) );
        
        _table.set( valueOf( "b" ), NIL );
        assertEquals( 101, _table.count() );
        assertFalse( _table.containsKey( valueOf( "b" ) ) );
        assertEquals( NIL, _table.get( valueOf( "b" ) ) );
        _table.clear();
    }
    
    @Test
    public void testListGetSet()
    {
        // List part
        _table.set( ONE, valueOf( "a" ) );
        assertTrue( _table.containsKey( ONE ) );
        assertEquals( 1, _table.listSize() );
        
        _table.set( valueOf( 2 ), valueOf( "b" ) );
        assertTrue( _table.containsKey( valueOf( 2 ) ) );
        assertEquals( 2, _table.listSize() );
        
        _table.set( valueOf( 3 ), TRUE );
        _table.set( valueOf( 4 ), TRUE );
        _table.set( valueOf( 5 ), TRUE );
        _table.set( valueOf( 6 ), TRUE );
        assertEquals( 6, _table.listSize() );
        
        _table.set( valueOf( 6 ), NIL );
        assertFalse( _table.containsKey( valueOf( 6 ) ) );
        assertEquals( 5, _table.listSize() );
        
        _table.set( valueOf( 3 ), NIL );
        assertEquals( 2, _table.listSize() );
        assertTrue( _table.containsKey( valueOf( 5 ) ) );
        
        _table.set( valueOf( 3 ), TRUE );
        assertEquals( 5, _table.listSize() );
        assertTrue( _table.containsKey( valueOf( 5 ) ) );
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
