package mobtalkerscript.misl.v1.value;


/**
 * @author Chimaine
 */
public abstract class MislFunction extends MislValue
{
    @Override
    public boolean isFunction()
    {
        return true;
    }
    
    @Override
    public MislFunction asFunction()
    {
        return this;
    }
    
    @Override
    public String getTypeName()
    {
        return TYPENAME_FUNCTION;
    }
}
