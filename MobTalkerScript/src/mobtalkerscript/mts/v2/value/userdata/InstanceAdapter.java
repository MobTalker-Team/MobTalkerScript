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
    protected MtsValue doGet( MtsValue key )
    {
        MtsValue method = null;
        if ( key.isString() )
        {
            method = _classAdapter.getMethod( key.asString().asJavaString() );
        }
        
        return method == null ? NIL : method;
    }
    
    // ========================================
    
    @Override
    public MtsType getType()
    {
        return _classAdapter.getType();
    }
}
