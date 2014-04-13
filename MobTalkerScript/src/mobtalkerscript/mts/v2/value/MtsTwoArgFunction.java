package mobtalkerscript.mts.v2.value;

public abstract class MtsTwoArgFunction extends MtsOneArgFunction
{
    @Override
    protected MtsValue invoke( MtsVarArgs args )
    {
        switch ( args.count() )
        {
            case 0:
                return invoke();
            case 1:
                return invoke( args.get( 0 ) );
            default:
                return invoke( args.get( 0 ), args.get( 1 ) );
        }
    }
    
    @Override
    protected MtsValue invoke( MtsValue arg )
    {
        return invoke( arg, NIL );
    }
    
    protected abstract MtsValue invoke( MtsValue arg1, MtsValue arg2 );
}