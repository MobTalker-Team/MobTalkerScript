package mobtalkerscript.mts.v2.value.userdata;

import static com.google.common.base.Preconditions.*;

import java.lang.reflect.*;
import java.util.*;

import mobtalkerscript.mts.v2.value.*;

import com.google.common.base.*;
import com.google.common.collect.*;

public class ClassAdapter
{
    private static final Map<Class<?>, ClassAdapter> _mappers;
    
    static
    {
        _mappers = Maps.newHashMap();
    }
    
    // ========================================
    
    /**
     * Returns a mapper for the given class.
     * <p>
     * The class needs to be public and the {@link MtsAccessibleClass} annotation must be present.
     * <p>
     * Primitives, Arrays, Interfaces and Annotations cannot have an adapter.
     * <p>
     * Adapters are created lazily and this method will return the same adapter for the same class in subsequent calls.
     */
    public static ClassAdapter forClass( Class<?> c )
    {
        if ( !checkClass( c ) )
            throw new IllegalArgumentException( "Class has no "
                                                + MtsAccessibleClass.class.getName()
                                                + " annotation!" );
        
        ClassAdapter mapper = _mappers.get( c );
        if ( mapper == null )
        {
            mapper = new ClassAdapter( c );
            _mappers.put( c, mapper );
        }
        
        return mapper;
    }
    
    // ========================================
    
    private final Class<?> _mappedClass;
    
    private final MtsType _type;
    private final Map<String, MethodAdapter> _methods;
    
    // ========================================
    
    private ClassAdapter( Class<?> mappedClass )
    {
        checkNotNull( mappedClass );
        
        _mappedClass = mappedClass;
        
        _type = createType( mappedClass );
        _methods = createMethodAdapters( mappedClass );
    }
    
    // ========================================
    
    public Class<?> getMappedClass()
    {
        return _mappedClass;
    }
    
    public MtsType getType()
    {
        return _type;
    }
    
    public MethodAdapter getMethod( String method )
    {
        return _methods.get( method );
    }
    
    // ========================================
    
    private static boolean checkClass( Class<?> c )
    {
        if ( c.isPrimitive() || c.isInterface() || c.isAnnotation() || c.isArray() )
            return false;
        
        if ( !Modifier.isPublic( c.getModifiers() ) )
            return false;
        
        if ( !c.isAnnotationPresent( MtsAccessibleClass.class ) )
            return false;
        
        return true;
    }
    
    private static MtsType createType( Class<?> c )
    {
        String typeName;
        
        // For whatever reason the compiler complains when we do not cast
        MtsAccessibleClass a = ( (AnnotatedElement) c ).getAnnotation( MtsAccessibleClass.class );
        typeName = a.name();
        
        if ( Strings.isNullOrEmpty( typeName ) )
        {
            typeName = c.getSimpleName();
        }
        
        return new MtsType( typeName );
    }
    
    private static Map<String, MethodAdapter> createMethodAdapters( Class<?> c )
    {
        Map<String, MethodAdapter> methods = Maps.newHashMap();
        
        for ( Method m : c.getMethods() )
        {
            if ( !checkMethodSignature( m ) )
                continue;
            
            String name = getMethodName( m );
            int nParams = getParamCount( m );
            methods.put( name, new MethodAdapter( m, nParams ) );
        }
        
        return methods;
    }
    
    private static boolean checkMethodSignature( Method m )
    {
        if ( !Modifier.isPublic( m.getModifiers() ) )
            return false;
        
        if ( !m.isAnnotationPresent( MtsCallableMethod.class ) )
            return false;
        
        Class<?>[] paramTypes = m.getParameterTypes();
        for ( Class<?> paramType : paramTypes )
        {
            if ( !isMtsValueClass( paramType ) )
                return false;
        }
        
        Class<?> returnType = m.getReturnType();
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
        Class<?>[] paramTypes = m.getParameterTypes();
        
        if ( ( paramTypes.length == 1 ) && isMtsVarargsClass( paramTypes[0] ) )
            return -1;
        
        return paramTypes.length;
    }
    
    private static boolean isMtsValueClass( Class<?> c )
    {
        return MtsValue.class.isAssignableFrom( c );
    }
    
    private static boolean isMtsVarargsClass( Class<?> c )
    {
        return MtsVarArgs.class.isAssignableFrom( c );
    }
}
