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

import java.io.*;

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
        MtsVarargs args = _nArgs > 0 ? frame.pop( _nArgs ) : MtsVarargs.Empty;
        MtsValue target = frame.pop();
        
        if ( MtsLog.EngineLog.isFineEnabled() )
        {
            MtsLog.EngineLog.fine( "Calling " + target + " with " + args );
        }
        
        MtsVarargs result = getResults( target, args );
        
        if ( MtsLog.EngineLog.isFineEnabled() )
        {
            MtsLog.EngineLog.fine( "Results: " + result );
        }
        
        pushResults( frame, result );
    }
    
    protected MtsVarargs getResults( MtsValue target, MtsVarargs args )
    {
        return target.call( args );
    }
    
    protected void pushResults( MtsFrame frame, MtsVarargs results )
    {
        if ( results instanceof MtsTailcall )
        {
            results = ( (MtsTailcall) results ).evaluate();
        }
        
        if ( _nReturn == 0 )
            return;
        
        // nReturn is -1 if the call site is the last formal parameter of a call
        // We return the varargs as-is, so they can be expanded by the following call
        if ( _nReturn == -1 )
        {
            frame.push( results );
        }
        else
        {
            frame.push( results, _nReturn );
        }
    }
    
    // ========================================
    
    public int getArgCount()
    {
        return _nArgs;
    }
    
    public int getReturnCount()
    {
        return _nReturn;
    }
    
    @Override
    public final int stackSizeChange()
    {
        return -( 1 + _nArgs ) + ( _nReturn == -1 ? 1 : _nReturn );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return Instructions.CALL_NAME + " " + _nArgs + " " + _nReturn;
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.CALL );
        stream.writeByte( _nArgs );
        stream.writeByte( _nReturn );
    }
}
