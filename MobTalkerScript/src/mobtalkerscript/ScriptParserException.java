package mobtalkerscript;

/**
 * Thrown when a parser encounters an error
 */
@SuppressWarnings("serial")
public class ScriptParserException extends RuntimeException
{
    
    public ScriptParserException(String msg)
    {
        super(msg);
    }
    
    public ScriptParserException(Exception parent)
    {
        super(parent);
    }
    
    public ScriptParserException(String msg, Object... args)
    {
        super(String.format(msg, args));
    }
    
    public ScriptParserException(String msg, Exception parent, Object... args)
    {
        super(String.format(msg, args), parent);
    }
    
}
