package net.mobtalker.mobtalkerscript.v2.compiler;

import java.util.*;

import net.mobtalker.mobtalkerscript.v2.*;

import com.google.common.collect.*;

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
