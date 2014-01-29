package mobtalkerscript.mts.v2.value;

public abstract class MtsTwoArgFunction extends MtsOneArgFunction
{
    @Override
    protected MtsValue invoke( MtsValue... args )
    {
        switch ( args.length )
        {
            case 0:
                return call();
            case 1:
                return call( args[0] );
            default:
                return call( args[0], args[1] );
        }
    }
    
    @Override
    protected MtsValue invoke( MtsValue arg )
    {
        return call( arg, NIL );
    }
    
    protected abstract MtsValue invoke( MtsValue arg1, MtsValue arg2 );
}