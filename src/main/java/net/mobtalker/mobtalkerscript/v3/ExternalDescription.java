/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3;

/**
 * Essential information about externals of a function.
 */
public class ExternalDescription extends VariableDescription
{
    private final int _parentIndex;
    private final boolean _parentLocal;
    
    // ========================================
    
    public ExternalDescription( String name, int index, boolean parentLocal, int parentIndex )
    {
        super( name, index );
        _parentIndex = parentIndex;
        _parentLocal = parentLocal;
    }
    
    // ========================================
    
    /**
     * If this external references a local or an external of the parent frame
     */
    public boolean isParentLocal()
    {
        return _parentLocal;
    }
    
    /**
     * The index of the local or external that is referenced by this external
     */
    public int getParentIndex()
    {
        return _parentIndex;
    }
}
