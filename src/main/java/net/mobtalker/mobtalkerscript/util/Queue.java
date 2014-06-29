package net.mobtalker.mobtalkerscript.util;

import java.util.*;

public class Queue<T>
{
    
    public static <T> Queue<T> newQueue()
    {
        return new Queue<T>();
    }
    
    public static <T> Queue<T> newQueue( Collection<T> c )
    {
        Queue<T> q = new Queue<T>( c.size() );
        
        for ( T o : c )
        {
            q.offer( o );
        }
        
        return q;
    }
    
    // ========================================
    
    private T[] _array;
    private int _head;
    private int _tail;
    
    // ========================================
    
    public Queue()
    {
        this( 10 );
    }
    
    @SuppressWarnings( "unchecked" )
    public Queue( int size )
    {
        _array = (T[]) new Object[size];
        _head = 0;
        _tail = 0;
    }
    
    // ========================================
    
    private int incrIndex( int i )
    {
        if ( ++i == _array.length )
        {
            i = 0;
        }
        
        return i;
    }
    
    // ========================================
    
    public void offer( T o )
    {
        _tail = incrIndex( _tail );
        
        if ( _tail == _head )
        {
            int newSize = _array.length * 2;
            
            @SuppressWarnings( "unchecked" )
            T[] newArr = (T[]) new Object[newSize];
            
            if ( _head <= _tail )
            {
                int len = _tail - _head;
                
                if ( len == 0 )
                {
                    System.arraycopy( _array, _head, newArr, 0, _array.length );
                    _head = 0;
                    _tail = _array.length;
                }
                else
                {
                    System.arraycopy( _array, _head, newArr, 0, len );
                    _head = 0;
                    _tail = len - 1;
                }
            }
            else
            {
                int len1 = _array.length - _head;
                int len2 = _tail + 1;
                
                System.arraycopy( _array, _head, newArr, 0, len1 );
                System.arraycopy( _array, 0, newArr, len1, len2 );
                
                _head = 0;
                _tail = ( len1 + len2 ) - 1;
            }
            
            _array = newArr;
        }
        
        _array[_tail] = o;
    }
    
    public T poll()
    {
        if ( isEmpty() ) { throw new NoSuchElementException( "Queue is empty" ); }
        
        _head = incrIndex( _head );
        
        return _array[_head];
    }
    
    public T peek()
    {
        if ( isEmpty() ) { throw new NoSuchElementException( "Queue is empty" ); }
        
        return _array[_head];
    }
    
    public boolean isEmpty()
    {
        return _head == _tail;
    }
    
    public void clear()
    {
        _head = 0;
        _tail = 0;
    }
    
}
