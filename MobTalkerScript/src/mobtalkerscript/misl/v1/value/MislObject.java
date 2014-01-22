package mobtalkerscript.misl.v1.value;

public abstract class MislObject<T> extends MislValue
{
    protected final T _value;
    protected MislTable _metatable;
    
    // ========================================
    
    protected MislObject( T value )
    {
        _value = value;
    }
    
    // ========================================
    
    @Override
    public boolean hasMetaTag( MislString tag )
    {
        return ( _metatable != null ) && _metatable.containsKey( tag );
    }
    
    // ========================================
    
    @Override
    public MislString toStringMts()
    {
        return MislValue.valueOf( toString() );
    }
    
    // ========================================
    
    @Override
    public boolean isObject()
    {
        return true;
    }
    
    @Override
    public MislObject<T> asObject() throws ClassCastException
    {
        return this;
    }
    
    @Override
    public String getTypeName()
    {
        return TYPENAME_JOBJECT;
    }
    
    @Override
    public MislBoolean equalsMts( MislValue x )
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
        if ( obj instanceof MislObject )
        {
            Object other = ( (MislObject<?>) obj ).toJava();
            return _value == other;
        }
        
        return false;
    }
    
}
