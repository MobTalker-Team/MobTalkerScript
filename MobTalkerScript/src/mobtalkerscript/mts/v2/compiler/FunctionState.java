package mobtalkerscript.mts.v2.compiler;

import static com.google.common.base.Preconditions.*;
import static mobtalkerscript.mts.v2.instruction.InstructionCache.*;

import java.util.*;
import java.util.Queue;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.instruction.*;
import mobtalkerscript.mts.v2.value.*;
import mobtalkerscript.util.Stack;

import com.google.common.collect.*;

public class FunctionState
{
    private final FunctionState _parent;
    private final List<FunctionState> _childs;
    
    private final List<MtsValue> _constants;
    private final List<LocalDescription> _locals;
    private final List<ExternalDescription> _externals;
    
    private final LinkedList<MtsInstruction> _instructions;
    private final Queue<PendingJump> _pendingJumps;
    
    private final Map<String, CompilerLabel> _labels;
    
    private final String _name;
    private final String _sourceFile;
    private final int _sourceLineStart;
    private final int _sourceLineEnd;
    private final LinkedList<SourcePosition> _lineNumbers;
    
    private BlockScope _block;
    private final Stack<LoopState> _loops;
    private final Stack<ConditionalState> _ifElses;
    
    // ========================================
    
    {
        _childs = Lists.newArrayList();
        
        _constants = Lists.newArrayList();
        _locals = Lists.newArrayList();
        _externals = Lists.newArrayList();
        
        _instructions = Lists.newLinkedList();
        _pendingJumps = Lists.newLinkedList();
        
        _labels = Maps.newHashMapWithExpectedSize( 2 );
        
        _lineNumbers = Lists.newLinkedList();
        
        _block = new BlockScope();
        _loops = Stack.newStack();
        _ifElses = Stack.newStack();
    }
    
    public FunctionState( FunctionState parent,
                          String name,
                          String sourceFile,
                          int sourceLineStart,
                          int sourceLineEnd )
    {
        _parent = parent;
        _name = name;
        
        _sourceFile = sourceFile;
        _sourceLineStart = sourceLineStart;
        _sourceLineEnd = sourceLineEnd;
    }
    
    // ========================================
    
    public FunctionState getParent()
    {
        return _parent;
    }
    
    public int addChild( FunctionState child )
    {
        _childs.add( child );
        return _childs.size() - 1;
    }
    
    public List<FunctionState> getChilds()
    {
        return _childs;
    }
    
    // ========================================
    
    private int addInstruction( MtsInstruction instr )
    {
        return addInstruction( instr, _lineNumbers.getLast() );
    }
    
    public int addInstruction( MtsInstruction instr, SourcePosition pos )
    {
        _instructions.add( instr );
        _lineNumbers.add( pos );
        
        return currentIndex();
    }
    
    public int currentIndex()
    {
        return _instructions.size() - 1;
    }
    
    // ========================================
    
    public void addLabel( String name )
    {
        checkArgument( !_labels.containsKey( name ), "Label %s already exists!", name );
        
        CompilerLabel label = new CompilerLabel();
        label.setTarget( currentIndex() + 1 );
        
        _labels.put( name, label );
    }
    
    public CompilerLabel getLabel( String name )
    {
        CompilerLabel label = _labels.get( name );
        if ( label == null )
        {
            label = new CompilerLabel();
            _labels.put( name, label );
        }
        
        return label;
    }
    
    public void gotoLabel( String name )
    {
        CompilerLabel label = getLabel( name );
        MtsJumpInstruction instr = InstrJump();
        
        int index = addInstruction( instr );
        label.addGoto( new PendingJump( instr, index ) );
    }
    
    // ========================================
    
    public void enterBlock()
    {
        _block = new BlockScope( _block );
    }
    
    public void exitBlock()
    {
        checkNotNull( _block.getParent(), "Tried to leave function scope!" );
        _block = _block.getParent();
    }
    
    // ========================================
    
    public void enterLoop()
    {
        _loops.push( new LoopState( currentIndex() + 1 ) );
    }
    
    /**
     * Marks the last added instruction as a pending loop break.
     */
    public void markBreak()
    {
        checkState( !_loops.isEmpty(), "There is no loop to break!" );
        checkState( _instructions.getLast() instanceof MtsJumpInstruction,
                    "Last added instruction is not a jump instruction!" );
        
        LoopState loop = _loops.peek();
        MtsJumpInstruction instr = (MtsJumpInstruction) _instructions.getLast();
        
        loop.addBreak( new PendingJump( instr, currentIndex() ) );
    }
    
    public void exitLoop()
    {
        checkState( !_loops.isEmpty(), "There is no loop to exit!" );
        checkState( _instructions.getLast() instanceof MtsJumpInstruction,
                    "Last added instruction is not a jump instruction!" );
        
        LoopState loop = _loops.pop();
        MtsJumpInstruction instr = (MtsJumpInstruction) _instructions.getLast();
        
        instr.setTarget( currentIndex(), loop.firstIndex() );
        loop.setBreakTarget( currentIndex() + 1 );
    }
    
    // ========================================
    
    public void enterNumericForLoop( String loopVariable )
    {
        int loopIndex = declareLocal( loopVariable ).getIndex();
        int limitIndex = declareLocal().getIndex();
        int stepIndex = declareLocal().getIndex();
        
        if ( !( ( stepIndex == ( limitIndex + 1 ) ) && ( limitIndex == ( loopIndex + 1 ) ) ) )
            throw new AssertionError( String.format( "Loop variable indices are not consecutive! (%s,%s,%s)",
                                                     loopIndex,
                                                     limitIndex,
                                                     stepIndex ) );
        
        addInstruction( new InstrNForPrep( loopIndex ) );
        
        enterLoop();
        addInstruction( new InstrNForLoop( loopIndex ) );
        markBreak();
    }
    
