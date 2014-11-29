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

import static net.mobtalker.mobtalkerscript.util.logging.MtsLog.*;
import static net.mobtalker.mobtalkerscript.v3.value.MtsValue.*;

import java.util.*;

import net.mobtalker.mobtalkerscript.v3.instruction.MtsInstruction;
import net.mobtalker.mobtalkerscript.v3.value.*;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public final class MtsFrame
{
    private static boolean DEBUG = true;
    
    public static void enableDebug( boolean flag )
    {
        DEBUG = flag;
    }
    
    // ========================================
    
    private final MtsClosure _closure;
    private int _ip;
    
    private final MtsValue[] _stack;
    private int _top;
    
    private final List<FrameValue> _locals;
    private final List<FrameValue> _externals;
    private final MtsVarArgs _varargs;
    
    private VariableDescription _lastVar;
    
    // ========================================
    
    public MtsFrame( MtsClosure closure, MtsVarArgs arguments, List<FrameValue> externals )
    {
        _closure = closure;
        _ip = 0;
        
        MtsFunctionPrototype prototype = closure.getPrototype();
        int nStack = prototype.getMaxStackSize();
        int nLocals = prototype.getLocalCount();
        int nArgs = prototype.getParameterCount();
        
        _stack = new MtsValue[nStack];
        _top = 0;
        
        ArrayList<FrameValue> locals = Lists.newArrayListWithCapacity( nLocals );
        int i = 0;
        for ( ; i < nArgs; i++ )
        {
            locals.add( new FrameValue( arguments.get( i ) ) );
        }
        for ( ; i < nLocals; i++ )
        {
            locals.add( new FrameValue() );
        }
        
        _locals = locals;
        _externals = externals;
        _varargs = prototype.hasVarargs() ? arguments.subArgs( i ) : EMPTY_VARARGS;
    }
    
    // ========================================
    
    public MtsClosure getClosure()
    {
        return _closure;
    }
    
    public int getInstructionPointer()
    {
        return _ip;
    }
    
    public void setInstructionPointer( int target )
    {
        _ip = target;
    }
    
    public VariableDescription getLastVariable()
    {
        return _lastVar;
    }
    
    // ========================================
    
    /**
     * Executes the instructions given until an instruction signals a frame exit and returns the top of the stack.
     */
    public MtsValue run()
    {
        List<MtsInstruction> instructions = _closure.getPrototype().getInstructions();
        
        if ( EngineLog.isFinestEnabled() )
        {
            EngineLog.finest( "Stack: " + formatStack() );
        }
        
        for ( ;; _ip++ )
        {
            MtsInstruction instr = instructions.get( _ip );
            
            if ( EngineLog.isInfoEnabled() )
            {
                EngineLog.info( "Executing [%s] %s",
                                formatInstructionPointer( instructions.size() ),
                                instr.toString( getClosure().getPrototype() ) );
            }
            
            instr.execute( this );
            
            if ( EngineLog.isFinestEnabled() )
            {
                EngineLog.finest( "Stack: " + formatStack() );
            }
            
            if ( instr.exits() )
                break;
        }
        
        MtsValue result = pop();
        
        assert stackIsEmpty() : "Stack was not emptied! " + formatStack();
        return result;
    }
    
    private String formatStack()
    {
        if ( _top == 0 )
            return "[]";
        
        StringBuilder s = new StringBuilder( "[" );
        int i = 0;
        for ( ; i < ( _top - 1 ); i++ )
        {
            s.append( _stack[i] ).append( ", " );
        }
        s.append( _stack[i] ).append( ']' );
        
        return s.toString();
    }
    
    private String formatInstructionPointer( int count )
    {
        int l = Integer.toString( count ).length();
        return Strings.padStart( Integer.toString( _ip ), l, '0' );
    }
    
    // ========================================
    
    public MtsValue getConstant( int i )
    {
        return _closure.getPrototype().getConstant( i );
    }
    
    public FrameValue getLocal( int i )
    {
        if ( DEBUG )
        {
            _lastVar = _closure.getPrototype().getLocalDescription( i );
        }
        
        return _locals.get( i );
    }
    
    public FrameValue getExternal( int i )
    {
        if ( DEBUG )
        {
            _lastVar = _closure.getPrototype().getExternalDescription( i );
        }
        
        return _externals.get( i );
    }
    
    // ========================================
    
    public void push( MtsValue o )
    {
        if ( _top == _stack.length )
            throw new ScriptEngineException( "stack overflow" );
        
        _stack[_top++] = o;
    }
    
    public void push( MtsVarArgs args, int count )
    {
        for ( int i = 0; i < count; i++ )
        {
            push( args.get( i ) );
        }
    }
    
    public MtsValue pop()
    {
        if ( stackIsEmpty() )
            throw new ScriptEngineException( "stack underflow" );
        
        return _stack[--_top];
    }
    
    public List<MtsValue> pop( int count )
    {
        if ( count == 0 )
            return Collections.emptyList();
        
        if ( count > stackCount() )
            throw new ScriptEngineException( "stack underflow" );
        
        MtsValue[] values = new MtsValue[count];
        System.arraycopy( _stack, ( _top - count ), values, 0, count );
        
        _top -= count;
        
        return Arrays.asList( values );
    }
    
    public MtsValue peek()
    {
        if ( stackIsEmpty() )
            throw new ScriptEngineException( "stack is empty" );
        
        return _stack[_top - 1];
    }
    
    public void duplicateTop()
    {
        if ( _top == _stack.length )
            throw new ScriptEngineException( "stack overflow" );
        
        _stack[_top] = _stack[_top - 1];
        _top++;
    }
    
    // ========================================
    
    public void pushNil()
    {
        push( NIL );
    }
    
    public void pushNil( int count )
    {
        for ( int i = 0; i < count; i++ )
        {
            push( NIL );
        }
    }
    
    public void pushConstant( int i )
    {
        push( getConstant( i ) );
    }
    
    public void pushLocal( int i )
    {
        push( getLocal( i ).get() );
    }
    
    public void pushExternal( int i )
    {
        push( getExternal( i ).get() );
    }
    
    public void pushVarargs()
    {
        push( _varargs );
    }
    
    public void pushVarargs( int count )
    {
        for ( int i = 0; i < count; i++ )
        {
            push( _varargs.get( i ) );
        }
    }
    
    public void storeLocal( int i )
    {
        getLocal( i ).set( pop() );
    }
    
    public void storeExternal( int i )
    {
        getExternal( i ).set( pop() );
    }
    
    // ========================================
    
    public int stackCount()
    {
        return _top;
    }
    
    public boolean stackIsEmpty()
    {
        return _top <= 0;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder( "Frame" );
        
        s.append( " [IP: " ).append( _ip );
        s.append( ", Last used Variable or Constant: " ).append( _lastVar );
        s.append( "]\n" );
        
        s.append( " Locals    " ).append( _locals.toString() ).append( "\n" );
        s.append( " Externals " ).append( _externals.toString() ).append( "\n" );
        s.append( " Stack     " ).append( formatStack() );
        
        return s.toString();
    }
    
}
