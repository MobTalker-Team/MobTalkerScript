package mobtalkerscript.mts.v2.value.userdata;

import static com.google.common.base.Preconditions.*;

import java.lang.reflect.*;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;

public class MethodAdapter extends MtsFunction
{
    private final Method _nativeMethod;
    private final int _nParams;
    
    // ========================================
    
    public MethodAdapter( Method nativeMethod, int nParams )
    {
        checkNotNull( nativeMethod );
        checkArgument( nParams >= -1 );
        
        _nativeMethod = nativeMethod;
        _nParams = nParams;
    }
    
    // ========================================
    
    private Object[] convertCallArgs( MtsVarArgs args )
    {
        if ( _nParams == -1 )
            return new Object[] { args.subArgs( 1 ) };
        if ( _nParams == 0 )
            return null; // This is allowed by Method.invoke(Object, Object...)
            
        Object[] result = new Object[_nParams];
        for ( int i = 1; i <= _nParams; i++ )
        {
            result[i] = args.get( i );
        }
        return result;
    }
    
    @Override
    public MtsVarArgs call( MtsVarArgs args )
    {
        Object instance = args.get( 0 ).asObject().asJavaObject();
        Object[] callArgs = convertCallArgs( args );
        
        Object result;
        try
        {
            result = _nativeMethod.invoke( instance, callArgs );
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
        
        return (MtsVarArgs) result;
    }
    
}
