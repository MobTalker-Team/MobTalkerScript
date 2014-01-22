package mobtalkerscript.misl.v1.value;

public abstract class MislTwoArgFunction extends MislOneArgFunction
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
            default:
                return call( args[0], args[1] );
        }
    }
    
    @Override
    public MislValue call( MislValue arg1 )
    {
        return call( arg1, NIL );
    }
    
    public abstract MislValue call( MislValue arg1, MislValue arg2 );
}