    // ========================================
    
    public void enterIfThenElse()
    {
        _ifElses.push( new ConditionalState() );
    }
    
    /**
     * Signals the begin of a condition.
     */
    public void enterIfCondition()
    {
        checkState( !_ifElses.isEmpty(), "Not inside an IfThenElse!" );
        
        ConditionalState block = _ifElses.peek();
        block.markBeginNext( currentIndex() + 1 );
    }
    
    public void endIfCondition()
    {
        checkState( !_ifElses.isEmpty(), "Not inside an IfThenElse!" );
        
        ConditionalState block = _ifElses.peek();
        MtsJumpInstruction jump = InstrTest();
        
        addInstruction( jump );
        block.setPendingNext( new PendingJump( jump, currentIndex() ) );
    }
    
    /**
     * Adds a jump to the first instruction after the conditional.
     */
    public void endThenBlock()
    {
        checkState( !_ifElses.isEmpty(), "Not inside an IfThenElse!" );
        
        ConditionalState block = _ifElses.peek();
        MtsJumpInstruction jump = InstrJump();
        
        addInstruction( jump );
        block.addPendingExit( new PendingJump( jump, currentIndex() ) );
    }
    
    public void enterElseBlock()
    {
        checkState( !_ifElses.isEmpty(), "Not inside an IfThenElse!" );
        
        ConditionalState block = _ifElses.peek();
        block.markBeginNext( currentIndex() + 1 );
    }
    
    /**
     * Signals the exit from the current conditional.
     */
    public void exitIfThenElse()
    {
        checkState( !_ifElses.isEmpty(), "Not inside an IfThenElse!" );
        
        ConditionalState block = _ifElses.pop();
        block.setExitTarget( currentIndex() + 1 );
    }
    
    // ========================================
    
    public void markPendingJump()
    {
        checkState( _instructions.getLast() instanceof MtsJumpInstruction,
                    "Last added instruction is not a jump instruction!" );
        
        MtsJumpInstruction jump = (MtsJumpInstruction) _instructions.getLast();
        _pendingJumps.add( new PendingJump( jump, currentIndex() ) );
    }
    
    public void setPendingJump()
    {
        checkState( !_pendingJumps.isEmpty(), "There is no pending jump!" );
        
        PendingJump jump = _pendingJumps.remove();
        jump.setTarget( currentIndex() );
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
    
    public LocalDescription declareLocal( String name )
    {
        int index = _locals.size();
        LocalDescription local = new LocalDescription( name, index, currentIndex() );
        _locals.add( local );
        
        _block.declare( local );
        
        return local;
    }
    
    /**
     * Declares an anonymous local variable. This variable has no scope and can only be referenced by its index.
     */
    public LocalDescription declareLocal()
    {
        int index = _locals.size();
        LocalDescription local = new LocalDescription( "$L" + index, index, currentIndex() );
        _locals.add( local );
        
        return local;
    }
    
    public int getLocalIndex( String name )
    {
        LocalDescription local = _block.getLocal( name );
        local.setEnd( _instructions.size() - 1 );
        return local.getIndex();
    }
    
//    protected int getLocalIndexSilent( String name )
//    {
//        return _block.getLocal( name ).getIndex();
//    }
    
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
            throw new IllegalArgumentException( name + " is a global!" );
        
        // Create a new external
        int index = _externals.size();
        int parentIndex;
        boolean isParentLocal;
        
        // Get the external index from our parent. If it's not a local of our parent, call this method recursively on it.
        if ( isParentLocal = _parent.isLocal( name ) )
            parentIndex = _parent.getLocalIndex( name );
        else
            parentIndex = _parent.getExternalIndex( name );
        
        _externals.add( new ExternalDescription( name, index, parentIndex, isParentLocal ) );
        return index;
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
    
    /**
     * Forcibly declares an external. Should only be used for the main function of a chunk and {@value CompilerConstants#ENV}.
     */
    public void addExternal( ExternalDescription descr )
    {
        _externals.add( descr );
    }
    
    // ========================================
    
    public MtsFunctionPrototype createPrototype()
    {
        // Ensure that we are in the proper state
        checkState( _block.getParent() == null, "Not in the outermost function scope!" );
        checkState( _pendingJumps.isEmpty(), "Didn't close all pending jumps!" );
        checkState( _loops.isEmpty(), "Didn't close all loops!" );
        checkState( _ifElses.isEmpty(), "Didn't close all IfElse!" );
        
        int curStackSize = 0;
        int maxStackSize = 0;
        for ( MtsInstruction instr : _instructions )
        {
            curStackSize += instr.stackSizeChange();
            maxStackSize = Math.max( curStackSize, maxStackSize );
        }
        
        MtsFunctionPrototype p = new MtsFunctionPrototype( Lists.newArrayList( _instructions ),
                                                           maxStackSize,
                                                           Lists.newArrayList( _constants ),
                                                           Lists.newArrayList( _externals ),
                                                           _locals,
                                                           _name,
                                                           _lineNumbers,
                                                           _sourceFile,
                                                           _sourceLineStart,
                                                           _sourceLineEnd );
        
        for ( FunctionState child : _childs )
        {
            p.addNestedPrototype( child.createPrototype() );
        }
        
        return p;
    }
    
}
