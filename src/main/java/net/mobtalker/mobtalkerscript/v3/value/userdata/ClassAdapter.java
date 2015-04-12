/*
 * Copyright (C) 2013-2015 Chimaine
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
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
