package mobtalkerscript.mts.v2.value;

public abstract class MtsOneArgFunction extends MtsZeroArgFunction
{
    @Override
    protected MtsValue invoke( MtsValue... args )
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
    protected MtsValue invoke()
    {
        return invoke( NIL );
    }
    
    protected abstract MtsValue invoke( MtsValue arg );
}