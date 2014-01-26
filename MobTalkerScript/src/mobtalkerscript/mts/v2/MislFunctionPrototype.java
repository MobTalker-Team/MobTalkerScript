package mobtalkerscript.mts.v2;

import static com.google.common.base.Preconditions.*;

import java.util.*;

import mobtalkerscript.mts.v2.instruction.*;
import mobtalkerscript.mts.v2.value.*;

import com.google.common.collect.*;

public class MislFunctionPrototype
{
    private final List<MtsInstruction> _instructions;
    private final int _maxStackSize;
    private final int _localCount;
    
    private final List<MtsValue> _constants;
    private final List<ExternalDescription> _externals;
    
    private final List<MislFunctionPrototype> _nestedPrototypes;
    
    // Debug information
    private String _name;
    private String _sourceFile;
    private int _sourceLineStart;
    private int _sourceLineEnd;
    
    // ========================================
    
    public MislFunctionPrototype( List<MtsInstruction> instructions,
                                  int maxStackSize,
                                  int localCount,
                                  List<MtsValue> constants,
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
    
    public void addNestedPrototype( MislFunctionPrototype prototype )
    {
        _nestedPrototypes.add( prototype );
    }
    
    public MislFunctionPrototype getNestedPrototype( int i )
    {
        return _nestedPrototypes.get( i );
    }
    
    // ========================================
}
