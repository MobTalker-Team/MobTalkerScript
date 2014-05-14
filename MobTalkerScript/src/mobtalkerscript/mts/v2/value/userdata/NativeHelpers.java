package mobtalkerscript.mts.v2.value.userdata;

import java.lang.reflect.*;

import mobtalkerscript.mts.v2.value.*;

/* package */class NativeHelpers
{
    public static int getParamCount( Method m )
    {
        Class<?>[] paramTypes = m.getParameterTypes();
        
        if ( ( paramTypes.length == 1 ) && isMtsVarargsClass( paramTypes[0] ) )
            return -1;
        
        return paramTypes.length;
    }
    
    public static boolean isMtsValueClass( Class<?> c )
    {
        return MtsValue.class.isAssignableFrom( c );
    }
    
    public static boolean isMtsVarargsClass( Class<?> c )
    {
        return MtsVarArgs.class.isAssignableFrom( c );
    }
}
