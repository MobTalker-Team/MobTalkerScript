package mobtalkerscript.misl.v1;

import static mobtalkerscript.misl.v1.ScriptEngineState.*;
import mobtalkerscript.*;
import mobtalkerscript.misl.v1.instruction.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;
import mobtalkerscript.util.logging.*;

/**
 * Executes {@link MislInstruction MtsInstructions}.
 * 
 * @author Chimaine
 */
public class MislEngine
{
    
    private final Stack<MislFrame> _frameStack;
    private final Queue<MislInstruction> _instructionQueue;
    
    private ScriptContext _context;
    private ScriptEngineState _state;
    
    // ========================================
    
    public MislEngine(ScriptContext context)
    {
        _frameStack = Stack.newStack();
        _frameStack.push(new MislFrame(null, 0, -1));
        
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
            while ((_state != STOPPED) && !_instructionQueue.isEmpty())
            {
                MislInstruction instr = _instructionQueue.poll();
                
                if (MTSLog.isFineEnabled())
                {
                    MTSLog.fine("[Engine] Executing %s", instr.toString());
                }
                
                instr.execute(_frameStack, _context);
                
                if (MTSLog.isFinestEnabled())
                {
                    MTSLog.finest("[Engine] Stack: %s", _frameStack.peek().getStack().toString());
                }
                
                MislInstruction next = instr.getNext();
                
                if (next != null)
                {
                    _instructionQueue.offer(next);
                }
                
                if (instr.pausesExecution())
                {
                    _state = PAUSED;
                    return;
                }
            }
            
            if (_frameStack.count() > 1)
            {
                throw new ScriptEngineException("Unclean exit: Frame stack still contains %s frames!",
                                                _frameStack.count() - 1);
            }
            
            if (_context.getCurrentScope() != _context.getScriptEnvironment())
            {
                throw new ScriptEngineException("Unclean exit: Current scope is not the script scope!");
            }
        }
        catch (ScriptRuntimeException ex)
        {
            _state = ERROR;
            throw ex;
        }
        catch (ScriptEngineException ex)
        {
            _state = ERROR;
            throw ex;
        }
        catch (Exception ex)
        {
            _state = ERROR;
            throw new ScriptEngineException("Something happend while running the engine", ex);
        }
        
        _state = STOPPED;
        
//        stop();
    }
    
    protected void pushValues(MislValue... args)
    {
        if ((args != null) && (args.length > 0))
        {
            MislFrame frame = _frameStack.peek();
            Stack<MislValue> stack = frame.getStack();
            
            for (int i = 0; i < args.length; i++)
            {
                stack.push(args[i]);
            }
        }
    }
    
    /**
     * Resumes a paused engine. Also pushes the given value onto the stack.
     * 
     * @param args The value to push onto the stack.
     */
    public void resume(MislValue... args)
    {
        if (!isPaused())
        {
            throw new ScriptEngineException("Cannot resume an engine that is not paused! State was: %s",
                                            _state.toString());
        }
        
        pushValues(args);
        
        run();
    }
    
    public MislValue[] call(String function, int returnCount, MislValue... args)
    {
        if (_state != STOPPED)
        {
            throw new ScriptEngineException("Engine must terminate before calling a function! State was: %s).",
                                            _state.toString());
        }
        
        pushValues(args);
        
        _instructionQueue.offer(new InstrLoad(function));
        _instructionQueue.offer(new InstrCall(args.length, returnCount));
        
        run();
        
        if (returnCount > 0)
        {
            MislValue[] returnVals = new MislValue[returnCount];
            
            Stack<MislValue> stack = _frameStack.peek().getStack();
            
            for (int i = returnVals.length - 1; i >= 0; i--)
            {
                returnVals[i] = stack.pop();
            }
            
            return returnVals;
        }
        else
        {
            return new MislValue[0];
        }
    }
    
    public void stop()
    {
        if (_state == STOPPED)
        {
            return;
        }
        
        _instructionQueue.clear();
        _frameStack.clear();
        _context.leaveAllScopes();
        
        _frameStack.push(new MislFrame(null, 0, -1));
        
        _state = STOPPED;
    }
    
}
