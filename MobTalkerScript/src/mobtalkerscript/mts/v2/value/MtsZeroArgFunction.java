package mobtalkerscript.mts.v2.value;

public abstract class MtsZeroArgFunction extends MtsFunction
{
    @Override
    public MtsValue call( MtsValue... args )
    {
        return call();
    }
    
    public abstract MtsValue call();
}