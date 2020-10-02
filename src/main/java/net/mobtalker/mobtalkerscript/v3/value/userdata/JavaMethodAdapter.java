/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.value.userdata;

import static net.mobtalker.mobtalkerscript.util.ThrowableUtil.throwUnchecked;
import static net.mobtalker.mobtalkerscript.v3.value.userdata.NativeHelpers.*;
import static org.apache.commons.lang3.Validate.notNull;

import java.lang.reflect.*;

import net.mobtalker.mobtalkerscript.v3.*;
import net.mobtalker.mobtalkerscript.v3.value.*;
import org.apache.commons.lang3.exception.ExceptionUtils;

/* package */abstract class JavaMethodAdapter extends MtsFunction
{
    /* package */static final Object[] EMPTY_CALLARGS = new Object[0];
    
    // ========================================
    
    private final Method _method;
    private final String _name;
    private final int _nParams;
    
    // ========================================
    
    protected JavaMethodAdapter( Method method, String name )
    {
        notNull( method );
        
        // Even if it is already, this turns off security checks.
        method.setAccessible( true );
        
        _method = method;
        _name = name;
        _nParams = getParamCount( method );
    }
    
    // ========================================
    
    public String getName()
    {
        return _name;
    }
    
    protected abstract Object getCallInstance( MtsVarargs args );
    
    protected abstract Object[] getCallArguments( MtsVarargs args, int nParams );
    
    // ========================================
    
    @Override
    public MtsVarargs call( MtsVarargs args )
    {
        Object instance = getCallInstance( args );
        Object[] convertedArgs = getCallArguments( args, _nParams );
        
        Object result;
        try
        {
            result = _method.invoke( instance, convertedArgs );
        }
        catch ( InvocationTargetException ex )
        {
            Throwable cause = ExceptionUtils.getRootCause( ex );
            if ( cause instanceof MtsRuntimeException )
            {
                MtsRuntimeException srex = (MtsRuntimeException) cause;
                srex.addStackTraceElement( _name );
            }
            
            throw throwUnchecked( cause );
        }
        catch ( Exception ex )
        {
            throw new MtsEngineException( ex );
        }
        
        if ( result == null )
            return MtsVarargs.Empty;
        
        return result instanceof MtsVarargs ? (MtsVarargs) result : MtsVarargs.of( (MtsValue) result );
    }
}
