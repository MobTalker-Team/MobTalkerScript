package mobtalkerscript.mts.v2.value;

import mobtalkerscript.mts.v2.*;

public abstract class MtsLibrary extends MtsFunction
{
    @Override
    public final MtsValue call( MtsVarArgs args )
    {
        switch ( args.count() )
        {
            case 2:
                return MtsVarArgs.of( bind( args.get( 0 ).toMtsString(), args.get( 1 ) ) );
            default:
                throw new ScriptRuntimeException( "wrong number of arguments for binding library (expected 2, got %s)",
                                                  args.count() );
        }
    }
    
    public abstract MtsValue bind( MtsString name, MtsValue env );
    
    protected static void bindFunction( MtsValue env, String name, MtsJavaFunction func )
    {
        env.set( name, func, false );
    }
}
