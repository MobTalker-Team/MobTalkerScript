package mobtalkerscript.mts.v2;

import mobtalkerscript.mts.v2.compiler.*;

public class MtsStackTraceElement
{
    private final String _fileName;
    private final SourcePosition _position;
    private final String _functionName;
    
    /**
     * For Java functions
     */
    public MtsStackTraceElement( String functionName )
    {
        _fileName = null;
        _position = SourcePosition.ZERO;
        _functionName = functionName;
    }
    
    /**
     * For compiled function
     */
    public MtsStackTraceElement( String fileName, SourcePosition position, String functionName )
    {
        _fileName = fileName;
        _position = position;
        _functionName = functionName;
    }
    
    public String getLocation()
    {
        return _fileName == null ? //
                "[Java]"
                : ( _fileName + ":" + _position.Line /* + ":" + _position.Coloum */);
    }
    
    public String getFunctionName()
    {
        return _functionName;
    }
    
    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append( getLocation() ).append( ": in " );
        
        if ( _functionName == null )
        {
            s.append( "main chunk" );
        }
        else
        {
            s.append( "function '" ).append( getFunctionName() ).append( "'" );
        }
        
        return s.toString();
    }
}
