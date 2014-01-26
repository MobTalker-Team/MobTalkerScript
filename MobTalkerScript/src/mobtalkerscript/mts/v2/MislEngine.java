package mobtalkerscript.mts.v2;

import static mobtalkerscript.mts.v2.ScriptEngineState.*;
import mobtalkerscript.misl.v1.instruction.*;
import mobtalkerscript.mts.v2.instruction.*;
import mobtalkerscript.mts.v2.value.*;
import mobtalkerscript.util.*;
import mobtalkerscript.util.logging.*;

/**
 * Executes {@link MtsInstruction MtsInstructions}.
 * 
 * @author Chimaine
 */
public class MislEngine
{
    
    private final Stack<MtsFrame> _frameStack;
    private final Queue<MtsInstruction> _instructionQueue;
    
    private ScriptContext _context;
    private ScriptEngineState _state;
    
    // ========================================
    
    public MislEngine( ScriptContext context )
    {
        _frameStack = Stack.newStack();
        _frameStack.push( new MtsFrame( null, 0 ) );
        
        _instructionQueue = Queue.newQueue();
        
        _context = context;
        _state = STOPPED;
    }
    
    // ========================================
    
    public ScriptContext getContext()
    {
        return _context;
    }
    
    public ScriptEngineState getState()
    {
        return _state;
    }
    
    public boolean isPaused()
    {
        return _state == PAUSED;
    }
    
    // ========================================
    
    private void run()
    {
        _state = RUNNING;
        
        try
        {
            while ( ( _state != STOPPED ) && !_instructionQueue.isEmpty() )
            {
                MtsInstruction instr = _instructionQueue.poll();
                
                MTSLog.fine( "[Engine] Executing %s", instr.toString() );
                
                instr.execute( _frameStack, _context );
                
                MtsInstruction next = instr.getNext();
                
                if ( next != null )
                {
                    _instructionQueue.offer( next );
                }
                
                if ( instr.pausesExecution() )
                {
                    _state = PAUSED;
                    return;
                }
            }
            
            if ( _frameStack.count() > 1 )
            {
                throw new ScriptEngineException( "Unclean exit: Frame stack still contains %s frames!",
                                                 _frameStack.count() - 1 );
            }
            
            if ( _context.getCurrentScope() != _context.getScriptEnvironment() )
            {
                throw new ScriptEngineException( "Unclean exit: Current scope is not the script scope!" );
            }
        }
        catch ( ScriptRuntimeException ex )
        {
            _state = ERROR;
            throw ex;
        }
        catch ( ScriptEngineException ex )
        {
            _state = ERROR;
            throw ex;
        }
        catch ( Exception ex )
        {
            _state = ERROR;
            throw new ScriptEngineException( "Something happend while running the engine", ex );
        }
        
        _state = STOPPED;
        
//        stop();
    }
    
    protected void pushValues( MtsValue... args )
    {
        if ( ( args != null ) && ( args.length > 0 ) )
        {
            MtsFrame frame = _frameStack.peek();
            
            for ( int i = 0; i < args.length; i++ )
            {
                frame.push( args[i] );
            }
        }
    }
    
    /**
     * Resumes a paused engine. Also pushes the given value onto the stack.
     * 
     * @param args The value to push onto the stack.
     */
    public void resume( MtsValue... args )
    {
        if ( !isPaused() )
        {
            throw new ScriptEngineException( "Cannot resume an engine that is not paused! State was: %s",
                                             _state.toString() );
        }
        
        pushValues( args );
        
        run();
    }
    
    public MtsValue[] call( String function, int returnCount, MtsValue... args )
    {
        if ( _state != STOPPED )
        {
            throw new ScriptEngineException( "Engine must terminate before calling a function! State was: %s).",
                                             _state.toString() );
        }
        
        pushValues( args );
        
        _instructionQueue.offer( new InstrLoad( function ) );
        _instructionQueue.offer( new InstrCallFunc( args.length, returnCount ) );
        
        run();
        
        if ( returnCount > 0 )
        {
            MtsValue[] returnVals = new MtsValue[returnCount];
            
            MtsFrame frame = _frameStack.peek();
            
            for ( int i = returnVals.length - 1; i >= 0; i-- )
            {
                returnVals[i] = frame.pop();
            }
            
            return returnVals;
        }
        else
        {
            return new MtsValue[0];
        }
    }
    
    public void stop()
    {
        if ( _state == STOPPED )
        {
            return;
        }
        
        _instructionQueue.clear();
        _frameStack.clear();
        _context.leaveAllScopes();
        
        _frameStack.push( new MtsFrame( null, 0 ) );
        
        _state = STOPPED;
    }
    
}
