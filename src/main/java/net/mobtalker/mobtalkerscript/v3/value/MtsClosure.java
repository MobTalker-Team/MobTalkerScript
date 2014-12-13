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
package net.mobtalker.mobtalkerscript.v3.value;

import static com.google.common.base.Preconditions.*;
import net.mobtalker.mobtalkerscript.v3.*;
import net.mobtalker.mobtalkerscript.v3.compiler.SourcePosition;

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
        checkNotNull( prototype );
        checkNotNull( externals );
        
        _prototype = prototype;
        _externals = externals;
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
        catch ( MtsScriptRuntimeException ex )
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
