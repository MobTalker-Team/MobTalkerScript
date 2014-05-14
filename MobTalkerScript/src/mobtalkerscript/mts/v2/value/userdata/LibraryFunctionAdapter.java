package mobtalkerscript.mts.v2.value.userdata;

import static com.google.common.base.Preconditions.*;
import static mobtalkerscript.mts.v2.value.userdata.NativeHelpers.*;

import java.lang.reflect.*;

import mobtalkerscript.mts.v2.value.*;

public class LibraryFunctionAdapter extends MtsNativeCallable
{
    private final Object _instance;
    private final int _nParams;
    
    // ========================================
    
    public LibraryFunctionAdapter( Object instance, Method method )
    {
        super( method );
        checkNotNull( instance );
        
        _instance = instance;
        _nParams = getParamCount( method );
    }
    
    // ========================================
    
    @Override
    protected Object getCallInstance( MtsVarArgs args )
    {
        return _instance;
    }
    
    @Override
    protected Object[] getCallArguments( MtsVarArgs args )
    {
        if ( _nParams == -1 )
            return new Object[] { args };
        if ( _nParams == 0 )
            return EMPTY_CALLARGS;
        
        Object[] result = new Object[_nParams];
        for ( int i = 0; i < _nParams; i++ )
        {
            result[i] = args.get( i );
        }
        
        return result;
    }
    
}
