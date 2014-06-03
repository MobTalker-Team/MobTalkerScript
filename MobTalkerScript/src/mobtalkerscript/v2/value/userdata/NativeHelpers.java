package mobtalkerscript.v2.value.userdata;

import java.lang.reflect.*;
import java.util.*;

import mobtalkerscript.v2.value.*;

import org.apache.commons.lang3.*;

import com.google.common.base.*;

/* package */class NativeHelpers
{
    public static List<Method> getAnnotatedMethods( Class<?> c )
    {
        List<Method> result = new ArrayList<Method>();
        for ( Method m : c.getMethods() )
        {
            if ( checkMethodSignature( m ) )
            {
                result.add( m );
            }
        }
        
        return result;
    }
    
    public static boolean checkMethodSignature( Method m )
    {
        if ( !Modifier.isPublic( m.getModifiers() ) )
        {
//            System.out.println( "Skipping non-public method " + m.getName() );
            return false;
        }
        if ( !m.isAnnotationPresent( MtsNativeFunction.class ) )
        {
//            System.out.println( "Skipping non-annotated method " + m.getName() );
            return false;
        }
        
        Class<?>[] paramTypes = m.getParameterTypes();
        for ( Class<?> paramType : paramTypes )
        {
            if ( !isMtsValueClass( paramType ) )
            {
//                System.out.println( "Skipping method "
//                                    + m.getName()
//                                    + " with wrong parameter type "
//                                    + paramType.getSimpleName() );
                return false;
            }
        }
        
        Class<?> returnType = m.getReturnType();
        if ( !isMtsValueClass( returnType ) && ( returnType != Void.TYPE ) )
        {
//            System.out.println( "Skipping method "
//                                + m.getName()
//                                + " with wrong return type "
//                                + returnType.getSimpleName() );
            return false;
        }
        
        return true;
    }
    
    public static List<Field> getAnnotatedFields( Class<?> c )
    {
        List<Field> result = new ArrayList<Field>();
        for ( Field f : c.getFields() )
        {
            if ( Modifier.isPublic( f.getModifiers() )
                 && Modifier.isFinal( f.getModifiers() )
                 && isMtsValueClass( f.getType() )
                 && f.isAnnotationPresent( MtsNativeField.class ) )
            {
                result.add( f );
            }
        }
        
        return result;
    }
    
    public static String getClassName( Class<?> c )
    {
        MtsNativeFunction a = ( (AnnotatedElement) c ).getAnnotation( MtsNativeFunction.class );
        if ( ( a == null ) || Strings.isNullOrEmpty( a.name() ) )
            return c.getSimpleName().toLowerCase();
        
        return a.name();
    }
    
    public static String getMethodName( Method m )
    {
        MtsNativeFunction a = m.getAnnotation( MtsNativeFunction.class );
        if ( ( a == null ) || Strings.isNullOrEmpty( a.name() ) )
            return StringUtils.capitalize( m.getName() );
        
        return a.name();
    }
    
    public static String getFieldName( Field f )
    {
        MtsNativeField a = f.getAnnotation( MtsNativeField.class );
        if ( ( a == null ) || Strings.isNullOrEmpty( a.name() ) )
            return StringUtils.capitalize( f.getName() );
        
        return a.name();
    }
    
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
