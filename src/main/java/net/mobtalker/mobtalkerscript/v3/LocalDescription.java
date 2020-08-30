/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3;

/**
 * Debug information about a local variable
 */
public class LocalDescription extends VariableDescription
{
    private final int _start;
    private int _end;
    
    // ========================================
    
    public LocalDescription( String name, int index, int start )
    {
        super( name, index );
        _start = start;
    }
    
    public LocalDescription( String name, int index, int start, int end )
    {
        super( name, index );
        _start = start;
        _end = end;
    }
    
    // ========================================
    
    /**
     * First instruction index where this local is defined
     */
    public int getStart()
    {
        return _start;
    }
    
    /**
     * Last instruction index where this local is defined
     */
    public int getEnd()
    {
        return _end;
    }
    
    public void setEnd( int end )
    {
        _end = end;
    }
}
