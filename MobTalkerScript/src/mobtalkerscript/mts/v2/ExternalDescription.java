package mobtalkerscript.mts.v2;

public class ExternalDescription
{
    private final int _index;
    private final boolean _isLocal;
    
    // Debug information
    private final String _name;
    
    // ========================================
    
    public ExternalDescription( int index, boolean isLocal, String name )
    {
        _index = index;
        _isLocal = isLocal;
        
        _name = name;
    }
    
    // ========================================
    
    public int getIndex()
    {
        return _index;
    }
    
    /**
     * Indicates if this external is on the stack of the enclosing function or belongs to another function (usually an enclosing
     * function of the enclosing function).
     */
    public boolean isLocal()
    {
        return _isLocal;
    }
    
    public String getName()
    {
        return _name;
    }
    
}
