package mobtalkerscript.mts.v2.value;

public abstract class MtsZeroArgFunction extends MtsJavaFunction
{
    @Override
    protected MtsValue invoke( MtsValue... args )
    {
        return invoke();
    }
    
    protected abstract MtsValue invoke();
}