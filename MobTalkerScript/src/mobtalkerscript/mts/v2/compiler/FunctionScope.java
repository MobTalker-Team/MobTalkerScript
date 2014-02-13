package mobtalkerscript.mts.v2.compiler;

import static com.google.common.base.Preconditions.*;

import java.util.*;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.instruction.*;
import mobtalkerscript.mts.v2.value.*;

import com.google.common.collect.*;

public class FunctionScope
{
    private final FunctionScope _parent;
    private final List<FunctionScope> _childs;
    
    private final List<MtsValue> _constants;
    private final List<ExternalDescription> _externals;
    private final List<LocalDescription> _locals;
    
    private final List<MtsInstruction> _instructions;
    private final Queue<Integer> _marker;
    
    private final String _name;
    private final String _sourceFile;
    private final List<SourcePosition> _lineNumbers;
    
    private BlockScope _block;
    
    // ========================================
    
    {
        _childs = Lists.newArrayList();
        
        _constants = Lists.newArrayList();
        _externals = Lists.newArrayList();
        _locals = Lists.newArrayList();
        
        _instructions = Lists.newArrayList();
        _marker = Lists.newLinkedList();
        
        _lineNumbers = Lists.newArrayList();
        
        _block = new BlockScope();
        
        // External 0 is always _ENV
        _externals.add( new ExternalDescription( MtsCompiler.ENV, 0 ) );
    }
    
    public FunctionScope( FunctionScope parent, String name, String sourceFile )
    {
        _parent = parent;
        _name = name;
        _sourceFile = sourceFile;
    }
    
    // ========================================
    
    public FunctionScope getParent()
    {
        return _parent;
    }
    
    public int addChild( FunctionScope scope )
    {
        _childs.add( scope );
        return _childs.size() - 1;
    }
    
    public List<FunctionScope> getChilds()
    {
        return _childs;
    }
    
    // ========================================
    
    public void enterBlock()
    {
        _block = new BlockScope( _block );
    }
    
    public void leaveBlock()
    {
        checkNotNull( _block.getParent(), "Tried to leave function scope!" );
        _block = _block.getParent();
    }
    
    // ========================================
    
    public void addInstruction( MtsInstruction instr, int line, int coloum )
    {
        _instructions.add( instr );
        _lineNumbers.add( new SourcePosition( line, coloum ) );
    }
    
    public int getInstructionIndex()
    {
        return _instructions.size() - 1;
    }
    
    public void markJumpOrigin()
    {
        int index = getInstructionIndex();
        if ( !( _instructions.get( index ) instanceof MtsJumpInstruction ) )
            throw new IllegalStateException();
        
        _marker.add( index );
    }
    
    public void setJumpTarget()
    {
        int origin = _marker.remove();
        MtsJumpInstruction instr = (MtsJumpInstruction) _instructions.get( origin );
        
        int distance = getInstructionIndex() - origin;
        
        instr.setOffset( distance );
    }
    
    // ========================================
    
    public int getConstantIndex( MtsValue value )
    {
        int result = _constants.indexOf( value );
        
        if ( result < 0 )
        {
            _constants.add( value );
            result = _constants.size() - 1;
        }
        
        return result;
    }
    
    // ========================================
    
    public boolean isLocal( String name )
    {
        return _block.isLocal( name );
    }
    
    public int declareLocal( String name )
    {
        int index = _locals.size();
        LocalDescription local = new LocalDescription( name, index, getInstructionIndex() );
        _locals.add( local );
        
        _block.declare( local );
        
        return index;
    }
    
    public int getLocalIndex( String name )
    {
        LocalDescription local = _block.getLocal( name );
        local.setEnd( _instructions.size() - 1 );
        return local.getIndex();
    }
    
    protected int getLocalIndexSilent( String name )
    {
        return _block.getLocal( name ).getIndex();
    }
    
    // ========================================
    
    /**
     * Gets the index for a given identifier that is considered to be an external. <br/>
     * If there is no external definition yet a new one will be created.
     */
    public int getExternalIndex( String name )
    {
        // Is it already an external?
        for ( int i = 0; i < _externals.size(); i++ )
        {
            ExternalDescription descr = _externals.get( i );
            if ( descr.getName().equals( name ) )
                return i;
        }
        
        // If we do not have a parent, we cannot create an external for that name
        if ( _parent == null )
            throw new IllegalArgumentException();
        
        // Get the external index from our parent. If it's not a local of our parent, call this method recursively on it.
        int index = _parent.isLocal( name ) //
                ? _parent.getLocalIndex( name )
                : _parent.getExternalIndex( name );
        
        _externals.add( new ExternalDescription( name, index ) );
        return _externals.size() - 1;
    }
    
    /**
     * Determines if the given identifier references an external.
     */
    public boolean isExternal( String name )
    {
        if ( isLocal( name ) )
            return false;
        
        for ( ExternalDescription external : _externals )
        {
            if ( external.getName().equals( name ) )
                return true;
        }
        
        if ( _parent == null )
            return false;
        
        return _parent.isLocal( name ) || _parent.isExternal( name );
    }
    
    // ========================================
    
    public MtsFunctionPrototype createPrototype()
    {
        int curStackSize = 0;
        int maxStackSize = 0;
        for ( MtsInstruction instr : _instructions )
        {
            curStackSize += instr.stackSizeChange();
            maxStackSize = Math.max( curStackSize, maxStackSize );
        }
        
        MtsFunctionPrototype p = new MtsFunctionPrototype( Lists.newArrayList( _instructions ),
                                                           maxStackSize,
                                                           _locals.size(),
                                                           Lists.newArrayList( _constants ),
                                                           Lists.newArrayList( _externals ),
                                                           _name,
                                                           _lineNumbers,
                                                           _sourceFile,
                                                           0,
                                                           0,
                                                           _locals );
        
        for ( FunctionScope child : _childs )
        {
            p.addNestedPrototype( child.createPrototype() );
        }
        
        return p;
    }
    
}
