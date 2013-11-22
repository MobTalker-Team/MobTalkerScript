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
