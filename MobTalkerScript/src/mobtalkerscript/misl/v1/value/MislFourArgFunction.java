package mobtalkerscript.misl.v1.value;

import mobtalkerscript.misl.v1.*;

public abstract class MislFourArgFunction extends MislThreeArgFunction
{
    @Override
    public MislValue call( IBindings env, MislValue... args )
    {
        switch ( args.length )
        {
            case 1:
                return call( env, args[0] );
            case 2:
                return call( env, args[0], args[1] );
            case 3:
                return call( env, args[0], args[1], args[2] );
            case 4:
                return call( env, args[0], args[1], args[2], args[3] );
            default:
                return call( env );
        }
    }
    
    @Override
    public MislValue call( IBindings env, MislValue arg1, MislValue arg2, MislValue arg3 )
    {
        return call( env, arg1, arg2, arg3, NIL );
    }
    
    public abstract MislValue call( IBindings env,
                                    MislValue arg1,
                                    MislValue arg2,
                                    MislValue arg3,
                                    MislValue arg4 );
}
