/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.value.userdata;

import java.lang.reflect.Method;

import net.mobtalker.mobtalkerscript.v3.value.MtsVarargs;

/* package */class FunctionAdapter extends JavaMethodAdapter
{
    public FunctionAdapter( Method method, String name )
    {
        super( method, name );
    }
    
    // ========================================
    
    @Override
    protected Object getCallInstance( MtsVarargs args )
    {
        return null;
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
