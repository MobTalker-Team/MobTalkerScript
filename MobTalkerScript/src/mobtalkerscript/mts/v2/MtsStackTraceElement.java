package mobtalkerscript.mts.v2;

public class MtsStackTraceElement
{
    private final String _fileName;
    private final int _lineNumber;
    private final String _functionName;
    
    /**
     * For Java functions
     */
    public MtsStackTraceElement( String functionName )
    {
        _fileName = null;
        _lineNumber = 0;
        _functionName = functionName;
    }
    
    /**
     * For compiled function
     */
    public MtsStackTraceElement( String fileName, int lineNumber, String functionName )
    {
        _fileName = fileName;
        _lineNumber = lineNumber;
        _functionName = functionName;
    }
    
    public String getLocation()
    {
        return _fileName == null ? "[Java]" : _fileName + ":" + _lineNumber;
    }
    
    public String getFunctionName()
    {
        return _functionName;
    }
    
    @Override
    public String toString()
    {
        return getLocation() + ": in " + getFunctionName();
    }
}
