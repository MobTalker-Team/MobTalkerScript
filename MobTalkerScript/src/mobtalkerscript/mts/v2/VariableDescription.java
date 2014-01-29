package mobtalkerscript.mts.v2;

public class VariableDescription
{
    protected String _name;
    protected int _pcStart;
    protected int _pcEnd;
    
    public VariableDescription( String name, int pcStart, int pcEnd )
    {
        _name = name;
        _pcStart = pcStart;
        _pcEnd = pcEnd;
    }
    
    public String getName()
    {
        return _name;
    }
    
    public int getPcStart()
    {
        return _pcStart;
    }
    
    public int getPcEnd()
    {
        return _pcEnd;
    }
    
}
