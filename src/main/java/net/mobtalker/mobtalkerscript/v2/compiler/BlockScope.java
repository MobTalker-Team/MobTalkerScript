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
package net.mobtalker.mobtalkerscript.v2.compiler;

import java.util.List;

import net.mobtalker.mobtalkerscript.v2.*;

import com.google.common.collect.Lists;

public class BlockScope
{
    private final BlockScope _parent;
    private final List<LocalDescription> _locals;
    
    // ========================================
    
    {
        _locals = Lists.newArrayList();
    }
    
    public BlockScope()
    {
        this( null );
    }
    
    public BlockScope( BlockScope parent )
    {
        _parent = parent;
    }
    
    // ========================================
    
    public BlockScope getParent()
    {
        return _parent;
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
    
    public boolean canDeclare( String name )
    {
        return !contains( name );
    }
    
    public void declare( LocalDescription local )
    {
        if ( !canDeclare( local.getName() ) )
            throw new ScriptParserException( "Tried to declare local variable '%s' twice", local.getName() );
        
        _locals.add( local );
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
