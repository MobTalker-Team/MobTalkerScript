package mobtalkerscript.mts.v2.compiler;

@SuppressWarnings( "serial" )
public class MtsSyntaxError extends RuntimeException
{
    private final String _sourceName;
    private final SourcePosition _sourcePosition;
    private final String _msg;
    
    // ========================================
    
    public MtsSyntaxError( String sourceName, SourcePosition sourcePosition, String msg )
    {
        _sourceName = sourceName;
        _sourcePosition = sourcePosition;
        _msg = msg;
    }
    
    public MtsSyntaxError( String sourceName, int line, int coloum, String msg )
    {
        _sourceName = sourceName;
        _sourcePosition = new SourcePosition( line, coloum );
        _msg = msg;
    }
    
    // ========================================
    
    public String getSourceName()
    {
        return _sourceName;
    }
    
    public SourcePosition getSourcePosition()
    {
        return _sourcePosition;
    }
    
    public String getOriginalMessage()
    {
        return _msg;
    }
    
    // ========================================
    
    @Override
    public String getMessage()
    {
        return new StringBuilder().append( _sourceName )
                                  .append( ":" )
                                  .append( _sourcePosition.Line )
                                  .append( ": " )
                                  .append( _msg ).toString();
    }
}
