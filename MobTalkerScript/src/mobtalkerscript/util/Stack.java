package mobtalkerscript.util;

import java.util.*;

public class Stack<T>
{
    public static <T> Stack<T> newStack()
    {
        return new Stack<T>();
    }
    
    // ========================================
    
    private T[] _array;
    private int _top;
    
    // ========================================
    
    @SuppressWarnings( "unchecked" )
    public Stack()
    {
        _array = (T[]) new Object[5];
        _top = -1;
    }
    
    // ========================================
    
    @SuppressWarnings( "unchecked" )
    private void checkArraySize()
    {
        if ( _array.length <= _top )
        {
            int newSize = _array.length * 2;
            
            T[] newArr = (T[]) new Object[newSize];
            
            System.arraycopy( _array, 0, newArr, 0, _array.length );
            _array = newArr;
        }
    }
    
    public void push( T o )
    {
        _top++;
        
        checkArraySize();
        
        _array[_top] = o;
    }
    
    public T pop()
    {
        if ( isEmpty() )
        {
            throw new NoSuchElementException( "Stack is empty" );
        }
        
        return _array[_top--];
    }
    
    public T peek()
    {
        if ( isEmpty() )
        {
            throw new NoSuchElementException( "Stack is empty" );
        }
        
        return _array[_top];
    }
    
    /**
     * A < B
     * 0 1
     * _top = 1
     * count() = 2
     * 
     * > swap( 2 );
     * swaps = 2 / 2 = 1
     * start = 1 + 1 - 2 = 0
     * B < A
     * 0 1
     * 
     * a = 0 + 0 = 0
     * b = 1 - 0 = 1
     * B <> A
     * 
     * 
     * A < B < C < D < E < F < G < H
     * 0 1 2 3 4 5 6 7
     * _top = 7
     * count() = 8
     * 
     * > swap( 4 );
     * swaps = 4 / 2 = 2
     * start = 7 + 1 - 4 = 4
     * A < B < C < D < H < G < F < E
     * 0 1 2 3 4 5 6 7
     * 
     * a = 4 + 0 = 4
     * b = 7 - 0 = 7
     * H <> E
     * a = 4 + 1 = 5
     * b = 7 - 1 = 6
     * G <> F
     * 
     * 
     * > swap( 5 );
     * swaps = 5 / 2 = 2
     * start = 7 + 1 - 5 = 3
     * A < B < C < H < G < F < E < D
     * 0 1 2 3 4 5 6 7
     * 
     * a = 3 + 0 = 3
     * b = 7 - 0 = 7
     * H <> D
     * a = 3 + 1 = 4
     * b = 7 - 1 = 6
     * G <> E
     */
    /**
     * Swaps the top <code>count</code> elements on the stack.
     */
    public void swap( int count )
    {
        if ( count < 2 )
        {
            throw new IllegalArgumentException( "Must swap at least 2 elements" );
        }
        if ( count > count() )
        {
            throw new IllegalArgumentException( "Tried to swap more elements than there are on the stack" );
        }
        
        final int swaps = count / 2;
        final int start = ( _top + 1 ) - count;
        for ( int i = 0; i < swaps; i++ )
        {
            int a = start + i;
            int b = _top - i;
            T tmp = _array[b];
            _array[b] = _array[a];
            _array[a] = tmp;
        }
    }
    
    // ========================================
    
    public int count()
    {
        return _top + 1;
    }
    
    public boolean isEmpty()
    {
        return _top == -1;
    }
    
    public void clear()
    {
        _top = -1;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder( "Stack [Count=" );
        s.append( count() ).append( "; " );
        
        if ( count() > 1 )
        {
            for ( int i = _top; i > 0; i-- )
            {
                T e = _array[i];
                s.append( e ).append( "," );
            }
        }
        
        if ( count() > 0 )
        {
            s.append( _array[0] );
        }
        
        s.append( "]" );
        
        return s.toString();
    }
    
}
