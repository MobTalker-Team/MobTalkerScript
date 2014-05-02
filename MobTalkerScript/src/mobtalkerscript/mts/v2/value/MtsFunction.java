package mobtalkerscript.mts.v2.value;

/**
 * @author Chimaine
 */
public abstract class MtsFunction extends MtsValue
{
    @Override
    public boolean isFunction()
    {
        return true;
    }
    
    @Override
    public MtsFunction asFunction()
    {
        return this;
    }
    
    @Override
    public MtsType getType()
    {
        return MtsType.FUNCTION;
    }
}
