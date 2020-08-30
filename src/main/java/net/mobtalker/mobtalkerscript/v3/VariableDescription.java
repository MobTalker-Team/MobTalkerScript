/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3;

public abstract class VariableDescription
{
    private final String _name;
    private final int _index;
    
    // ========================================
    
    public VariableDescription( String name, int index )
    {
        _name = name;
        _index = index;
    }
    
    // ========================================
    
    public String getName()
    {
        return _name;
    }
    
    public int getIndex()
    {
        return _index;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return _name;
    }
}
