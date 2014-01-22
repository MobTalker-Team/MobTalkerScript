package mobtalkerscript.misl.v1;

import static com.google.common.base.Preconditions.*;

import java.util.*;

import mobtalkerscript.misl.v1.instructionV2.*;
import mobtalkerscript.misl.v1.value.*;

import com.google.common.collect.*;

public class MislFunctionPrototype
{
    private final List<MislInstruction> _instructions;
    private final int _maxStackSize;
    private final int _localCount;
    
    private final List<MislValue> _constants;
    private final List<ExternalDescription> _externals;
    
    private final List<MislFunctionPrototype> _nestedPrototypes;
    
    // Debug information
    private String _name;
    private String _sourceFile;
    private int _sourceLineStart;
    private int _sourceLineEnd;
    
    // ========================================
    
    public MislFunctionPrototype( List<MislInstruction> instructions,
                                  int maxStackSize,
                                  int localCount,
                                  List<MislValue> constants,
                                  List<ExternalDescription> externals,
                                  String name,
                                  String sourceFile,
                                  int sourceStart,
                                  int sourceEnd )
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
        _sourceFile = sourceFile;
        _sourceLineStart = sourceStart;
        _sourceLineEnd = sourceEnd;
    }
    
    // ========================================
    
    public List<MislInstruction> getInstructions()
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
    
    public List<MislValue> getConstants()
    {
        return _constants;
    }
    
    public List<ExternalDescription> getExternals()
    {
        return _externals;
    }
    
    // ========================================
    
    public void addNestedPrototype( MislFunctionPrototype prototype )
    {
        _nestedPrototypes.add( prototype );
    }
    
    public MislFunctionPrototype getNestedPrototype( int i )
    {
        return _nestedPrototypes.get( i );
    }
    
    // ========================================
    
    public MislClosure createClosure()
    {
        return null;
    }
    
    // ========================================
}
