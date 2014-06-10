package mobtalkerscript.v2.value;

import mobtalkerscript.v2.*;

/**
 * Base class for functions implemented in Java. Takes care of handling runtime exceptions.
 */
public abstract class MtsJavaFunction extends MtsFunction
{
    @Override
    public final MtsValue call( MtsVarArgs args )
    {
        try
        {
            MtsValue result = invoke( args );
            return result.isVarArgs() ? result.asVarArgs() : MtsVarArgs.of( result );
        }
        catch ( ScriptRuntimeException ex )
        {
            ex.addStackTraceElement( getClass().getSimpleName() );
            throw ex;
        }
    }
    
    protected abstract MtsValue invoke( MtsVarArgs args );
}
