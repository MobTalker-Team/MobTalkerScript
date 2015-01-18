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

import java.lang.reflect.*;
import java.util.*;

import net.mobtalker.mobtalkerscript.v3.value.*;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/* package */class NativeHelpers
{
    public static List<AnnotatedMethod> getAnnotatedMethods( Class<?> c )
    {
        List<AnnotatedMethod> results = Lists.newArrayList();
        for ( Method m : c.getMethods() )
        {
            AnnotatedMethod am = getAnnotatedMethod( m );
            if ( am != null )
            {
                results.add( am );
            }
        }
        
        return results;
    }
    
    public static AnnotatedMethod getAnnotatedMethod( Method m )
    {
        if ( !Modifier.isPublic( m.getModifiers() ) )
            return null;
        if ( m.getDeclaringClass().equals( Object.class ) )
            return null;
        
        Class<?>[] paramTypes = m.getParameterTypes();
        for ( Class<?> paramType : paramTypes )
        {
            if ( !isMtsValueClass( paramType ) )
                return null;
        }
        
        Class<?> returnType = m.getReturnType();
        if ( !isMtsValueClass( returnType ) && ( returnType != Void.TYPE ) )
            return null;
        
        MtsNativeFunction a = m.getAnnotation( MtsNativeFunction.class );
        if ( a == null )
        {
            a = searchInterfaces( m, m.getDeclaringClass().getInterfaces() );
        }
        if ( a == null )
            return null;
        
        return new AnnotatedMethod( m, getMethodName( m, a ) );
    }
    
    private static MtsNativeFunction searchInterfaces( Method m, Class<?>[] ifaces )
    {
        for ( Class<?> iface : ifaces )
        {
            try
            {
                Method mIface = iface.getDeclaredMethod( m.getName(), m.getParameterTypes() );
                MtsNativeFunction annotation = mIface.getAnnotation( MtsNativeFunction.class );
                if ( annotation != null )
                    return annotation;
            }
            catch ( NoSuchMethodException | SecurityException ex )
            {}
        }
        
        return null;
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
        if ( ( a == null ) || Strings.isNullOrEmpty( a.value() ) )
            return c.getSimpleName().toLowerCase();
        
        return a.value();
    }
    
    public static String getMethodName( Method m, MtsNativeFunction a )
    {
        if ( ( a == null ) || Strings.isNullOrEmpty( a.value() ) )
            return StringUtils.capitalize( m.getName() );
        
        return a.value();
    }
    
    public static String getMethodName( Method m )
    {
        MtsNativeFunction a = m.getAnnotation( MtsNativeFunction.class );
        if ( ( a == null ) || Strings.isNullOrEmpty( a.value() ) )
            return StringUtils.capitalize( m.getName() );
        
        return a.value();
    }
    
    public static String getFieldName( Field f )
    {
        MtsNativeField a = f.getAnnotation( MtsNativeField.class );
        if ( ( a == null ) || Strings.isNullOrEmpty( a.value() ) )
            return StringUtils.capitalize( f.getName() );
        
        return a.value();
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
        return MtsVarargs.class.isAssignableFrom( c );
    }
}
