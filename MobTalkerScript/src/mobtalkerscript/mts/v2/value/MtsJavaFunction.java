package mobtalkerscript.mts.v2.value;

import mobtalkerscript.mts.v2.*;

/**
 * Base class for functions implemented in Java. Takes care of handling runtime exceptions.
 */
public abstract class MtsJavaFunction extends MtsFunction
{
    @Override
    public final MtsVarArgs call( MtsVarArgs args )
    {
        try
        {
            MtsValue result = invoke( args );
            return result.isVarArgs() ? result.asVarArgs() : new MtsVarArgs( result );
        }
        catch ( ScriptRuntimeException ex )
        {
            String name = getClass().getSimpleName();
            ex.addStackTraceElement( new MtsStackTraceElement( name ) );
            
            throw ex;
        }
    }
    
    protected abstract MtsValue invoke( MtsVarArgs args );
}
