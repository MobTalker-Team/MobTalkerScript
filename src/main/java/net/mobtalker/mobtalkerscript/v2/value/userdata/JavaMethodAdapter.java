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
package net.mobtalker.mobtalkerscript.v2.value.userdata;

import static com.google.common.base.Preconditions.*;
import static net.mobtalker.mobtalkerscript.v2.value.userdata.NativeHelpers.*;

import java.lang.reflect.*;

import net.mobtalker.mobtalkerscript.v2.*;
import net.mobtalker.mobtalkerscript.v2.value.*;

import com.google.common.base.Throwables;

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
    
    protected abstract Object getCallInstance( MtsVarArgs args );
    
    protected abstract Object[] getCallArguments( MtsVarArgs args, int nParams );
    
    // ========================================
    
    @Override
    public MtsValue call( MtsVarArgs args )
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
            if ( ex.getCause() instanceof ScriptRuntimeException )
            {
                ScriptRuntimeException srex = (ScriptRuntimeException) ex.getCause();
                srex.addStackTraceElement( _name );
                throw srex;
            }
            else
            {
                throw Throwables.propagate( ex.getCause() );
            }
        }
        catch ( Exception ex )
        {
            throw new ScriptEngineException( ex );
        }
        
        if ( result == null )
            return EMPTY_VARARGS;
        
        return (MtsValue) result;
    }
}
