/*
 * Copyright (C) 2013-2020 Chimaine, MobTalkerScript contributors
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
package net.mobtalker.mobtalkerscript.v3.value;

import java.util.Iterator;

public final class MtsTailcall extends MtsVarargs
{
    private MtsValue _target;
    private MtsVarargs _args;
    
    // ========================================
    
    public MtsTailcall( MtsValue target, MtsVarargs args )
    {
        _target = target;
        _args = args;
    }
    
    // ========================================
    
    public MtsValue getTarget()
    {
        return _target;
    }
    
    public MtsVarargs getArguments()
    {
        return _args;
    }
    
    // ========================================
    
    @Override
    public MtsVarargs subArgs( int start, int end )
    {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public MtsValue get( int i )
    {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public MtsValue[] toArray()
    {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int count()
    {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean isEmpty()
    {
        throw new UnsupportedOperationException();
    }
    
    // ========================================
    
    /**
     * Executes this tail call and any subsequent returned tail calls.
     */
    public MtsVarargs evaluate()
    {
        if ( _target instanceof MtsTailcall )
        {
            MtsValue result = ( (MtsTailcall) _target ).evaluate();
            _target = result.isVarArgs() ? result.get() : result;
        }
        
        MtsVarargs result = _target.call( _args );
        
        while ( result instanceof MtsTailcall )
        {
            MtsTailcall next = (MtsTailcall) result;
            result = next.getTarget().call( next.getArguments() );
        }
        
        _target = null;
        _args = null;
        return result;
    }
    
    // ========================================
    
    @Override
    public Iterator<MtsValue> iterator()
    {
        throw new UnsupportedOperationException();
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        if ( _target == null )
            return "tailcall";
        else
            return "tailcall[" + _target + "(" + _args + ")]";
    }
}
