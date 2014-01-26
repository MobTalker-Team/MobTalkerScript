package mobtalkerscript.mts.v2.value;

public abstract class MtsThreeArgFunction extends MtsTwoArgFunction
{
    @Override
    public MtsValue call( MtsValue... args )
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
    public MtsValue call( MtsValue arg1, MtsValue arg2 )
    {
        return call( arg1, arg2, NIL );
    }
    
    public abstract MtsValue call( MtsValue arg1, MtsValue arg2, MtsValue arg3 );
}