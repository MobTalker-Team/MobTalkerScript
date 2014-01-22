package mobtalkerscript.misl.v1.value;

public abstract class MislZeroArgFunction extends MislFunction
{
    @Override
    public MislValue call( MislValue... args )
    {
        return call();
    }
    
    public abstract MislValue call();
}