/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3;

import java.util.ArrayList;
import java.util.List;

import net.mobtalker.mobtalkerscript.v3.compiler.SourcePosition;
import net.mobtalker.mobtalkerscript.v3.instruction.MtsInstruction;
import net.mobtalker.mobtalkerscript.v3.value.MtsValue;

import static org.apache.commons.lang3.Validate.*;

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
    
    public MtsFunctionPrototype( String name, List<MtsValue> constants,
                                 List<LocalDescription> locals, List<ExternalDescription> externals,
                                 int nParams,
                                 boolean hasVarargs,
                                 int maxStackSize,
                                 List<MtsInstruction> instructions,
                                 List<SourcePosition> lineNumbers,
                                 String sourceName, int sourceStart, int sourceEnd )
    {
        notNull( instructions );
        isTrue( maxStackSize >= 0, "Stack size cannot be negative: %d", maxStackSize);
        isTrue( maxStackSize < 0xFF, "Stack size exceeds maximum: %d", maxStackSize);
        isTrue( 0 <= nParams, "Parameter count cannot be negative: " + nParams );
        
        _instructions = instructions.toArray( new MtsInstruction[instructions.size()] );
        _maxStackSize = maxStackSize;
        
        _nParams = nParams;
        _hasVarargs = hasVarargs;
        _constants = constants.toArray( new MtsValue[constants.size()] );
        _locals = locals.toArray( new LocalDescription[locals.size()] );
        _externals = externals.toArray( new ExternalDescription[externals.size()] );
        
        _nestedPrototypes = new ArrayList<>();
        
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
        validIndex(_lineNumbers, instruction);
        
        return _lineNumbers.get( instruction );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return getSource() + ":" + getName();
    }
}
