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
package net.mobtalker.mobtalkerscript.v3.instruction;

import net.mobtalker.mobtalkerscript.util.logging.MtsLog;
import net.mobtalker.mobtalkerscript.v3.MtsFrame;
import net.mobtalker.mobtalkerscript.v3.value.*;

public class InstrCall extends MtsInstruction
{
    private final int _nArgs;
    private final int _nReturn;
    
    // ========================================
    
    /* package */InstrCall( int nArgs, int nReturn )
    {
        _nArgs = nArgs;
        _nReturn = nReturn;
    }
    
    // ========================================
    
    @Override
    public final void execute( MtsFrame frame )
    {
        MtsVarargs args = getCallArgs( frame );
        MtsValue target = frame.pop();
        
        if ( MtsLog.EngineLog.isFineEnabled() )
        {
            MtsLog.EngineLog.fine( "Calling " + target + " with " + args );
        }
        
        MtsValue result = getResults( target, args );
        
        if ( MtsLog.EngineLog.isFineEnabled() )
        {
            MtsLog.EngineLog.fine( "Results: " + result );
        }
        
        pushResults( frame, result );
    }
    
    protected MtsVarargs getCallArgs( MtsFrame frame )
    {
        return frame.pop( _nArgs );
    }
    
    protected MtsValue getResults( MtsValue target, MtsVarargs args )
    {
        return target.call( args );
    }
    
    protected void pushResults( MtsFrame frame, MtsValue result )
    {
        if ( result.isVarArgs() )
        {
            if ( result instanceof MtsTailcall )
            {
                result = ( (MtsTailcall) result ).evaluate();
                if ( !result.isVarArgs() )
                    result = MtsVarargs.of( result );
            }
            
            // nReturn is -1 if the call site is the last formal parameter of a call
            // We return the varargs as-is, so they can be expanded by the following call (see above)
            if ( _nReturn == -1 )
            {
                frame.push( result );
            }
            else
            {
                frame.push( result.asVarArgs(), _nReturn );
            }
        }
        else
        {
            frame.push( result );
            
            if ( _nReturn != -1 )
            {
                frame.pushNil( _nReturn - 1 );
            }
        }
    }
    
    // ========================================
    
    @Override
    public final int stackSizeChange()
    {
        return -( 1 + _nArgs ) + ( _nReturn == -1 ? 1 : _nReturn );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "CALL " + _nArgs + " " + _nReturn;
    }
}
