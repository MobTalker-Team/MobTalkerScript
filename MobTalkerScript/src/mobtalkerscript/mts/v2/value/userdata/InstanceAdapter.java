package mobtalkerscript.mts.v2.value.userdata;

import mobtalkerscript.mts.v2.value.*;

public class InstanceAdapter extends MtsUserdata
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
        if ( !useMetaTag )
            return super.get( key, useMetaTag );
        
        MtsValue method = null;
        if ( key.isString() )
        {
            method = _classAdapter.getMethod( key.asString().asJavaString() );
        }
        
        if ( method == null )
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
