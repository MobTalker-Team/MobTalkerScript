package mobtalkerscript.mts.v2;

import mobtalkerscript.mts.v2.value.*;
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
    
    public ScriptContext( IBindings globals )
    {
        _globalEnv = globals;
        _scriptEnv = new Bindings( _globalEnv );
    }
    
    public ScriptContext( IBindings globals, IBindings locals )
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
        if ( _scopes.stackIsEmpty() )
        {
            return _scriptEnv;
        }
        else
        {
            return _scopes.peek();
        }
    }
    
    private void pushScope( IBindings parent )
    {
        _scopes.push( new Bindings( parent ) );
    }
    
    public void enterFunctionScope()
    {
        if ( MTSLog.isFinestEnabled() )
        {
            MTSLog.finest( "[Engine] Entering new function scope" );
        }
        
        pushScope( _scriptEnv );
    }
    
    public void leaveFunctionScope()
    {
        if ( _scopes.stackIsEmpty() )
        {
            throw new IllegalStateException( "No Scopes to leave" );
        }
        
        if ( MTSLog.isFinestEnabled() )
        {
            MTSLog.finest( "[Engine] Leaving function scope" );
        }
        
        while ( getCurrentScope().getParent() != _scriptEnv )
        {
            leaveBlockScope();
        }
        
        leaveBlockScope();
    }
    
    public void enterBlockScope()
    {
        if ( MTSLog.isFinestEnabled() )
        {
            MTSLog.finest( "[Engine] Entering new block scope" );
        }
        pushScope( getCurrentScope() );
    }
    
    public void leaveBlockScope()
    {
        if ( MTSLog.isFinestEnabled() )
        {
            MTSLog.finest( "[Engine] Leaving block scope" );
        }
        _scopes.pop();
    }
    
    public void leaveAllScopes()
    {
        while ( !_scopes.stackIsEmpty() )
        {
            leaveFunctionScope();
        }
    }
    
    // ========================================
    
    /**
     * Gets the value of a binding. Searches all available scopes in descending order.
     * 
     * @param key The key of the binding.
     * @return The value of the binding, or {@link MtsNil} if no such bindings exits in any scope.
     */
    public MtsValue getValue( String key )
    {
        return getCurrentScope().get( key );
    }
    
    /**
     * Sets a value within the current scope.
     * 
     * @param key
     * @param value
     * @return
     */
    public MtsValue setValue( String key, MtsValue value )
    {
        return getCurrentScope().set( key, value );
    }
    
    /**
     * Searches all scopes for an existing key to store in, defaulting to the script scope if the key is not found.
     */
    public MtsValue setExistingValue( String key, MtsValue value )
    {
        IBindings env = getCurrentScope();
        while ( !env.contains( key ) && ( env != _scriptEnv ) )
        {
            env = env.getParent();
            
            if ( env == null )
            {
                throw new AssertionError();
            }
        }
        
        return env.set( key, value );
    }
    
}
