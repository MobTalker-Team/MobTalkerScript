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

import java.lang.reflect.*;
import java.util.*;
import java.util.Map.Entry;

import net.mobtalker.mobtalkerscript.v3.value.*;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;

/* package */class LibraryAdapter
{
    private static final Map<Class<?>, LibraryAdapter> _mappers;

    static
    {
        _mappers = Maps.newHashMap();
    }

    // ========================================

    public static LibraryAdapter getMapper( Class<?> c )
    {
        LibraryAdapter mapper = _mappers.get( c );
        if ( mapper == null )
        {
            mapper = new LibraryAdapter( c );
            _mappers.put( c, mapper );
        }
        return mapper;
    }

    public static MtsTable bind( Class<?> library )
    {
        MtsTable libHolder = new MtsTable( 0, 0 );
        bind( library, libHolder );
        return libHolder;
    }

    public static MtsTable bind( Object library )
    {
        MtsTable libHolder = new MtsTable( 0, 0 );
        bind( library, libHolder );
        return libHolder;
    }

    public static void bind( Class<?> library, MtsTable table )
    {
        if ( !checkClass( library ) )
            throw new IllegalArgumentException( library.getSimpleName() + " is not a valid class!" );

        LibraryAdapter mapper = getMapper( library );
        mapper.createAndBindAdapter( null, table );
    }

    public static void bind( Object library, MtsTable table )
    {
        Class<?> c = library.getClass();

        if ( !checkClass( c ) )
            throw new IllegalArgumentException( c.getSimpleName() + " is not a valid class!" );

        LibraryAdapter mapper = getMapper( c );
        mapper.createAndBindAdapter( library, table );
    }

    // ========================================

    private final Map<String, Method> _methods;
    private final Map<String, Field> _fields;

    // ========================================

    private LibraryAdapter( Class<?> mappedClass )
    {
        checkNotNull( mappedClass );

        _methods = getMethods( mappedClass );
        _fields = getFields( mappedClass );

        if ( _methods.isEmpty() && _fields.isEmpty() )
            throw new IllegalArgumentException( "Class '" + mappedClass.getName()
                                                + "' does not have any annotated methods or fields!" );
    }

    // ========================================

    public void createAndBindAdapter( Object instance, MtsTable t )
    {
        Set<Entry<String, Method>> methods = _methods.entrySet();

        t.map().ensureSpace( methods.size() );

        for ( Entry<String, Method> entry : methods )
        {
            String name = entry.getKey();
            Method method = entry.getValue();

            JavaMethodAdapter adapter;
            if ( Modifier.isStatic( method.getModifiers() ) )
            {
                adapter = new FunctionAdapter( method, name );
            }
            else if ( instance != null )
            {
                adapter = new FixedInstanceMethodAdapter( instance, method, name );
            }
            else
            {
                throw new IllegalArgumentException( "A static library cannot contain an annotated non-static method!" );
            }

            t.set( name, adapter, false );
        }

        for ( Entry<String, Field> entry : _fields.entrySet() )
        {
            String name = entry.getKey();
            Field field = entry.getValue();

            MtsValue value;
            try
            {
                // if the field is static, the instance is ignored without an exception.
                value = (MtsValue) field.get( instance );
            }
            catch ( Exception ex )
            {
                throw Throwables.propagate( ex );
            }

            t.set( name, value );
        }
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

    private static Map<String, Method> getMethods( Class<?> c )
    {
        Map<String, Method> methods = Maps.newHashMap();

        for ( AnnotatedMethod am : getAnnotatedMethods( c ) )
        {
            methods.put( am.getName(), am.getMethod() );
        }

        return methods;
    }

    private static Map<String, Field> getFields( Class<?> c )
    {
        Map<String, Field> fields = Maps.newHashMap();

        for ( Field f : getAnnotatedFields( c ) )
        {
            fields.put( getFieldName( f ), f );
        }

        return fields;
    }
}
