/*
 * Copyright (C) 2013-2014 Chimaine
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

import java.lang.reflect.Method;

import net.mobtalker.mobtalkerscript.v3.value.MtsVarargs;

/* package */class FixedInstanceMethodAdapter extends JavaMethodAdapter
{
    private final Object _instance;
    
    // ========================================
    
    public FixedInstanceMethodAdapter( Object instance, Method method, String name )
    {
        super( method, name );
        checkNotNull( instance );
        
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
