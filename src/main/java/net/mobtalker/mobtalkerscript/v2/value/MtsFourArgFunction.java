package mobtalkerscript.v2.value;

public abstract class MtsFourArgFunction extends MtsThreeArgFunction
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
            case 2:
                return invoke( args.get( 0 ), args.get( 1 ) );
            case 3:
                return invoke( args.get( 0 ), args.get( 1 ), args.get( 2 ) );
            default:
                return invoke( args.get( 0 ), args.get( 1 ), args.get( 2 ), args.get( 3 ) );
        }
    }
    
    @Override
    protected MtsValue invoke( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
    {
        return invoke( arg1, arg2, arg3, NIL );
    }
    
    protected abstract MtsValue invoke( MtsValue arg1, MtsValue arg2, MtsValue arg3, MtsValue arg4 );
}
