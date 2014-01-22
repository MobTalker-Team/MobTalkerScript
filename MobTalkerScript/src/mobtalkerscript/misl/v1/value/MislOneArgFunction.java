package mobtalkerscript.misl.v1.value;

public abstract class MislOneArgFunction extends MislZeroArgFunction
{
    @Override
    public MislValue call( MislValue... args )
    {
        switch ( args.length )
        {
            case 0:
                return call();
            default:
                return call( args[0] );
        }
    }
    
    @Override
    public MislValue call()
    {
        return call( NIL );
    }
    
    public abstract MislValue call( MislValue arg1 );
}