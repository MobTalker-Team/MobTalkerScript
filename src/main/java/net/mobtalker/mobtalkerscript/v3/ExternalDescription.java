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
