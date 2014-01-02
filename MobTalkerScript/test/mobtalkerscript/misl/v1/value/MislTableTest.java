package mobtalkerscript.misl.v1.value;

import static mobtalkerscript.misl.v1.value.MislValue.*;
import static org.junit.Assert.*;

import org.junit.*;

public class MislTableTest
{
    
    private MislTable _table;
    
    @Before
    public void before()
    {
        _table = new MislTable( 10 );
    }
    
    @Test
    public void testIsValidArrayKey()
    {
        assertFalse( MislTable.isValidArrayKey( TRUE ) );
        assertFalse( MislTable.isValidArrayKey( FALSE ) );
        assertFalse( MislTable.isValidArrayKey( NIL ) );
        
        assertFalse( MislTable.isValidArrayKey( valueOf( 0.1 ) ) );
        assertFalse( MislTable.isValidArrayKey( valueOf( 1.34 ) ) );
        
        assertFalse( MislTable.isValidArrayKey( valueOf( -1 ) ) );
        assertFalse( MislTable.isValidArrayKey( valueOf( Integer.MIN_VALUE ) ) );
        
        assertTrue( MislTable.isValidArrayKey( valueOf( 0 ) ) );
        assertTrue( MislTable.isValidArrayKey( valueOf( 1 ) ) );
        assertTrue( MislTable.isValidArrayKey( valueOf( Integer.MAX_VALUE ) ) );
    }
    
    @Test
    public void testCount()
    {
        fail( "Not yet implemented" );
    }
    
    @Test
    public void testListSize()
    {
        fail( "Not yet implemented" );
    }
    
    @Test
    public void testIsEmpty()
    {
        assertTrue( _table.isEmpty() );
        
//        _table.rawset( valueOf( "x" ), valueOf( 1 ) );
//        
//        assertFalse( _table.isEmpty() );
    }
    
    @Test
    public void testContainsKey()
    {
        fail( "Not yet implemented" );
    }
    
    @Test
    public void testGetFirstEntry()
    {
        fail( "Not yet implemented" );
    }
    
    @Test
    public void testGetEntryAfter()
    {
        fail( "Not yet implemented" );
    }
    
    @Test
    public void testRawget()
    {
        fail( "Not yet implemented" );
    }
    
    @Test
    public void testRawset()
    {
        _table.rawset( valueOf( 0 ), valueOf( "a" ) );
        _table.rawset( valueOf( 2 ), valueOf( "b" ) );
        _table.rawset( valueOf( 1 ), valueOf( "c" ) );
    }
    
}
