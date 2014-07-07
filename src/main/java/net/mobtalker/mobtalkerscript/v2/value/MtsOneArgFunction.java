package net.mobtalker.mobtalkerscript.v2.value;

public abstract class MtsOneArgFunction extends MtsZeroArgFunction
{
    @Override
    protected MtsValue invoke( MtsVarArgs args )
    {
        switch ( args.count() )
        {
            case 0:
                return invoke();
            default:
                return invoke( args.get( 0 ) );
        }
    }
    
    @Override
    protected MtsValue invoke()
    {
        return invoke( NIL );
    }
    
    protected abstract MtsValue invoke( MtsValue arg );
}