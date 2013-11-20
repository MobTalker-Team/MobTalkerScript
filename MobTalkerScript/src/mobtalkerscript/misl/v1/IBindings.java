package mobtalkerscript.misl.v1;

import mobtalkerscript.misl.v1.value.*;

/**
 * Represents a mapping of key-value pairs.
 * 
 * @author Tobsen
 */
public interface IBindings
{
    
    IBindings getParent();
    
    /**
     * Returns the default value that is returned if a key does not exist.
     * <p>
     * Can be <code>null</code>, but this is discouraged.
     * 
     * @return The default value of an unassigned binding.
     */
    MislValue getDefault();
    
    /**
     * Checks if an binding for the given key exists.
     * 
     * @param key The key of the binding to check.
     * @param recursive If the parent bindings should be included in the search.
     * @return <code>true</code> if a binding for the given key exists, <code>false</code> otherwise.
     */
    boolean contains( String key, boolean recursive );
    
    /**
     * Returns the value of an binding.
     * 
     * @param key The key of the binding.
     * @return The value of the binding, or {@link #getDefault()} if the binding does not exist.
     */
    MislValue get( String key );
    
    /**
     * Sets the value of an binding.
     * 
     * @param key The key of the binding.
     * @param value The new value for the binding.
     * @return The previous value of the binding.
     */
    MislValue set( String key, MislValue value );
    
    /**
     * Copies this bindings into to specified bindings.
     */
    void copyTo( IBindings target );
    
}
