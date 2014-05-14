package mobtalkerscript.mts.v2.value.userdata;

import java.lang.reflect.*;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;

public abstract class MtsNativeCallable extends MtsFunction
{
    protected final Method _method;
    
    protected MtsNativeCallable( Method method )
    {
        _method = method;
    }
    
    protected static final Object[] EMPTY_CALLARGS = new Object[0];
    
    protected abstract Object getCallInstance( MtsVarArgs args );
    
    protected abstract Object[] getCallArguments( MtsVarArgs args );
    
    @Override
    public MtsValue call( MtsVarArgs args )
    {
        Object instance = getCallInstance( args );
        Object[] convertedArgs = getCallArguments( args );
        
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
            throw new ScriptRuntimeException( ex.getMessage() );
        }
        
        if ( result == null )
            return EMPTY_VARARGS;
        
        return (MtsValue) result;
    }
}
