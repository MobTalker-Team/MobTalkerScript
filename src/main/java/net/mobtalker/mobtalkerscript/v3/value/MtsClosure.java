/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.value;

import net.mobtalker.mobtalkerscript.v3.*;
import net.mobtalker.mobtalkerscript.v3.compiler.SourcePosition;

import static org.apache.commons.lang3.Validate.notNull;

public final class MtsClosure extends MtsFunction
{
    private final MtsFunctionPrototype _prototype;
    private final FrameValue[] _externals;
    
    // ========================================
    
    public MtsClosure( MtsFunctionPrototype prototype, MtsValue env )
    {
        this( prototype, new FrameValue[] { new FrameValue( env ) } );
    }
    
    public MtsClosure( MtsFunctionPrototype prototype, FrameValue[] externals )
    {
        _prototype = notNull( prototype );
        _externals = notNull( externals );
    }
    
    // ========================================
    
    public MtsFunctionPrototype getPrototype()
    {
        return _prototype;
    }
    
    // ========================================
    
    @Override
    public MtsVarargs call( MtsVarargs args )
    {
        MtsFrame frame = new MtsFrame( this, args, _externals );
        try
        {
            return frame.run();
        }
        catch ( MtsRuntimeException ex )
        {
            String source = _prototype.getSource();
            SourcePosition pos = _prototype.getSourcePosition( frame.getInstructionPointer() );
            String name = _prototype.getName();
            
            ex.addStackTraceElement( source, pos, name );
            
            throw ex;
        }
    }
    
    // ========================================
    
    @Override
    public final boolean isClosure()
    {
        return true;
    }
    
    @Override
    public final MtsClosure asClosure()
    {
        return this;
    }
    
    // ========================================
    
    @Override
    public int hashCode()
    {
        return _prototype.hashCode();
    }
    
    @Override
    public boolean equals( Object obj )
    {
        if ( obj == this )
            return true;
        if ( !( obj instanceof MtsClosure ) )
            return false;
        
        return ( (MtsClosure) obj ).asClosure().getPrototype().equals( _prototype );
    }
}
