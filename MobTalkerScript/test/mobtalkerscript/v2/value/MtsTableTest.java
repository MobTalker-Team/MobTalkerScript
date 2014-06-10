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
    public void testCount()
    {
        assertEquals( 0, _table.count() );
        
        _table.set( valueOf( 0 ), valueOf( "a" ) );
        assertEquals( 1, _table.count() );
        
        _table.set( valueOf( 3 ), valueOf( "b" ) );
        assertEquals( 2, _table.count() );
        
        _table.set( valueOf( 0.1 ), valueOf( "d" ) );
        assertEquals( 3, _table.count() );
        
        _table.set( valueOf( "foo" ), valueOf( "e" ) );
        assertEquals( 4, _table.count() );
        
        _table.set( valueOf( 1 ), valueOf( "c" ) );
        assertEquals( 5, _table.count() );
        
        _table.set( valueOf( 1 ), NIL );
        assertEquals( 4, _table.count() );
        
        _table.set( valueOf( 0.1 ), NIL );
        assertEquals( 3, _table.count() );
    }
    
    @Test
    public void testListSize()
    {
        assertEquals( 0, _table.listSize() );
        
        _table.set( valueOf( 0 ), valueOf( "a" ) );
        assertEquals( 0, _table.listSize() );
        
        _table.set( valueOf( 1 ), valueOf( "a" ) );
        assertEquals( 1, _table.listSize() );
        
        _table.set( valueOf( 3 ), valueOf( "b" ) );
        assertEquals( 1, _table.listSize() );
        
        _table.set( valueOf( 0.1 ), valueOf( "d" ) );
        assertEquals( 1, _table.listSize() );
        
        _table.set( valueOf( "foo" ), valueOf( "e" ) );
        assertEquals( 1, _table.listSize() );
        
        _table.set( valueOf( 2 ), valueOf( "c" ) );
        assertEquals( 3, _table.listSize() );
        
        _table.set( valueOf( 2 ), NIL );
        assertEquals( 1, _table.listSize() );
        
        _table.set( valueOf( 0.1 ), NIL );
        assertEquals( 1, _table.listSize() );
    }
    
    @Test
    public void testIsEmpty()
    {
        assertTrue( _table.isEmpty() );
        
        _table.set( valueOf( "x" ), valueOf( 1 ) );
        
        assertFalse( _table.isEmpty() );
    }
    
    @Test
    public void testContainsKey()
    {
        assertFalse( _table.containsKey( valueOf( 2 ) ) );
        assertFalse( _table.containsKey( valueOf( "foo" ) ) );
        assertFalse( _table.containsKey( valueOf( 0.1 ) ) );
        
        _table.set( valueOf( 1 ), valueOf( "a" ) );
        _table.set( valueOf( 3 ), valueOf( "b" ) );
        _table.set( valueOf( 2 ), valueOf( "c" ) );
        
        _table.set( valueOf( 0.1 ), valueOf( "d" ) );
        _table.set( valueOf( "foo" ), valueOf( "e" ) );
        
        assertTrue( _table.containsKey( valueOf( 2 ) ) );
        assertTrue( _table.containsKey( valueOf( "foo" ) ) );
        assertTrue( _table.containsKey( valueOf( 0.1 ) ) );
    }
    
    @Test
    public void testGetFirstEntry()
    {
        assertNull( _table.getFirstEntry() );
        
        _table.set( valueOf( "foo" ), valueOf( "e" ) );
        assertEquals( valueOf( "e" ), _table.getFirstEntry().getValue() );
        
        _table.set( valueOf( 1 ), valueOf( "a" ) );
        assertEquals( valueOf( "a" ), _table.getFirstEntry().getValue() );
        
        _table.set( valueOf( 0 ), valueOf( "d" ) );
        assertEquals( valueOf( "a" ), _table.getFirstEntry().getValue() );
        
        _table.set( valueOf( 2 ), valueOf( "b" ) );
        assertEquals( valueOf( "a" ), _table.getFirstEntry().getValue() );
        
        _table.set( valueOf( 1 ), valueOf( "c" ) );
        assertEquals( valueOf( "c" ), _table.getFirstEntry().getValue() );
        
        _table.remove( valueOf( 1 ) );
        assertEquals( valueOf( "b" ), _table.getFirstEntry().getValue() );
    }
    
    @Test
    public void testGetEntryAfter()
    {
        assertNull( _table.getNext( NIL ) );
        
        _table.set( valueOf( 0 ), valueOf( "a" ) );
        assertEquals( valueOf( 0 ), _table.getNext( NIL ).getKey() );
        assertNull( _table.getNext( valueOf( 0 ) ) );
        
        _table.set( valueOf( 2 ), valueOf( "b" ) );
        _table.set( valueOf( 1 ), valueOf( "c" ) );
        _table.set( valueOf( 0 ), valueOf( "e" ) );
        
        assertEquals( valueOf( 1 ), _table.getNext( NIL ).getKey() );
        assertEquals( valueOf( 2 ), _table.getNext( valueOf( 1 ) ).getKey() );
        assertNotNull( _table.getNext( valueOf( 2 ) ) );
        
        _table.set( valueOf( 0.1 ), valueOf( "e" ) );
        _table.set( valueOf( "foo" ), valueOf( "f" ) );
        _table.set( valueOf( "foo" ), valueOf( "g" ) );
        
        assertEquals( valueOf( 1 ), _table.getNext( NIL ).getKey() );
        assertEquals( valueOf( 2 ), _table.getNext( valueOf( 1 ) ).getKey() );
        assertNotNull( _table.getNext( valueOf( 2 ) ) );
    }
    
    @Test
    public void testset()
    {
        _table.set( valueOf( 0 ), valueOf( "a" ) );
        _table.set( valueOf( 2 ), valueOf( "b" ) );
        _table.set( valueOf( 1 ), valueOf( "c" ) );
        _table.set( valueOf( 0 ), valueOf( "e" ) );
        
        _table.set( valueOf( 0.1 ), valueOf( "e" ) );
        _table.set( valueOf( "foo" ), valueOf( "f" ) );
        _table.set( valueOf( "foo" ), valueOf( "g" ) );
        
        assertEquals( valueOf( "e" ), _table.get( valueOf( 0 ) ) );
        assertEquals( valueOf( "g" ), _table.get( valueOf( "foo" ) ) );
    }
    
    @Test
    public void testget()
    {
        assertEquals( NIL, _table.get( valueOf( 2 ) ) );
        assertEquals( NIL, _table.get( valueOf( "foo" ) ) );
        assertEquals( NIL, _table.get( valueOf( 0.1 ) ) );
        
        _table.set( valueOf( 0 ), valueOf( "a" ) );
        _table.set( valueOf( 2 ), valueOf( "b" ) );
        _table.set( valueOf( 1 ), valueOf( "c" ) );
        
        _table.set( valueOf( 0.1 ), valueOf( "d" ) );
        _table.set( valueOf( "foo" ), valueOf( "e" ) );
        
        assertEquals( valueOf( "b" ), _table.get( valueOf( 2 ) ) );
        assertEquals( valueOf( "e" ), _table.get( valueOf( "foo" ) ) );
        assertEquals( valueOf( "d" ), _table.get( valueOf( 0.1 ) ) );
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
