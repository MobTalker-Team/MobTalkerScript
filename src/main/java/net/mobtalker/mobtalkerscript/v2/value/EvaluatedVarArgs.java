package net.mobtalker.mobtalkerscript.v2.value;

import java.util.*;

import com.google.common.collect.*;

/* package */class EvaluatedVarArgs extends MtsVarArgs
{
    private final List<MtsValue> _values;
    
    // ========================================
    
    public EvaluatedVarArgs( MtsValue[] values )
    {
        this( Arrays.asList( values ) );
    }
    
    public EvaluatedVarArgs( List<MtsValue> values )
    {
        _values = values;
    }
    
    public EvaluatedVarArgs( MtsValue first, EvaluatedVarArgs rest )
    {
        ArrayList<MtsValue> values = Lists.newArrayListWithCapacity( rest.count() + 1 );
        values.add( first );
        values.addAll( rest._values );
        
        _values = values;
    }
    
    // ========================================
    
    @Override
    public EvaluatedVarArgs subArgs( int start, int end )
    {
        return new EvaluatedVarArgs( _values.subList( start, Math.min( end, _values.size() ) ) );
    }
    
    // ========================================
    
    /**
     * Returns the value at index <code>i</code> (zero based) or <code>nil</code>, if the index does not exist.
     */
    @Override
    public MtsValue get( int i )
    {
        if ( ( i < 0 ) || ( count() <= i ) )
            return NIL;
        
        return _values.get( i );
    }
    
    @Override
    public int count()
    {
        return _values.size();
    }
    
    @Override
    public boolean isEmpty()
    {
        return _values.isEmpty();
    }
    
    // ========================================
    
    @Override
    public MtsValue[] toArray()
    {
        return (MtsValue[]) _values.toArray();
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return super.toString() + _values.toString();
    }
}
