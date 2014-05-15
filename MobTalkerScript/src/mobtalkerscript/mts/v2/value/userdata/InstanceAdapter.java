package mobtalkerscript.mts.v2.value.userdata;

import mobtalkerscript.mts.v2.value.*;

public class InstanceAdapter extends MtsObject
{
    public static InstanceAdapter forInstance( Object instance )
    {
        ClassAdapter classAdapter = ClassAdapter.forClass( instance.getClass() );
        return new InstanceAdapter( classAdapter, instance );
    }
    
    // ========================================
    
    private final ClassAdapter _classAdapter;
    
    // ========================================
    
    private InstanceAdapter( ClassAdapter classAdapter, Object value )
    {
        super( value );
        _classAdapter = classAdapter;
    }
    
    // ========================================
    
    @Override
    public MtsValue get( MtsValue key, boolean useMetaTag )
    {
        MtsValue method = _classAdapter.getMethod( key.asString().asJavaString() );
        
        if ( !useMetaTag || ( method == null ) )
            return super.get( key, useMetaTag );
        
        return method;
    }
    
    // ========================================
    
    @Override
    public MtsType getType()
    {
        return _classAdapter.getType();
    }
}
