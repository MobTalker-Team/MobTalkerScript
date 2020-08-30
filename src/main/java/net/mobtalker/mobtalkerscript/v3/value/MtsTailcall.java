/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
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
