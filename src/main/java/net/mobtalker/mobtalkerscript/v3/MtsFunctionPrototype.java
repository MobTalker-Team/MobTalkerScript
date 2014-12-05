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

import static com.google.common.base.Preconditions.*;

import java.util.List;

import net.mobtalker.mobtalkerscript.v3.compiler.SourcePosition;
import net.mobtalker.mobtalkerscript.v3.instruction.MtsInstruction;
import net.mobtalker.mobtalkerscript.v3.value.MtsValue;

import com.google.common.collect.Lists;

public class MtsFunctionPrototype
{
    private final MtsInstruction[] _instructions;
    private final int _maxStackSize;
    
    private final int _nParams;
    private final boolean _hasVarargs;
    private final MtsValue[] _constants;
    private final LocalDescription[] _locals;
    private final ExternalDescription[] _externals;
    
    private final List<MtsFunctionPrototype> _nestedPrototypes;
    
    // Debug information
    private final String _name;
    private final List<SourcePosition> _lineNumbers;
    private final String _sourceName;
    private final int _sourceLineStart;
    private final int _sourceLineEnd;
    
    // ========================================
    
    public MtsFunctionPrototype( List<MtsInstruction> instructions, int maxStackSize,
                                 int nParams, boolean hasVarargs,
                                 List<MtsValue> constants,
                                 List<LocalDescription> locals,
                                 List<ExternalDescription> externals,
                                 String name,
                                 List<SourcePosition> lineNumbers,
                                 String sourceName, int sourceStart, int sourceEnd )
    {
        checkNotNull( instructions );
        checkArgument( 0 <= maxStackSize, "Stack size cannot be negative: " + maxStackSize );
        checkArgument( maxStackSize <= 0xFE, "Stack size exceeds maximum " + maxStackSize );
        checkArgument( 0 <= nParams, "Parameter count cannot be negative: " + nParams );
        
        _instructions = instructions.toArray( new MtsInstruction[instructions.size()] );
        _maxStackSize = maxStackSize;
        
        _nParams = nParams;
        _hasVarargs = hasVarargs;
        _constants = constants.toArray( new MtsValue[constants.size()] );
        _locals = locals.toArray( new LocalDescription[locals.size()] );
        _externals = externals.toArray( new ExternalDescription[externals.size()] );
        
        _nestedPrototypes = Lists.newArrayList();
        
        _name = name;
        _lineNumbers = lineNumbers;
        _sourceName = sourceName;
        _sourceLineStart = sourceStart;
        _sourceLineEnd = sourceEnd;
    }
    
    // ========================================
    
    public MtsInstruction[] getInstructions()
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
        return _constants.length;
    }
    
    public MtsValue getConstant( int index )
    {
        return _constants[index];
    }
    
    // ========================================
    
    public int getExternalCount()
    {
        return _externals.length;
    }
    
    public ExternalDescription getExternalDescription( int index )
    {
        return _externals[index];
    }
    
    // ========================================
    
    public int getLocalCount()
    {
        return _locals.length;
    }
    
    public LocalDescription getLocalDescription( int index )
    {
        return _locals[index];
    }
    
    public boolean hasVarargs()
    {
        return _hasVarargs;
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
    
    public List<MtsFunctionPrototype> getNestedPrototypes()
    {
        return _nestedPrototypes;
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
    
    public int getSourceLineStart()
    {
        return _sourceLineStart;
    }
    
    public int getSourceLineEnd()
    {
        return _sourceLineEnd;
    }
    
    public SourcePosition getSourcePosition( int instruction )
    {
        checkElementIndex( instruction, _lineNumbers.size() );
        
        return _lineNumbers.get( instruction );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return getSource() + ":" + getName();
    }
}
