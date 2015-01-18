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

import net.mobtalker.mobtalkerscript.v3.*;
import net.mobtalker.mobtalkerscript.v3.value.*;

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
        checkNotNull( method );
        
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
            Throwable cause = ex.getCause();
            MtsRuntimeException srex;
            if ( cause instanceof MtsRuntimeException )
            {
                srex = (MtsRuntimeException) cause;
            }
            else
            {
                String msg = cause.getMessage();
                StackTraceElement ste = cause.getStackTrace()[0];
                
                srex = new MtsRuntimeException( msg );
                srex.addStackTraceElement( ste.toString() );
            }
            
            srex.addStackTraceElement( _name );
            throw srex;
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
