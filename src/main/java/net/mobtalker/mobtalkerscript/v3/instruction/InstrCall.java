/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.instruction;

import java.io.*;

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
        
        pushResults( frame, getResults( target, args ) );
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
