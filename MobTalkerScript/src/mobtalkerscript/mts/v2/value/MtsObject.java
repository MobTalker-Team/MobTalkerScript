package mobtalkerscript.mts.v2.value;

public abstract class MtsObject<T> extends MtsValue
{
    protected final T _value;
    protected MtsTable _metatable;
    
    // ========================================
    
    protected MtsObject( T value )
    {
        _value = value;
    }
    
    // ========================================
    
    @Override
    public MtsString toStringMts()
    {
        return MtsValue.valueOf( toString() );
    }
    
    // ========================================
    
    @Override
    public boolean isObject()
    {
        return true;
    }
    
    @Override
    public MtsObject<T> asObject() throws ClassCastException
    {
        return this;
    }
    
    @Override
    public MtsType getType()
    {
        return MtsType.OBJECT;
    }
    
    @Override
    public MtsBoolean equalsMts( MtsValue x )
    {
        return x.isObject() ? valueOf( x.asObject().equals( _value ) ) : FALSE;
    }
    
    // ========================================
    
    public T toJava() throws ClassCastException
    {
        return _value;
    }
    
    @Override
    public int hashCode()
    {
        return _value.hashCode();
    }
    
    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof MtsObject )
        {
            Object other = ( (MtsObject<?>) obj ).toJava();
            return _value == other;
        }
        
        return false;
    }
    
}
