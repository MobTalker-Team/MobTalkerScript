/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.value.userdata;

import java.lang.reflect.*;
import java.util.*;

import net.mobtalker.mobtalkerscript.v3.value.*;

import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/* package */class NativeHelpers
{
    public static List<AnnotatedMethod> getAnnotatedMethods( Class<?> c )
    {
        List<AnnotatedMethod> results = new ArrayList<>();
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
            return null;
        
        return new AnnotatedMethod( m, getMethodName( m, a ) );
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
        if ( ( a == null ) || isEmpty( a.value() ) )
            return c.getSimpleName().toLowerCase();
        
        return a.value();
    }
    
    public static String getMethodName( Method m, MtsNativeFunction a )
    {
        if ( ( a == null ) || isEmpty( a.value() ) )
            return StringUtils.capitalize( m.getName() );
        
        return a.value();
    }
    
    public static String getMethodName( Method m )
    {
        MtsNativeFunction a = m.getAnnotation( MtsNativeFunction.class );
        if ( ( a == null ) || isEmpty( a.value() ) )
            return StringUtils.capitalize( m.getName() );
        
        return a.value();
    }
    
    public static String getFieldName( Field f )
    {
        MtsNativeField a = f.getAnnotation( MtsNativeField.class );
        if ( ( a == null ) || isEmpty( a.value() ) )
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
    
    // ========================================
    
    // private constructor to prevent instantiation
    private NativeHelpers() {}
}
