package mobtalkerscript.mts.v2.value;

public abstract class MtsThreeArgFunction extends MtsTwoArgFunction
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
            case 2:
                return call( args[0], args[1] );
            default:
                return call( args[0], args[1], args[2] );
        }
    }
    
    @Override
    protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
    {
        return call( arg1, arg2, NIL );
    }
    
    protected abstract MtsValue invoke( MtsValue arg1, MtsValue arg2, MtsValue arg3 );
}