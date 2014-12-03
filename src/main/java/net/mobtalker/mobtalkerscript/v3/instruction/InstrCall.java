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

import java.util.*;

import net.mobtalker.mobtalkerscript.util.logging.MtsLog;
import net.mobtalker.mobtalkerscript.v3.MtsFrame;
import net.mobtalker.mobtalkerscript.v3.value.*;

import com.google.common.collect.Lists;

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
        MtsVarArgs args = getCallArgs( frame );
        MtsValue target = frame.pop();
        
        if ( MtsLog.EngineLog.isFineEnabled() )
        {
            MtsLog.EngineLog.fine( "Calling " + target + " with " + args );
        }
        
        MtsValue result = target.call( args );
        
        if ( MtsLog.EngineLog.isFineEnabled() )
        {
            MtsLog.EngineLog.fine( "Results: " + args );
        }
        
        // Trampoline tail calls
        // Need to be executed explicitly to make sure that 0 return value tail calls are evaluated.
        if ( result instanceof MtsTailcall )
        {
            ( (MtsTailcall) result ).evaluate();
        }
        
        pushResults( frame, result );
    }
    
    protected MtsVarArgs getCallArgs( MtsFrame frame )
    {
        // TODO Not happy with this. See also InstrTailcall
        
        int nArgs = _nArgs;
        List<MtsValue> args;
        
        MtsValue lastArg = frame.pop();
        if ( lastArg.isVarArgs() )
        {
            MtsVarArgs varargs = lastArg.asVarArgs();
            int nVarargs = varargs.count();
            
            args = new ArrayList<>( nArgs + nVarargs );
            
            for ( int i = nVarargs - 1; i >= 0; i-- )
            {
                args.add( varargs.get( i ) );
            }
        }
        else
        {
            args = new ArrayList<>( nArgs );
            args.add( lastArg );
        }
        
        for ( int i = 1; i < nArgs; i++ )
        {
            args.add( frame.pop() );
        }
        
        return MtsVarArgs.of( Lists.reverse( args ) );
    }
    
    protected void pushResults( MtsFrame frame, MtsValue result )
    {
        if ( result.isVarArgs() )
        {
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
    public final String toString()
    {
        return "CALL " + _nArgs + " " + _nReturn;
    }
}
