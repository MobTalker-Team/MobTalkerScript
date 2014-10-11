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
package net.mobtalker.mobtalkerscript.v2.compiler;

import static com.google.common.base.Preconditions.*;
import static net.mobtalker.mobtalkerscript.util.logging.MtsLog.*;
import static net.mobtalker.mobtalkerscript.v2.instruction.Instructions.*;

import java.util.*;
import java.util.Map.Entry;

import net.mobtalker.mobtalkerscript.util.Stack;
import net.mobtalker.mobtalkerscript.v2.*;
import net.mobtalker.mobtalkerscript.v2.instruction.*;
import net.mobtalker.mobtalkerscript.v2.value.MtsValue;

import com.google.common.collect.*;

public class FunctionState
{
    private final FunctionState _parent;
    private final List<FunctionState> _childs;
    
    private final List<MtsValue> _constants;
    private final List<LocalDescription> _locals;
    private final List<ExternalDescription> _externals;
    
    private final LinkedList<MtsInstruction> _instructions;
    private final Stack<PendingJump> _pendingJumps;
    
    private final Map<String, CompilerLabel> _labels;
    
    private final String _name;
    private final int _nParam;
    
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
        _pendingJumps = Stack.newStack();
        
        _labels = Maps.newHashMapWithExpectedSize( 2 );
        
        _lineNumbers = Lists.newLinkedList();
        
