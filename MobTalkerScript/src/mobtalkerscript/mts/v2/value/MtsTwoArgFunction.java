package mobtalkerscript.mts.v2.value;

public abstract class MtsTwoArgFunction extends MtsOneArgFunction
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
            default:
                return call( args[0], args[1] );
        }
    }
    
    @Override
    public MtsValue call( MtsValue arg1 )
    {
        return call( arg1, NIL );
    }
    
    public abstract MtsValue call( MtsValue arg1, MtsValue arg2 );
}