package net.mobtalker.mobtalkerscript.v2.value;

public class MtsUserdata extends MtsValue
{
    protected final Object _value;
    
    // ========================================
    
    protected MtsUserdata( Object value )
    {
        _value = value;
    }
    
    // ========================================
    
    @Override
    public MtsString toMtsString()
    {
        if ( !hasMetaTag( __TOSTRING ) )
            return super.toMtsString();
        
        return getMetaTag( __TOSTRING ).call( this ).asString();
    }
    
    // ========================================
    
    @Override
    public boolean isUserdata()
    {
        return true;
    }
    
    @Override
    public MtsUserdata asUserdata()
    {
        return this;
    }
    
    @Override
    public MtsType getType()
    {
        return MtsType.USERDATA;
    }
    
    public Object asJavaObject()
    {
        return _value;
    }
    
    @SuppressWarnings( "unchecked" )
    @Override
    public <T> T asNative() throws ClassCastException
    {
        return (T) _value;
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
        if ( obj instanceof MtsUserdata )
        {
            Object other = ( (MtsUserdata) obj ).asJavaObject();
            
            return _value == other;
        }
        
        return false;
    }
    
}
