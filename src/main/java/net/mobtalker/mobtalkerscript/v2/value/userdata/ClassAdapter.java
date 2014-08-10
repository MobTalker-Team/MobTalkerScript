/*
 * Copyright (C) 2013-2014 Chimaine
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mobtalker.mobtalkerscript.v2.value.userdata;

import static com.google.common.base.Preconditions.*;
import static net.mobtalker.mobtalkerscript.v2.value.userdata.NativeHelpers.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.Map.Entry;

import net.mobtalker.mobtalkerscript.v2.value.MtsType;

import com.google.common.base.Throwables;
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
        if ( !ClassAdapter.checkClass( c ) )
            throw new IllegalArgumentException( c.getSimpleName() + " is not a valid class!" );

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

        _type = getType( mappedClass );
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

    public Set<Entry<String, MethodAdapter>> getMethods()
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

        if ( !c.isAnnotationPresent( MtsNativeClass.class ) )
            return false;

        return true;
    }

    private static MtsType getType( Class<?> c )
    {
        for ( Field f : c.getDeclaredFields() )
        {
            if ( Modifier.isStatic( f.getModifiers() )
                    && Modifier.isPublic( f.getModifiers() )
                    && ( f.getType() == MtsType.class )
                    && f.isAnnotationPresent( MtsNativeType.class ) )
            {
                try
                {
                    return (MtsType) f.get( null );
                }
                catch ( Exception ex )
                {
                    throw Throwables.propagate( ex );
                }
            }
        }

        return MtsType.forName( getClassName( c ) );
    }

    private static Map<String, MethodAdapter> createMethodAdapters( Class<?> c )
    {
        Map<String, MethodAdapter> methods = Maps.newHashMap();

        for ( Method m : getAnnotatedMethods( c ) )
        {
            String name = getMethodName( m );
            methods.put( name, new MethodAdapter( m, name ) );
        }

        return methods;
    }
}
