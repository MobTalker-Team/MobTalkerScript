package mobtalkerscript.mts.v2.value.userdata;

import static com.google.common.base.Preconditions.*;

import java.lang.reflect.*;
import java.util.*;

import mobtalkerscript.mts.v2.value.*;

import com.google.common.collect.*;

@SuppressWarnings( "rawtypes" )
public class ClassMapper
{
    private final Class _mappedClass;
    private final Map<String, MethodAdapter> _methods;
    
    public ClassMapper( Class mappedClass )
    {
        checkNotNull( mappedClass );
        
        _mappedClass = mappedClass;
        _methods = Maps.newHashMap();
        
        initialize();
    }
    
    private void initialize()
    {
        for ( Method m : _mappedClass.getMethods() )
        {
            if ( !checkMethodSignature( m ) )
                continue;
            
            m.setAccessible( true );
            
            String name = getMethodName( m );
            int nParams = getParamCount( m );
            _methods.put( name, new MethodAdapter( m, nParams ) );
        }
    }
    
    private static boolean checkMethodSignature( Method m )
    {
        MtsCallableMethod a = m.getAnnotation( MtsCallableMethod.class );
        if ( a == null )
            return false;
        
        Class[] paramTypes = m.getParameterTypes();
        if ( ( paramTypes.length == 1 ) && !isMtsVarargsClass( paramTypes[0] ) )
        {
            return false;
        }
        else
        {
            for ( Class paramType : paramTypes )
            {
                if ( !isMtsValueClass( paramType ) )
                    return false;
            }
        }
        
        Class returnType = m.getReturnType();
        if ( returnType != MtsVarArgs.class )
            return false;
        
        return true;
    }
    
    private static String getMethodName( Method m )
    {
        MtsCallableMethod a = m.getAnnotation( MtsCallableMethod.class );
        
        String result = a.name();
        return result == null ? m.getName() : result;
    }
    
    private static int getParamCount( Method m )
    {
        Class[] paramTypes = m.getParameterTypes();
        if ( ( paramTypes.length == 1 ) && MtsVarArgs.class.isAssignableFrom( paramTypes[0] ) )
            return -1;
        
        return paramTypes.length;
    }
    
    private static boolean isMtsValueClass( Class c )
    {
        return MtsValue.class.isAssignableFrom( c );
    }
    
    private static boolean isMtsVarargsClass( Class c )
    {
        return MtsVarArgs.class.isAssignableFrom( c );
    }
}
