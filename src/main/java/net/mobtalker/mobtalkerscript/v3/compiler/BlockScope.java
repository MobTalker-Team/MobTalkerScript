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
package net.mobtalker.mobtalkerscript.v3.compiler;

import java.util.LinkedList;

import net.mobtalker.mobtalkerscript.v3.LocalDescription;

public class BlockScope
{
    private final BlockScope _parent;
    private final LinkedList<LocalDescription> _locals;
    
    private final SourcePosition _start;
    
    // ========================================
    
    {
        _locals = new LinkedList<>();
    }
    
    public BlockScope( SourcePosition pos )
    {
        this( pos, null );
    }
    
    public BlockScope( SourcePosition pos, BlockScope parent )
    {
        _parent = parent;
        _start = pos;
    }
    
    // ========================================
    
    public BlockScope getParent()
    {
        return _parent;
    }
    
    public SourcePosition getStart()
    {
        return _start;
    }
    
    // ========================================
    
    private boolean contains( String name )
    {
        for ( LocalDescription descr : _locals )
        {
            if ( descr.getName().equals( name ) )
                return true;
        }
        return false;
    }
    
    // ========================================
    
    public void declare( LocalDescription local )
    {
        _locals.addFirst( local );
    }
    
    /**
     * Searches in this and all parent blocks for a local
     */
    public boolean isLocal( String name )
    {
        return contains( name ) || ( ( _parent != null ) && _parent.isLocal( name ) );
    }
    
    /**
     * Searches in this and all parent blocks for a local and returns it.
     */
    public LocalDescription getLocal( String name )
    {
        for ( LocalDescription descr : _locals )
        {
            if ( descr.getName().equals( name ) )
                return descr;
        }
        
        if ( _parent == null )
            throw new IllegalArgumentException();
        
        return _parent.getLocal( name );
    }
    
    /**
     * Searches in this and all parent blocks for a local and returns its index.
     */
    public int getIndex( String name )
    {
        for ( LocalDescription descr : _locals )
        {
            if ( descr.getName().equals( name ) )
                return descr.getIndex();
        }
        
        if ( _parent == null )
            throw new IllegalArgumentException();
        
        return _parent.getIndex( name );
    }
}
