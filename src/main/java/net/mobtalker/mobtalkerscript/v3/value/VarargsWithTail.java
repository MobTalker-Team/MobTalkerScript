package net.mobtalker.mobtalkerscript.v3.value;

import java.util.Iterator;

/* package */class VarargsWithTail extends MtsVarargs
{
    private final MtsValue[] _values;
    private final MtsVarargs _tail;
    
    // ========================================
    
    public VarargsWithTail( MtsValue[] values, MtsVarargs tail )
    {
        _values = values;
        _tail = tail;
    }
    
    // ========================================
    
    @Override
    public int count()
    {
        return _values.length + _tail.count();
    }
    
    @Override
    public boolean isEmpty()
    {
        return ( _values.length == 0 ) && _tail.isEmpty();
    }
    
    @Override
    public MtsValue get( int i )
    {
        return i < _values.length ? _values[i] : _tail.get( i - _values.length );
    }
    
    @Override
    public MtsVarargs subArgs( int start, int end )
    {
        if ( count() < start )
            return EMPTY_VARARGS;
        
        end = Math.min( end, count() );
        if ( end <= start )
            return EMPTY_VARARGS;
        
        // TODO A bit naive approach. This could be optimized just a tiny bit.
        
        MtsValue[] values = new MtsValue[end - start];
        for ( int i = 0; i < values.length; ++i )
        {
            values[i] = get( start + i );
        }
        
        return MtsVarargs.of( values );
    }
    
    @Override
    public MtsValue[] toArray()
    {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Iterator<MtsValue> iterator()
    {
        throw new UnsupportedOperationException();
    }
    
}
