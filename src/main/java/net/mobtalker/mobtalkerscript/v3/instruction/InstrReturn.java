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

import static net.mobtalker.mobtalkerscript.v3.value.MtsValue.*;
import net.mobtalker.mobtalkerscript.v3.MtsFrame;
import net.mobtalker.mobtalkerscript.v3.value.*;

public final class InstrReturn extends MtsInstruction
{
    private final int _count;
    
    // ========================================
    
    /* package */InstrReturn( int count )
    {
        _count = count;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        if ( _count == 0 )
        {
            frame.push( EMPTY_VARARGS );
        }
        else if ( frame.peek() instanceof MtsTailcall )
        {
            return;
        }
        else
        {
            /*
             * Possible scenarios
             * [ a, b, c, d ]
             * [ a, b, varargs[ c, d ] ]
             * Must result in
             * [ varargs[ a, b, c, d ] ]
             */
            
            int nValues = _count;
            MtsValue[] values;
            
            MtsValue value = frame.pop();
            if ( value.isVarArgs() )
            {
                assert !( value instanceof MtsTailcall );
                
                int nVarargs = value.asVarArgs().count();
                values = new MtsValue[( nValues - 1 ) + nVarargs];
                
                for ( int iValues = values.length - 1, iVarargs = nVarargs - 1; iVarargs >= 0; --iValues, --iVarargs )
                {
                    values[iValues] = value.get( iVarargs );
                }
            }
            else
            {
                values = new MtsValue[nValues];
                values[nValues - 1] = value;
            }
            
            for ( int i = nValues - 2; i >= 0; --i )
            {
                values[i] = frame.pop();
            }
            
            if ( values.length > 1 )
                frame.push( MtsVarargs.of( values ) );
            else if ( values.length == 1 )
                frame.push( MtsVarargs.of( values[0] ) );
            else
                frame.push( EMPTY_VARARGS );
        }
    }
    
    @Override
    public boolean exits()
    {
        return true;
    }
    
    @Override
    public int stackSizeChange()
    {
        return 1 - _count;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "RETURN " + _count;
    }
}
