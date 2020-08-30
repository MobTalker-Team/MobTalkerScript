/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
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
