package mobtalkerscript.v2;

public abstract class VariableDescription
{
    protected final String _name;
    protected final int _index;
    
    public VariableDescription( String name, int index )
    {
        _name = name;
        _index = index;
    }
    
    public String getName()
    {
        return _name;
    }
    
    public int getIndex()
    {
        return _index;
    }
    
    @Override
    public String toString()
    {
        return _name;
    }
}
