package mobtalkerscript;

/**
 * Thrown when the execution of script code causes an exception.
 */
@SuppressWarnings("serial")
public class ScriptRuntimeException extends RuntimeException
{
    
    public ScriptRuntimeException(String msg)
    {
        super(msg);
    }
    
    public ScriptRuntimeException(Exception parent)
    {
        super(parent);
    }
    
    public ScriptRuntimeException(String msg, Object... args)
    {
        super(String.format(msg, args));
    }
    
    public ScriptRuntimeException(String msg, Exception parent, Object... args)
    {
        super(String.format(msg, args), parent);
    }
    
}
