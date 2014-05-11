package mobtalkerscript.mts.v2.value;

import java.util.*;

import com.google.common.collect.*;

public class MtsVarArgs extends MtsValue
{
    private final List<MtsValue> _values;
    
    // ========================================
    
    public MtsVarArgs( MtsValue value )
    {
        this( Collections.singletonList( value ) );
    }
    
    public MtsVarArgs( MtsValue e1, MtsValue e2 )
    {
        this( ImmutableList.of( e1, e2 ) );
    }
    
    public MtsVarArgs( MtsValue e1, MtsValue e2, MtsValue e3 )
    {
        this( ImmutableList.of( e1, e2, e3 ) );
    }
    
    public MtsVarArgs( MtsValue e1, MtsValue e2, MtsValue e3, MtsValue e4 )
    {
        this( ImmutableList.of( e1, e2, e3, e4 ) );
    }
    
    public MtsVarArgs( MtsValue e1, MtsValue e2, MtsValue e3, MtsValue e4, MtsValue e5 )
    {
        this( ImmutableList.of( e1, e2, e3, e4, e5 ) );
    }
    
    public MtsVarArgs( MtsValue e1, MtsValue e2, MtsValue e3, MtsValue e4, MtsValue e5, MtsValue e6 )
    {
        this( ImmutableList.of( e1, e2, e3, e4, e5, e6 ) );
    }
    
    public MtsVarArgs( MtsValue[] values )
    {
        this( Arrays.asList( values ) );
    }
    
    public MtsVarArgs( List<MtsValue> values )
    {
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
    
    public MtsVarArgs subArgs( int start, int end )
    {
        return new MtsVarArgs( _values.subList( start, Math.min( end, _values.size() ) ) );
    }
    
    public MtsVarArgs subArgs( int start )
    {
        return subArgs( start, _values.size() );
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
    
    public MtsValue[] toArray()
    {
        return (MtsValue[]) _values.toArray();
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
