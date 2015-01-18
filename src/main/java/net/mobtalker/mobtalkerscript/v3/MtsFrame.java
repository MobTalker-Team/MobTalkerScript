/*
 * Copyright (C) 2013-2015 Chimaine
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
import net.mobtalker.mobtalkerscript.v3.instruction.MtsInstruction;
import net.mobtalker.mobtalkerscript.v3.value.*;

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
    
    private final FrameValue[] _locals;
    private final FrameValue[] _externals;
    private final MtsVarargs _varargs;
    
    private VariableDescription _lastVar;
    
    // ========================================
    
    public MtsFrame( MtsClosure closure, MtsVarargs arguments, FrameValue[] externals )
    {
        _closure = closure;
        _ip = 0;
        
        MtsFunctionPrototype prototype = closure.getPrototype();
        int nLocals = prototype.getLocalCount();
        int nArgs = prototype.getParameterCount();
        
        _stack = new MtsValue[prototype.getMaxStackSize()];
        _top = 0;
        
        FrameValue[] locals = new FrameValue[nLocals];
        int i = 0;
        for ( ; i < nArgs; i++ )
        {
            locals[i] = new FrameValue( arguments.get( i ) );
        }
        _varargs = prototype.hasVarargs() ? arguments.subArgs( i ) : MtsVarargs.Empty;
        
        for ( ; i < nLocals; i++ )
        {
            locals[i] = new FrameValue();
        }
        
        _locals = locals;
        _externals = externals;
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
    public MtsVarargs run()
    {
        MtsInstruction[] instructions = _closure.getPrototype().getInstructions();
        
        if ( EngineLog.isFinestEnabled() )
        {
            EngineLog.finest( "Enter Frame" );
            EngineLog.finest( formatStack() );
        }
        
        for ( ;; _ip++ )
        {
            MtsInstruction instr = instructions[_ip];
            
            if ( EngineLog.isInfoEnabled() )
            {
                EngineLog.info( formatExecutedInstruction( instr ) );
            }
            
            instr.execute( this );
            
            if ( EngineLog.isFinestEnabled() )
            {
                EngineLog.finest( formatStack() );
            }
            
            if ( instr.exits() )
                break;
        }
        
        MtsValue result = pop();
        
        assert isEmpty() : formatStack();
        
        EngineLog.finest( "Exit Frame" );
        
        return result instanceof MtsVarargs ? (MtsVarargs) result : MtsVarargs.of( result );
    }
    
    private String formatExecutedInstruction( MtsInstruction instr )
    {
        MtsFunctionPrototype prototype = _closure.getPrototype();
        return new StringBuilder( 50 ).append( "Executing " )
                                      .append( '[' )
                                      .append( prototype.getName() )
                                      .append( ':' )
                                      .append( prototype.getSourcePosition( _ip ).Line )
                                      .append( "][" )
                                      .append( Integer.toString( _ip ) )
                                      .append( "] " )
                                      .append( instr.toString( prototype ) )
                                      .toString();
    }
    
    private String formatStack()
    {
        if ( _top == 0 )
            return "[]";
        
        StringBuilder s = new StringBuilder( 50 );
        s.append( "Stack: [" );
        
        int i = 0;
        int limit = _top - 1;
        for ( ; i < limit; ++i )
        {
            s.append( _stack[i].toString( DEBUG ) ).append( ", " );
        }
        s.append( _stack[i].toString( DEBUG ) ).append( ']' );
        
        return s.toString();
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
        
        return _locals[i];
    }
    
    public FrameValue getExternal( int i )
    {
        if ( DEBUG )
        {
            _lastVar = _closure.getPrototype().getExternalDescription( i );
        }
        
        return _externals[i];
    }
    
    // ========================================
    
    public void push( MtsValue o )
    {
        if ( _top == _stack.length )
            throw new MtsEngineException( "stack overflow" );
        
        _stack[_top++] = o;
    }
    
    public void push( MtsVarargs args, int count )
    {
        for ( int i = 0; i < count; i++ )
        {
            push( args.get( i ) );
        }
    }
    
    public MtsValue pop()
    {
        if ( _top == 0 )
            throw new MtsEngineException( "stack underflow" );
        
        return _stack[--_top];
    }
    
    /**
     * Packs the contents of the stack into a single {@link MtsVarargs} and returns them.
     * The top of the stack is the last element of the resulting varargs.
     */
    public MtsVarargs pop( int count )
    {
        if ( count > _top )
            throw new MtsEngineException( "stack underflow" );
        
        if ( count == 0 )
        {
            return MtsVarargs.Empty;
        }
        else if ( peek().isVarArgs() )
        {
            MtsVarargs tail = pop().asVarArgs();
            MtsValue[] values = new MtsValue[--count];
            System.arraycopy( _stack, ( _top - count ), values, 0, count );
            _top -= count;
            
            return MtsVarargs.of( values, tail );
        }
        else
        {
            MtsValue[] values = new MtsValue[count];
            System.arraycopy( _stack, ( _top - count ), values, 0, count );
            _top -= count;
            
            return MtsVarargs.of( values );
        }
    }
    
    public MtsValue peek()
    {
        if ( _top == 0 )
            throw new MtsEngineException( "stack is empty" );
        
        return _stack[_top - 1];
    }
    
    public void duplicateTop()
    {
        if ( _top == _stack.length )
            throw new MtsEngineException( "stack overflow" );
        
        _stack[_top] = _stack[_top - 1];
        _top++;
    }
    
    /**
     * Packs the contents of the stack into a single {@link MtsVarargs} and pushes them onto the stack.
     * The top of the stack is the last element of the resulting varargs.
     */
    public void pack()
    {
        pack( _top );
    }
    
    /**
     * Packs the top <code>count</code> values of the stack into a single {@link MtsVarargs} and pushes them onto the stack.
     * The top of the stack is the last element of the resulting varargs.
     */
    public void pack( int count )
    {
        push( pop( count ) );
    }
    
    // ========================================
    
    public void pushNil()
    {
        push( Nil );
    }
    
    public void pushNil( int count )
    {
        for ( int i = 0; i < count; i++ )
        {
            push( Nil );
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
    
    public int count()
    {
        return _top;
    }
    
    public boolean isEmpty()
    {
        return _top == 0;
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
