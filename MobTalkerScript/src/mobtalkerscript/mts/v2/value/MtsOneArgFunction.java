package mobtalkerscript.mts.v2.value;

public abstract class MtsOneArgFunction extends MtsZeroArgFunction
{
    @Override
    public MtsValue call( MtsValue... args )
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
    public MtsValue call()
    {
        return call( NIL );
    }
    
    public abstract MtsValue call( MtsValue arg1 );
}