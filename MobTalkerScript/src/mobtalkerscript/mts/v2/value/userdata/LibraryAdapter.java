package mobtalkerscript.mts.v2.value.userdata;

import static com.google.common.base.Preconditions.*;
import static mobtalkerscript.mts.v2.value.userdata.NativeHelpers.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.Map.Entry;

import mobtalkerscript.mts.v2.value.*;

import com.google.common.collect.*;

public class LibraryAdapter
{
    private static final Map<Class<?>, LibraryAdapter> _mappers;
    
    static
    {
        _mappers = Maps.newHashMap();
    }
    
    // ========================================
    
    public static MtsTable bind( Object library )
    {
        MtsTable libHolder = new MtsTable();
        bindTo( library, libHolder );
        return libHolder;
    }
    
    public static void bindTo( Object library, MtsTable table )
    {
        Class<?> c = library.getClass();
        
        if ( !checkClass( c ) )
            throw new IllegalArgumentException( c.getSimpleName() + " is not a valid class!" );
        
        LibraryAdapter mapper = _mappers.get( c );
        if ( mapper == null )
        {
            mapper = new LibraryAdapter( c );
            _mappers.put( c, mapper );
        }
        
        for ( Entry<String, Method> entry : mapper.getMethods() )
        {
            String name = entry.getKey();
            Method method = entry.getValue();
            
            JavaMethodAdapter adapter;
            if ( Modifier.isStatic( method.getModifiers() ) )
            {
                adapter = new FunctionAdapter( method );
            }
            else
            {
                adapter = new LibraryMethodAdapter( library, method );
            }
            
            table.set( name, adapter, false );
        }
    }
    
    // ========================================
    
    private final Map<String, Method> _methods;
    
    // ========================================
    
    private LibraryAdapter( Class<?> mappedClass )
    {
        checkNotNull( mappedClass );
        _methods = getValidMethods( mappedClass );
    }
    
    // ========================================
    
    public Set<Entry<String, Method>> getMethods()
    {
        return _methods.entrySet();
    }
    
    // ========================================
    
    private static boolean checkClass( Class<?> c )
    {
        if ( c.isPrimitive() || c.isInterface() || c.isAnnotation() || c.isArray() )
            return false;
        
        if ( !Modifier.isPublic( c.getModifiers() ) )
            return false;
        
        return true;
    }
    
    private static Map<String, Method> getValidMethods( Class<?> c )
    {
        Map<String, Method> methods = Maps.newHashMap();
        
        for ( Method m : c.getMethods() )
        {
            if ( !checkMethodSignature( m ) )
                continue;
            
            String name = getMethodName( m );
            methods.put( name, m );
        }
        
        return methods;
    }
    
    private static boolean checkMethodSignature( Method m )
    {
        if ( !Modifier.isPublic( m.getModifiers() ) )
            return false;
        
        if ( !m.isAnnotationPresent( MtsNativeLibraryFunction.class ) )
            return false;
        
        Class<?>[] paramTypes = m.getParameterTypes();
        for ( Class<?> paramType : paramTypes )
        {
            if ( !isMtsValueClass( paramType ) )
                return false;
        }
        
        Class<?> returnType = m.getReturnType();
        if ( !isMtsValueClass( returnType ) && ( returnType != Void.TYPE ) )
            return false;
        
        return true;
    }
    
    private static String getMethodName( Method m )
    {
        MtsNativeLibraryFunction a = m.getAnnotation( MtsNativeLibraryFunction.class );
        
        String result = a.name();
        return result == null ? m.getName() : result;
    }
}
