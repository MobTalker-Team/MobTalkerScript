/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.instruction;

import java.io.*;
import java.util.*;

import net.mobtalker.mobtalkerscript.v3.MtsFrame;
import net.mobtalker.mobtalkerscript.v3.value.*;

public class InstrNewTable extends MtsInstruction
{
    private final int _nListElements;
    private final int _nMapElements;
    
    // ========================================
    
    /* package */InstrNewTable( int listElements, int mapElements )
    {
        _nListElements = listElements;
        _nMapElements = mapElements;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsTable t = new MtsTable( _nListElements, _nMapElements );
        
        // List elements
        if ( _nListElements > 0 )
        {
            Deque<MtsValue> deque = new LinkedList<>();
            
            { // First value can be varargs
                MtsValue value = frame.pop();
                if ( value.isVarArgs() )
                {
                    unpackVarargs( value.asVarArgs(), deque );
                }
                else
                {
                    deque.addFirst( value );
                }
            }
            
            for ( int i = 1; i < _nListElements; i++ )
            {
                deque.addFirst( frame.pop() );
            }
            
            t.list().addAll( deque );
        }
        
        // Key-Value pairs
        for ( int i = 0; i < _nMapElements; i++ )
        {
            MtsValue v = frame.pop();
            MtsValue k = frame.pop();
            t.map().put( k, v );
        }
        
        frame.push( t );
    }
    
    private static void unpackVarargs( MtsVarargs varargs, Deque<MtsValue> deque )
    {
        int count = varargs.count();
        for ( int i = count - 1; i >= 0; --i )
        {
            deque.addFirst( varargs.get( i ) );
        }
    }
    
    @Override
    public int stackSizeChange()
    {
        return 1 - _nListElements - ( _nMapElements * 2 );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return Instructions.NEWTBL_NAME + " " + _nListElements + " " + _nMapElements;
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.NEWTBL );
        stream.writeByte( _nListElements );
        stream.writeByte( _nMapElements );
    }
}
