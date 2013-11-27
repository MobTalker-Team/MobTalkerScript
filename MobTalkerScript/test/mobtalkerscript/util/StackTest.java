package mobtalkerscript.util;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

public class StackTest
{
    
    private Stack<Object> _stack;
    
    // ========================================
    
    @Before
    public void beforeTest()
    {
        _stack = new Stack<Object>();
    }
    
    // ========================================
    
    @Test
    public void testStack()
    {
        assertTrue( _stack.isEmpty() );
        assertEquals( 0, _stack.count() );
        
    }
    
    @Test
    public void testPush()
    {
        Object a = new Object();
        // [ A ]
        _stack.push( a );
        
        assertFalse( _stack.isEmpty() );
        assertEquals( 1, _stack.count() );
        assertSame( a, _stack.peek() );
        
        Object b = new Object();
        // [ A < B ]
        _stack.push( b );
        
        assertFalse( _stack.isEmpty() );
        assertEquals( 2, _stack.count() );
        assertSame( b, _stack.peek() );
        
        // [ A ]
        _stack.pop();
        
        Object c = new Object();
        // [ A < C ]
        _stack.push( c );
        
        assertFalse( _stack.isEmpty() );
        assertEquals( 2, _stack.count() );
        assertSame( c, _stack.peek() );
    }
    
    @Test
    public void testPop()
    {
        Object a = new Object();
        // [ A ]
        _stack.push( a );
        
        assertSame( a, _stack.pop() );
        assertTrue( _stack.isEmpty() );
        assertEquals( 0, _stack.count() );
        
        Object b = new Object();
        // [ A < B ]
        _stack.push( b );
        Object c = new Object();
        // [ A < B < C ]
        _stack.push( c );
        
        // [ A < B ]
        assertSame( c, _stack.pop() );
        assertFalse( _stack.isEmpty() );
        assertEquals( 1, _stack.count() );
        
        // [ A ]
        assertSame( b, _stack.pop() );
        assertTrue( _stack.isEmpty() );
        assertEquals( 0, _stack.count() );
    }
    
    @Test( expected = NoSuchElementException.class )
    public void testPopException()
    {
        _stack.pop();
    }
    
    @Test
    public void testPeek()
    {
        Object a = new Object();
        // [ A ]
        _stack.push( a );
        
        assertSame( a, _stack.peek() );
        
        Object b = new Object();
        // [ A < B ]
        _stack.push( b );
        
        assertSame( b, _stack.peek() );
        
        // [ A ]
        _stack.pop();
        
        assertSame( a, _stack.peek() );
        
        Object c = new Object();
        // [ A < C ]
        _stack.push( c );
        
        assertSame( c, _stack.peek() );
    }
    
    @Test( expected = NoSuchElementException.class )
    public void testPeekException()
    {
        _stack.peek();
    }
    
    @Test
    public void testCount()
    {
        assertEquals( 0, _stack.count() );
        
        Object a = new Object();
        // [ A ]
        _stack.push( a );
        
        assertEquals( 1, _stack.count() );
        
        Object b = new Object();
        // [ A < B ]
        _stack.push( b );
        
        assertEquals( 2, _stack.count() );
        
        // [ A ]
        _stack.pop();
        
        assertEquals( 1, _stack.count() );
        
        Object c = new Object();
        // [ A < C ]
        _stack.push( c );
        
        assertEquals( 2, _stack.count() );
        
        // [ A < C < B < B ]
        _stack.push( b );
        _stack.push( b );
        
        assertEquals( 4, _stack.count() );
        
        // []
        _stack.clear();
        
        assertEquals( 0, _stack.count() );
    }
    
}
