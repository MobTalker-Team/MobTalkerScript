package mobtalkerscript.misl.v1;

import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;
import mobtalkerscript.util.logging.*;

public class ScriptContext
{
    
    private final IBindings _globalEnv;
    private final IBindings _scriptEnv;
    
    private final Stack<IBindings> _scopes;
    
    // ========================================
    
    {
        _scopes = Stack.newStack();
    }
    
    public ScriptContext(IBindings globals)
    {
        _globalEnv = globals;
        _scriptEnv = new SimpleBindings(_globalEnv);
    }
    
    public ScriptContext(IBindings globals, IBindings locals)
    {
        _globalEnv = globals;
        _scriptEnv = locals;
    }
    
    // ========================================
    
    public IBindings getGlobalEnvironment()
    {
        return _globalEnv;
    }
    
    public IBindings getScriptEnvironment()
    {
        return _scriptEnv;
    }
    
    // ========================================
    
    public IBindings getCurrentScope()
    {
        if (_scopes.isEmpty())
        {
            return _scriptEnv;
        }
        else
        {
            return _scopes.peek();
        }
    }
    
    private void pushScope(IBindings parent)
    {
        _scopes.push(new SimpleBindings(parent));
    }
    
    public void enterFunctionScope()
    {
        if (MTSLog.isFinestEnabled())
        {
            MTSLog.finest("[Engine] Entering new function scope");
        }
        
        pushScope(_scriptEnv);
    }
    
    public void leaveFunctionScope()
    {
        if (_scopes.isEmpty())
        {
            throw new IllegalStateException("No Scopes to leave");
        }
        
        if (MTSLog.isFinestEnabled())
        {
            MTSLog.finest("[Engine] Leaving function scope");
        }
        
        while (getCurrentScope().getParent() != _scriptEnv)
        {
            leaveBlockScope();
        }
        
        leaveBlockScope();
    }
    
    public void enterBlockScope()
    {
        if (MTSLog.isFinestEnabled())
        {
            MTSLog.finest("[Engine] Entering new block scope");
        }
        pushScope(getCurrentScope());
    }
    
    public void leaveBlockScope()
    {
        if (MTSLog.isFinestEnabled())
        {
            MTSLog.finest("[Engine] Leaving block scope");
        }
        _scopes.pop();
    }
    
    public void leaveAllScopes()
    {
        while (!_scopes.isEmpty())
        {
            leaveFunctionScope();
        }
    }
    
    // ========================================
    
    /**
     * Gets the value of a binding. Searches all available scopes in descending order.
     * 
     * @param key The key of the binding.
     * @return The value of the binding, or {@link MislNil} if no such bindings exits in any scope.
     */
    public MislValue getValue(String key)
    {
        IBindings scope = getCurrentScope();
        
        if (scope.contains(key))
        {
            return scope.get(key);
        }
        else
        {
            return MislValue.NIL;
        }
    }
    
    /**
     * Sets a value within the current scope.
     * 
     * @param key
     * @param value
     * @return
     */
    public MislValue setValue(String key, MislValue value)
    {
        return getCurrentScope().set(key, value);
    }
    
}
