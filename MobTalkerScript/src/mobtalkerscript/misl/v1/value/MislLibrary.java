package mobtalkerscript.misl.v1.value;

import mobtalkerscript.misl.v1.*;

public abstract class MislLibrary extends MislFunction
{
    @Override
    public final MislValue call( MislValue... args )
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
    
    public abstract MislValue bind( MislString name, MislValue env );
}
