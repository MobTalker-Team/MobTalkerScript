package mobtalkerscript.misl.v1.value;

public abstract class MislThreeArgFunction extends MislTwoArgFunction
{
    @Override
    public MislValue call( MislValue... args )
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
    public MislValue call( MislValue arg1, MislValue arg2 )
    {
        return call( arg1, arg2, NIL );
    }
    
    public abstract MislValue call( MislValue arg1, MislValue arg2, MislValue arg3 );
}