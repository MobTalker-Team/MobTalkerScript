package mobtalkerscript.mts.v2.value.userdata;

import static com.google.common.base.Preconditions.*;
import static mobtalkerscript.mts.v2.value.userdata.NativeHelpers.*;

import java.lang.reflect.*;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;

import com.google.common.base.*;

/* package */abstract class JavaMethodAdapter extends MtsFunction
{
    /* package */static final Object[] EMPTY_CALLARGS = new Object[0];
    
    // ========================================
    
    private final Method _method;
    private final int _nParams;
    
    // ========================================
    
    protected JavaMethodAdapter( Method method )
    {
        checkNotNull( method );
        
        _method = method;
        _nParams = getParamCount( method );
    }
    
    // ========================================
    
    protected abstract Object getCallInstance( MtsVarArgs args );
    
    protected abstract Object[] getCallArguments( MtsVarArgs args, int nParams );
    
    // ========================================
    
    @Override
    public MtsValue call( MtsVarArgs args )
    {
        Object instance = getCallInstance( args );
        Object[] convertedArgs = getCallArguments( args, _nParams );
        
        Object result;
        try
        {
            result = _method.invoke( instance, convertedArgs );
        }
        catch ( IllegalAccessException ex )
        {
            throw new Error( ex );
        }
        catch ( IllegalArgumentException ex )
        {
            throw new ScriptEngineException( ex );
        }
        catch ( InvocationTargetException ex )
        {
            throw Throwables.propagate( ex.getTargetException() );
        }
        
        if ( result == null )
            return EMPTY_VARARGS;
        
        return (MtsValue) result;
    }
}
