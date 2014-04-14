package mobtalkerscript.mts.v2.value;

import java.util.*;

import com.google.common.collect.*;

public class MtsVarArgs extends MtsValue
{
    private final List<MtsValue> _values;
    
    // ========================================
    
    public MtsVarArgs()
    {
        _values = Collections.<MtsValue> emptyList();
    }
    
    public MtsVarArgs( MtsValue value )
    {
        this( Collections.singletonList( value ) );
    }
    
    public MtsVarArgs( MtsValue first, MtsValue second )
    {
        this( Arrays.asList( first, second ) );
    }
    
    public MtsVarArgs( MtsValue[] values )
    {
        this( Arrays.asList( values ) );
    }
    
    public MtsVarArgs( List<MtsValue> values )
    {
        _values = values;
    }
    
    public MtsVarArgs( MtsValue first, List<MtsValue> rest )
    {
        ArrayList<MtsValue> values = Lists.newArrayListWithCapacity( rest.size() + 1 );
        values.add( first );
        values.addAll( rest );
        
        _values = values;
    }
    
    public MtsVarArgs( MtsValue first, MtsVarArgs rest )
    {
        ArrayList<MtsValue> values = Lists.newArrayListWithCapacity( rest.count() + 1 );
        values.add( first );
        values.addAll( rest._values );
        
        _values = values;
    }
    
    // ========================================
    
    /**
     * Returns the value at index <code>i</code> (zero based) or <code>nil</code>, if the index does not exist.
     */
    @Override
    public MtsValue get( int i )
    {
        return i < count() ? _values.get( i ) : NIL;
    }
    
    public int count()
    {
        return _values.size();
    }
    
    public boolean isEmpty()
    {
        return _values.isEmpty();
    }
    
    // ========================================
    
    @Override
    public boolean isVarArgs()
    {
        return true;
    }
    
    @Override
    public MtsVarArgs asVarArgs()
    {
        return this;
    }
    
    @Override
    public MtsType getType()
    {
        return MtsType.VARARGS;
    }
    
    // ========================================
    
    @Override
    public int hashCode()
    {
        return _values.hashCode();
    }
    
    @Override
    public String toString()
    {
        return getType() + _values.toString();
    }
}
