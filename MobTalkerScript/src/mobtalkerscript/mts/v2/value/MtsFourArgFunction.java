package mobtalkerscript.mts.v2.value;

public abstract class MtsFourArgFunction extends MtsThreeArgFunction
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
            case 3:
                return call( args[0], args[1], args[2] );
            default:
                return call( args[0], args[1], args[2], args[3] );
        }
    }
    
    @Override
    public MtsValue call( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
    {
        return call( arg1, arg2, arg3, NIL );
    }
    
    public abstract MtsValue call( MtsValue arg1, MtsValue arg2, MtsValue arg3, MtsValue arg4 );
}
