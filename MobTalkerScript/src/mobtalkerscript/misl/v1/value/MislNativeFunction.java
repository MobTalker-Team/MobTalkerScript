package mobtalkerscript.misl.v1.value;

import mobtalkerscript.misl.v1.*;

/**
 * A function that can be called in scripts and is implemented in Java.
 * <p>
 * The engine <b>must</b> ensure that any variables are resolved when passed to any <code>call</code> method.
 * 
 * @author Chimaine
 */
public abstract class MislNativeFunction extends MislValue
{
    
    static final MislValue[] EMPTY_ARGS = {};
    
    // ========================================
    
    /**
     * Calls this function. If the return value is not <code>null</code> its contents will be pushed onto the stack.
     */
    public abstract MislValue call( IBindings env );
    
    /**
     * Calls this function. If the return value is not <code>null</code> its contents will be pushed onto the stack.
     */
    public abstract MislValue call( IBindings env, MislValue... args );
    
    /**
     * Indicates if this function pauses the engine.
     * <p>
     * If this function also has a return value, the engine will pause before the return value is assigned to its target.
     * 
     * @return
     */
    public boolean isAsync()
    {
        return false;
    }
    
    // ========================================
    
    @Override
    public MislNativeFunction asNativeFunction()
    {
        return this;
    }
    
    // ========================================
    
    @Override
    public MislString toMtsString()
    {
        throw new ScriptRuntimeException( "Cannot convert a native function to a string!" );
    }
    
    // ========================================
    
    @Override
    public boolean isNativeFunction()
    {
        return true;
    }
    
    @Override
    public String getTypeName()
    {
        return "nativefunction";
    }
    
    @Override
    public MislBoolean equal( MislValue x )
    {
        return valueOf( equals( x ) );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "func#" + Integer.toHexString( hashCode() );
    }
    
    @Override
    public int compareTo( MislValue o )
    {
        return hashCode() - o.hashCode();
    }
    
}
