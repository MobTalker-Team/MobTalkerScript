package mobtalkerscript.mts.v1.parser;

@SuppressWarnings("serial")
public class MtsSyntaxError extends RuntimeException
{
    
    private final int _line;
    private final int _col;
    private final String _msg;
    
    // ========================================
    
    public MtsSyntaxError(int line, int col, String msg)
    {
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
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        
        s.append("line ") //
         .append(_line)
         .append(",")
         .append(_col)
         .append(" : ")
         .append(_msg);
        
        return s.toString();
    }
    
}
