package mobtalkerscript.mts.v2;

import static com.google.common.base.Preconditions.*;

import java.util.*;

import mobtalkerscript.mts.v2.instruction.*;
import mobtalkerscript.mts.v2.value.*;

import com.google.common.collect.*;

public class MtsFunctionPrototype
{
    private final List<MtsInstruction> _instructions;
    private final int _maxStackSize;
    private final int _localCount;
    
    private final List<MtsValue> _constants;
    private final List<ExternalDescription> _externals;
    
    private final List<MtsFunctionPrototype> _nestedPrototypes;
    
    // Debug information
    private final String _name;
    private final int[] _lineNumbers;
    private final String _sourceFile;
    private final int _sourceLineStart;
    private final int _sourceLineEnd;
    private final List<VariableDescription> _localDescr;
    
    // ========================================
    
    public MtsFunctionPrototype( List<MtsInstruction> instructions,
                                 int maxStackSize,
                                 int localCount,
                                 List<MtsValue> constants,
                                 List<ExternalDescription> externals,
                                 String name,
                                 int[] lineNumbers,
                                 String sourceFile,
                                 int sourceStart,
                                 int sourceEnd,
                                 List<VariableDescription> localDescr )
    {
        checkNotNull( instructions );
        checkArgument( 0 <= maxStackSize, "Stack size cannot be negative" );
        checkArgument( maxStackSize <= 0xFFFF, "Stack size exceeds maximum" );
        
        _instructions = instructions;
        _maxStackSize = maxStackSize;
        _localCount = localCount;
        
        _constants = constants;
        _externals = externals;
        
        _nestedPrototypes = Lists.newArrayList();
        
        _name = name;
        _lineNumbers = lineNumbers;
        _sourceFile = sourceFile;
        _sourceLineStart = sourceStart;
        _sourceLineEnd = sourceEnd;
        _localDescr = localDescr;
    }
    
    // ========================================
    
    public List<MtsInstruction> getInstructions()
    {
        return _instructions;
    }
    
    public int getLocalCount()
    {
        return _localCount;
    }
    
    public int getMaxStackSize()
    {
        return _maxStackSize;
    }
    
    public List<MtsValue> getConstants()
    {
        return _constants;
    }
    
    public List<ExternalDescription> getExternals()
    {
        return _externals;
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
    
    public String getSourceFile()
    {
        return _sourceFile;
    }
    
    public int getLineNumber( int instruction )
    {
        checkElementIndex( instruction, _lineNumbers.length );
        
        return _lineNumbers[instruction];
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        
        s.append( "function " ).append( _name ).append( "\n" );
        s.append( " stack: " ).append( _maxStackSize ).append( "\n" );
        s.append( " locals: " ).append( _localCount ).append( "\n" );
        s.append( " externals: " ).append( _externals.size() ).append( "\n" );
        s.append( " constants: " ).append( _constants.size() );
        
        return super.toString();
    }
}
