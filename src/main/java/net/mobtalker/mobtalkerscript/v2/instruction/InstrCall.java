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
package net.mobtalker.mobtalkerscript.v2.instruction;

import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;
import net.mobtalker.mobtalkerscript.v2.MtsFrame;
import net.mobtalker.mobtalkerscript.v2.value.*;

public final class InstrCall extends MtsInstruction
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
    public void execute( MtsFrame frame )
    {
        MtsValue[] args = new MtsValue[_nArgs];
        for ( int i = _nArgs - 1; i >= 0; i-- )
        {
            args[i] = frame.pop();
        }
        
        MtsValue target = frame.pop();
        MtsValue result = target.call( MtsVarArgs.of( args ) );
        
        // Trampoline tail calls
        // Need to be executed explicitly to make sure that 0 return value tail calls are evaluated.
        if ( result instanceof MtsTailcall )
        {
            ( (MtsTailcall) result ).evaluate();
        }
        
        if ( _nReturn == 0 )
            return;
        
        if ( result.isVarArgs() )
        {
            MtsVarArgs results = result.asVarArgs();
            for ( int i = 0; i < _nReturn; i++ )
            {
                frame.push( results.get( i ) );
            }
        }
        else
        {
            frame.push( result );
            for ( int i = 1; i < _nReturn; i++ )
            {
                frame.push( NIL );
            }
        }
    }
    
    @Override
    public int stackSizeChange()
    {
        return -( 1 + _nArgs ) + _nReturn;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "CALL " + _nArgs + " " + _nReturn;
    }
}
