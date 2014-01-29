package mobtalkerscript.mts.v2.value;

import mobtalkerscript.mts.v2.*;

public abstract class MtsLibrary extends MtsFunction
{
    @Override
    public String getName()
    {
        return getClass().getSimpleName();
    }
    
    @Override
    public final MtsValue call( MtsValue... args )
    {
        switch ( args.length )
        {
            case 2:
                return bind( args[0].toStringMts(), args[1] );
            default:
                throw new ScriptRuntimeException( "wrong number of arguments for binding library (expected 2, got %s)",
                                                  args.length );
        }
    }
    
    public abstract MtsValue bind( MtsString name, MtsValue env );
    
    protected void bindFunction( MtsValue env, MtsJavaFunction func )
    {
        env.set( func.getName(), func );
    }
}
