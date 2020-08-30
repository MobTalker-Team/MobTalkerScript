/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.value.userdata;

import static com.google.common.base.Preconditions.*;
import static net.mobtalker.mobtalkerscript.v3.value.userdata.NativeHelpers.*;

import java.lang.reflect.Modifier;
import java.util.Map;

import net.mobtalker.mobtalkerscript.v3.value.MtsType;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

/* package */class ClassAdapter
{
    private static final Map<Class<?>, ClassAdapter> _mappers = Maps.newHashMap();
    
    /**
     * Returns a mapper for the given class.
     * <p>
     * The class needs to be public and the {@link MtsNativeClass} annotation must be present.
     * <p>
     * Primitives, Arrays, Interfaces and Annotations cannot have an adapter.
     * <p>
     * Adapters are created lazily and this method will return the same adapter for the same class in subsequent calls.
     */
    public static ClassAdapter get( Class<?> c )
    {
        MtsType type = getTypeFromClass( c );
        if ( type == null )
            throw new IllegalArgumentException( c.getSimpleName() + " is not a valid class!" );
        
        ClassAdapter mapper = _mappers.get( c );
        if ( mapper == null )
        {
            mapper = new ClassAdapter( c, type );
            _mappers.put( c, mapper );
        }
        
        return mapper;
    }
    
    // ========================================
    
    private final Class<?> _mappedClass;
    
    private final MtsType _type;
    private final Map<String, JavaMethodAdapter> _methods;
    
    // ========================================
    
    private ClassAdapter( Class<?> mappedClass, MtsType type )
    {
        checkNotNull( mappedClass );
        checkNotNull( type );
        
        _mappedClass = mappedClass;
        _type = type;
        _methods = createMethodAdapters( mappedClass );
    }
    
    private Map<String, JavaMethodAdapter> createMethodAdapters( Class<?> c )
    {
        Map<String, JavaMethodAdapter> methods = Maps.newHashMap();
        
        for ( AnnotatedMethod am : getAnnotatedMethods( c ) )
        {
            methods.put( am.getName(), createMethodAdapter( am ) );
        }
        
        return methods;
    }
    
    private JavaMethodAdapter createMethodAdapter( AnnotatedMethod method )
    {
        return new MethodAdapter( method.getMethod(), method.getName() );
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
    
    public JavaMethodAdapter getMethod( String method )
    {
        return _methods.get( method );
    }
    
    // ========================================
    
    private static MtsType getTypeFromClass( Class<?> c )
    {
        if ( c.isPrimitive() || c.isInterface() || c.isAnnotation() || c.isArray() )
            return null;
        if ( !Modifier.isPublic( c.getModifiers() ) )
            return null;
        
        MtsNativeClass a = c.getAnnotation( MtsNativeClass.class );
        if ( a == null )
            return null;
        
        String name = a.value();
        return Strings.isNullOrEmpty( name ) ? MtsType.USERDATA : MtsType.forName( name );
    }
}
