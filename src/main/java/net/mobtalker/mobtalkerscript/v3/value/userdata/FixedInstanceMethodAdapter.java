/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.value.userdata;

import java.lang.reflect.Method;

import net.mobtalker.mobtalkerscript.v3.value.MtsVarargs;

import static org.apache.commons.lang3.Validate.notNull;

/* package */class FixedInstanceMethodAdapter extends JavaMethodAdapter
{
    private final Object _instance;
    
    // ========================================
    
    public FixedInstanceMethodAdapter( Object instance, Method method, String name )
    {
        super( method, name );
        notNull( instance );
        
        if ( !method.getDeclaringClass().isAssignableFrom( instance.getClass() ) )
            throw new IllegalArgumentException( String.format( "instance is of type %s that is neither the same or a subclass of %s!",
                                                               instance.getClass().getName(),
                                                               method.getDeclaringClass().getName() ) );
        
        _instance = instance;
    }
    
    // ========================================
    
    @Override
    protected Object getCallInstance( MtsVarargs args )
    {
        return _instance;
    }
    
    @Override
    protected Object[] getCallArguments( MtsVarargs args, int nParams )
    {
        if ( nParams == -1 )
            return new Object[] { args };
        if ( nParams == 0 )
            return EMPTY_CALLARGS;
        
        Object[] result = new Object[nParams];
        for ( int i = 0; i < nParams; i++ )
        {
            result[i] = args.get( i );
        }
        
        return result;
    }
}
