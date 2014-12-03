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

import net.mobtalker.mobtalkerscript.v3.MtsFrame;
import net.mobtalker.mobtalkerscript.v3.value.*;

import com.google.common.collect.Lists;

public final class InstrTailcall extends MtsInstruction
{
    private final int _nArgs;
    
    // ========================================
    
    /* package */InstrTailcall( int nArgs )
    {
        _nArgs = nArgs;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsVarArgs args = getCallArgs( frame );
        MtsValue target = frame.pop();
        
        frame.push( new MtsTailcall( target, args ) );
    }
    
    protected MtsVarArgs getCallArgs( MtsFrame frame )
    {
        // TODO Not happy with this. See also InstrCall
        
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
    
    // ========================================
    
    @Override
    public boolean exits()
    {
        return false;
    }
    
    @Override
    public int stackSizeChange()
    {
        return 1 - _nArgs;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "TAILCALL " + _nArgs;
    }
}
