package mobtalkerscript.v2;

public class ExternalDescription extends VariableDescription
{
    private final int _parentIndex;
    private final boolean _parentLocal;
    
    // ========================================
    
    public ExternalDescription( String name, int index, int parentIndex, boolean parentLocal )
    {
        super( name, index );
        _parentIndex = parentIndex;
        _parentLocal = parentLocal;
    }
    
    // ========================================
    
    public int getParentIndex()
    {
        return _parentIndex;
    }
    
    public boolean isParentLocal()
    {
        return _parentLocal;
    }
    
}