        _block = new BlockScope();
        _loops = Stack.newStack();
        _ifElses = Stack.newStack();
    }
    
    public FunctionState( FunctionState parent, String name, int nParam,
                          String sourceFile, int sourceLineStart, int sourceLineEnd )
    {
        _parent = parent;
        _name = name;
        _nParam = nParam;
        
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
        
        CompilerLog.fine( "  Instruction: %s (%s)", instr, currentIndex() );
        
        return currentIndex();
    }
    
    public int currentIndex()
    {
        return _instructions.size() - 1;
    }
    
    // ========================================
    
    public void addLabel( String name )
    {
        CompilerLabel label = _labels.get( name );
        if ( label == null )
        {
            label = new CompilerLabel();
            _labels.put( name, label );
        }
        else
        {
            checkArgument( label.getTarget() != 0, "label '%s' already exists", name );
        }
        
        label.setTarget( currentIndex() + 1 );
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
        assert _block.getParent() != null : "Tried to leave function scope!";
        
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
        assert !_loops.isEmpty() : "There is no loop to break!";
        assert _instructions.getLast() instanceof MtsJumpInstruction
        /*   */: "Last added instruction is not a jump instruction!";
        
        LoopState loop = _loops.peek();
        MtsJumpInstruction instr = (MtsJumpInstruction) _instructions.getLast();
        
        loop.addBreak( new PendingJump( instr, currentIndex() ) );
    }
    
    public void exitLoop()
    {
        assert !_loops.isEmpty() : "There is no loop to exit!";
        assert _instructions.getLast() instanceof MtsJumpInstruction
        /*   */: "Last added instruction is not a jump instruction!";
        
        LoopState loop = _loops.pop();
        MtsJumpInstruction instr = (MtsJumpInstruction) _instructions.getLast();
        
        instr.setTarget( currentIndex(), loop.firstIndex() );
        loop.setBreakTarget( currentIndex() + 1 );
    }
    
    // ========================================
    
    public void enterNumericForLoop( String loopVariable )
    {
        int loopIndex = declareLocal( loopVariable ).getIndex();
        int limitIndex = declareAnonymousLocal( "limit" ).getIndex();
        int stepIndex = declareAnonymousLocal( "step" ).getIndex();
        
        assert ( stepIndex == ( limitIndex + 1 ) ) && ( limitIndex == ( loopIndex + 1 ) )
        /*   */: String.format( "Loop variable indices are not consecutive! (%s,%s,%s)",
                              loopIndex, limitIndex, stepIndex );
        
        addInstruction( InstrNForPrep( loopIndex ) );
        
        enterLoop();
        addInstruction( InstrNForLoop( loopIndex ) );
        markBreak();
    }
    
    public void enterGenericForLoop( String... loopVars )
    {
        int iterIndex = declareAnonymousLocal( "iter" ).getIndex();
        int stateIndex = declareAnonymousLocal( "state" ).getIndex();
        int indexIndex = declareAnonymousLocal( "index" ).getIndex();
        
        assert ( indexIndex == ( stateIndex + 1 ) ) && ( stateIndex == ( iterIndex + 1 ) )
        /*   */: String.format( "Loop variable indices are not consecutive! (%s,%s,%s)",
                              iterIndex, stateIndex, indexIndex );
        
        for ( int i = 0; i < loopVars.length; i++ )
        {
            String loopVar = loopVars[i];
            int varIndex = declareLocal( loopVar ).getIndex();
            
            assert ( varIndex - i - 1 ) == indexIndex : "Loop variable indices are not consecutive!";
        }
        
        addInstruction( InstrStoreL( indexIndex ) );
        addInstruction( InstrStoreL( stateIndex ) );
        addInstruction( InstrStoreL( iterIndex ) );
        
        enterLoop();
        addInstruction( InstrGForLoop( iterIndex, loopVars.length ) );
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
        assert !_ifElses.isEmpty() : "Not inside an IfThenElse!";
        
        ConditionalState block = _ifElses.peek();
        block.markBeginNext( currentIndex() + 1 );
    }
    
    public void endIfCondition()
    {
        assert !_ifElses.isEmpty() : "Not inside an IfThenElse!";
        
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
        assert !_ifElses.isEmpty() : "Not inside an IfThenElse!";
        
        ConditionalState block = _ifElses.peek();
        MtsJumpInstruction jump = InstrJump();
        
        addInstruction( jump );
        block.addPendingExit( new PendingJump( jump, currentIndex() ) );
    }
    
    public void enterElseBlock()
    {
        assert !_ifElses.isEmpty() : "Not inside an IfThenElse!";
        
        ConditionalState block = _ifElses.peek();
        block.markBeginNext( currentIndex() + 1 );
    }
    
    /**
     * Signals the exit from the current conditional.
     */
    public void exitIfThenElse()
    {
        assert !_ifElses.isEmpty() : "Not inside an IfThenElse!";
        
        ConditionalState block = _ifElses.pop();
        block.setExitTarget( currentIndex() + 1 );
    }
    
    // ========================================
    
    public void markPendingJump()
    {
        assert _instructions.getLast() instanceof MtsJumpInstruction
        /*   */: "Last added instruction is not a jump instruction!";
        
        MtsJumpInstruction jump = (MtsJumpInstruction) _instructions.getLast();
        _pendingJumps.push( new PendingJump( jump, currentIndex() ) );
    }
    
    public void setPendingJump()
    {
        assert !_pendingJumps.isEmpty() : "There is no pending jump!";
        
        PendingJump jump = _pendingJumps.pop();
        jump.setTarget( currentIndex() );
    }
    
    public void setPendingJump( int offset )
    {
        assert !_pendingJumps.isEmpty() : "There is no pending jump!";
        
        PendingJump jump = _pendingJumps.pop();
        jump.setTarget( currentIndex() + offset );
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
    public LocalDescription declareAnonymousLocal( String name )
    {
        int index = _locals.size();
        LocalDescription local = new LocalDescription( "$" + name, index, currentIndex() );
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
    // {
    // return _block.getLocal( name ).getIndex();
    // }
    
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
        checkArgument( _parent != null, name + " is a global!" );
        
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
    
    private int calculateMaxStackSize()
    {
        int curStackSize = 0;
        int maxStackSize = 0;
        for ( MtsInstruction instr : _instructions )
        {
            curStackSize += instr.stackSizeChange();
            maxStackSize = Math.max( curStackSize, maxStackSize );
        }
        
        return maxStackSize;
    }
    
    public MtsFunctionPrototype createPrototype()
    {
        // Ensure that we are in the proper state
        checkState( _block.getParent() == null, "Not in the outermost function scope!" );
        checkState( _pendingJumps.isEmpty(), "Didn't close all pending jumps!" );
        checkState( _loops.isEmpty(), "Didn't close all loops!" );
        checkState( _ifElses.isEmpty(), "Didn't close all IfElse!" );
        
        for ( Entry<String, CompilerLabel> label : _labels.entrySet() )
        {
            if ( label.getValue().getTarget() == 0 )
                throw new IllegalStateException( "unknown label '" + label.getKey() + "'" );
        }
        
        MtsFunctionPrototype p = new MtsFunctionPrototype( ImmutableList.copyOf( _instructions ),
                                                           calculateMaxStackSize(),
                                                           _nParam,
                                                           ImmutableList.copyOf( _constants ),
                                                           ImmutableList.copyOf( _externals ),
                                                           ImmutableList.copyOf( _locals ),
                                                           _name,
                                                           ImmutableList.copyOf( _lineNumbers ),
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
