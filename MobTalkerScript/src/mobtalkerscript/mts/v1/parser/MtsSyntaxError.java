package mobtalkerscript.mts.v1.parser;

@SuppressWarnings( "serial" )
public class MtsSyntaxError extends RuntimeException
{
    private final String _sourceName;
    private final int _line;
    private final int _col;
    private final String _msg;
    
    // ========================================
    
    public MtsSyntaxError( String sourceName, int line, int col, String msg )
    {
        _sourceName = sourceName;
        _line = line;
        _col = col;
        _msg = msg;
    }
    
    // ========================================
    
    public int getLine()
    {
        return _line;
    }
    
    public int getCharPos()
    {
        return _col;
    }
    
    public String getMsg()
    {
        return _msg;
    }
    
    // ========================================
    
    @Override
    public String getMessage()
    {
        return toString();
    }
    
    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        
        s.append( _sourceName ) //
         .append( ":" )
         .append( _line )
         .append( "," )
         .append( _col )
         .append( " : " )
         .append( _msg );
        
        return s.toString();
    }
    
}
