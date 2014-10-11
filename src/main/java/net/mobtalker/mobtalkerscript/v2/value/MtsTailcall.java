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
package net.mobtalker.mobtalkerscript.v2.value;

public class MtsTailcall extends MtsVarArgs
{
    private MtsValue _target;
    private MtsVarArgs _args;
    private final int _nResults;
    private MtsValue _result;
    
    // ========================================
    
    public MtsTailcall( MtsValue target, MtsVarArgs args, int nResults )
    {
        _target = target;
        _args = args;
        _nResults = nResults;
    }
    
    // ========================================
    
    public MtsValue getTarget()
    {
        return _target;
    }
    
    public MtsVarArgs getArguments()
    {
        return _args;
    }
    
    // ========================================
    
    @Override
    public MtsVarArgs subArgs( int start, int end )
    {
        if ( _result == null )
            evaluate();
        
        if ( _result.isVarArgs() )
            return _result.asVarArgs().subArgs( start, end );
        else
            return MtsVarArgs.of( _result );
    }
    
    @Override
    public MtsValue get( int i )
    {
        if ( _result == null )
            evaluate();
        
        return _result.get( i );
    }
    
    @Override
    public MtsValue[] toArray()
    {
        if ( _result == null )
            evaluate();
        
        if ( _result.isVarArgs() )
            return _result.asVarArgs().toArray();
        else
            return new MtsValue[] { _result };
    }
    
    @Override
    public int count()
    {
        return _nResults;
    }
    
    @Override
    public boolean isEmpty()
    {
        return _nResults <= 0;
    }
    
    // ========================================
    
    /**
     * Executes this tail call and any subsequent returned tail calls.
     */
    public void evaluate()
    {
        MtsValue result = _target.call( _args );
        
        while ( result instanceof MtsTailcall )
        {
            MtsTailcall next = (MtsTailcall) result;
            result = next.getTarget().call( next.getArguments() );
        }
        
        _target = null;
        _args = null;
        _result = result;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "tailcall";
    }
}
