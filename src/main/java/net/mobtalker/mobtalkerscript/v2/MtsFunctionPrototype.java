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
package net.mobtalker.mobtalkerscript.v2;

import static com.google.common.base.Preconditions.*;

import java.util.List;

import net.mobtalker.mobtalkerscript.v2.compiler.SourcePosition;
import net.mobtalker.mobtalkerscript.v2.instruction.MtsInstruction;
import net.mobtalker.mobtalkerscript.v2.value.MtsValue;

import com.google.common.collect.Lists;

public class MtsFunctionPrototype
{
    private final List<MtsInstruction> _instructions;
    private final int _maxStackSize;
    
    private final int _nParams;
    private final List<MtsValue> _constants;
    private final List<ExternalDescription> _externals;
    private final List<LocalDescription> _locals;
    
    private final List<MtsFunctionPrototype> _nestedPrototypes;
    
    // Debug information
    private final String _name;
    private final List<SourcePosition> _lineNumbers;
    private final String _sourceName;
    private final int _sourceLineStart;
    private final int _sourceLineEnd;
    
    // ========================================
    
    public MtsFunctionPrototype( List<MtsInstruction> instructions,
                                 int maxStackSize,
                                 int nParams,
                                 List<MtsValue> constants,
                                 List<ExternalDescription> externals,
                                 List<LocalDescription> locals,
                                 String name,
                                 List<SourcePosition> lineNumbers,
                                 String sourceName,
                                 int sourceStart,
                                 int sourceEnd )
    {
        checkNotNull( instructions );
        checkArgument( 0 <= maxStackSize, "Stack size cannot be negative: " + maxStackSize );
        checkArgument( maxStackSize <= 0xFE, "Stack size exceeds maximum " + maxStackSize );
        checkArgument( 0 <= nParams, "Parameter count cannot be negative: " + nParams );
        
        _instructions = instructions;
        _maxStackSize = maxStackSize;
        
        _nParams = nParams;
        _constants = constants;
        _externals = externals;
        _locals = locals;
        
        _nestedPrototypes = Lists.newArrayList();
        
        _name = name;
        _lineNumbers = lineNumbers;
        _sourceName = sourceName;
        _sourceLineStart = sourceStart;
        _sourceLineEnd = sourceEnd;
    }
    
    // ========================================
    
    public List<MtsInstruction> getInstructions()
    {
        return _instructions;
    }
    
    public int getMaxStackSize()
    {
        return _maxStackSize;
    }
    
    public int getParameterCount()
    {
        return _nParams;
    }
    
    // ========================================
    
    public int getConstantCount()
    {
        return _constants.size();
    }
    
    public MtsValue getConstant( int index )
    {
        return _constants.get( index );
    }
    
    // ========================================
    
    public int getExternalCount()
    {
        return _externals.size();
    }
    
    public ExternalDescription getExternalDescription( int index )
    {
        return _externals.get( index );
    }
    
    // ========================================
    
    public int getLocalCount()
    {
        return _locals.size();
    }
    
    public LocalDescription getLocalDescription( int index )
    {
        return _locals.get( index );
    }
    
    // ========================================
    
    public void addNestedPrototype( MtsFunctionPrototype prototype )
    {
        _nestedPrototypes.add( prototype );
    }
    
    public MtsFunctionPrototype getNestedPrototype( int i )
    {
        return _nestedPrototypes.get( i );
    }
    
    // ========================================
    
    public String getName()
    {
        return _name;
    }
    
    public String getSource()
    {
        return _sourceName;
    }
    
    public SourcePosition getSourcePosition( int instruction )
    {
        checkElementIndex( instruction, _lineNumbers.size() );
        
        return _lineNumbers.get( instruction );
    }
    
    // ========================================
    
    private void printLocals( StringBuilder s, String prefix, boolean full )
    {
        s.append( '\n' ).append( prefix );
        s.append( "locals: " ).append( _locals.size() );
        
        if ( full )
        {
            for ( LocalDescription descr : _locals )
            {
                s.append( '\n' ).append( prefix ).append( "    " );
                s.append( "[" ).append( descr.getIndex() ).append( "] " ).append( descr.getName() );
            }
        }
    }
    
    private void printExternals( StringBuilder s, String prefix, boolean full )
    {
        s.append( '\n' ).append( prefix );
        s.append( "externals: " ).append( _externals.size() );
        
        if ( full )
        {
            for ( ExternalDescription descr : _externals )
            {
                s.append( '\n' ).append( prefix ).append( "    " );
                s.append( "[" ).append( descr.getIndex() ).append( "] " ).append( descr.getName() );
            }
        }
    }
    
    private void printConstants( StringBuilder s, String prefix, boolean full )
    {
        s.append( '\n' ).append( prefix );
        s.append( "constants: " ).append( _constants.size() );
        
        if ( full )
        {
            for ( int i = 0; i < _constants.size(); i++ )
            {
                MtsValue constant = _constants.get( i );
                s.append( '\n' ).append( prefix ).append( "    " );
                s.append( "[" ).append( i ).append( "] " ).append( constant );
            }
        }
    }
    
    private void printInstructions( StringBuilder s, String prefix, boolean full )
    {
        s.append( '\n' ).append( prefix );
        s.append( "instructions: " ).append( _instructions.size() );
        
        if ( full )
        {
            for ( int i = 0; i < _instructions.size(); i++ )
            {
                MtsInstruction instr = _instructions.get( i );
                s.append( '\n' ).append( prefix ).append( "    " );
                s.append( "[" ).append( i ).append( "] " ).append( instr.toString( this ) );
            }
        }
    }
    
    @Override
    public String toString()
    {
        return toString( "", false, false );
    }
    
    public String toString( boolean full, boolean includeChildren )
    {
        return toString( "", full, includeChildren );
    }
    
    /* package */String toString( String prefix, boolean full, boolean includeChildren )
    {
        StringBuilder s = new StringBuilder();
        
        s.append( "function: " ).append( _name ).append( '\n' );
        s.append( "    source: " )
         .append( _sourceName )
         .append( ':' )
         .append( _sourceLineStart )
         .append( '-' )
         .append( _sourceLineEnd )
         .append( '\n' );
        s.append( "    stacksize: " ).append( _maxStackSize );
        s.append( "    parameters: " ).append( _nParams );
        
        printLocals( s, "    " + prefix, full );
        printExternals( s, "    " + prefix, full );
        printConstants( s, "    " + prefix, full );
        printInstructions( s, "    " + prefix, full );
        
        if ( includeChildren )
        {
            for ( MtsFunctionPrototype child : _nestedPrototypes )
            {
                s.append( "\n\n" );
                s.append( child.toString( prefix + "    ", full, true ) );
            }
        }
        
        return s.toString();
    }
}
