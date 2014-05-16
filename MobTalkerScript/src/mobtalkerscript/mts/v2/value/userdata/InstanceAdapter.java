package mobtalkerscript.mts.v2.value.userdata;

import mobtalkerscript.mts.v2.value.*;

public class InstanceAdapter extends MtsObject
{
    private final ClassAdapter _classAdapter;
    
    // ========================================
    
    /* package */InstanceAdapter( ClassAdapter classAdapter, Object value )
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
