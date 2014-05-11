package mobtalkerscript.mts.v2.value;

public abstract class MtsObject extends MtsValue
{
    protected final Object _value;
    protected MtsTable _metatable;
    
    // ========================================
    
    protected MtsObject( Object value )
    {
        _value = value;
    }
    
    // ========================================
    
    @Override
    public MtsString toMtsString()
    {
        if ( !hasMetaTag( __TOSTRING ) )
            return super.toMtsString();
        
        return getMetaTag( __TOSTRING ).call( this ).get( 0 ).asString();
    }
    
    // ========================================
    
    @Override
    public boolean isObject()
    {
        return true;
    }
    
    @Override
    public MtsObject asObject()
    {
        return this;
    }
    
    @Override
    public MtsType getType()
    {
        return MtsType.OBJECT;
    }
    
    @Override
    public MtsBoolean isMtsEqual( MtsValue x )
    {
        return x.isObject() ? valueOf( x.asObject().equals( _value ) ) : FALSE;
    }
    
    public Object asJavaObject()
    {
        return _value;
    }
    
    // ========================================
    
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
            Object other = ( (MtsObject) obj ).asJavaObject();
            
            return _value == other;
        }
        
        return false;
    }
    
}
