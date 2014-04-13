package mobtalkerscript.mts.v2.value;

import mobtalkerscript.mts.v2.*;

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
            return result.isVarArgs() ? result : new MtsVarArgs( result );
        }
        catch ( ScriptRuntimeException ex )
        {
            String name = getClass().getSimpleName();
            ex.addStackTraceElement( new MtsStackTraceElement( name ) );
            
            throw ex;
        }
    }
    
    protected abstract MtsValue invoke( MtsVarArgs args );
    
    // ========================================
    
    public void checkType( MtsValue value, int i, MtsType expected )
    {
        if ( value.getType() != expected )
            // TODO
            throw new BadArgumentException( "(%s expected, got %s)", expected, value.getType() );
    }
    
    public void checkType( MtsValue value, int i, MtsType... expected )
    {
        for ( MtsType t : expected )
        {
            if ( t == value.getType() )
                return;
        }
        
        StringBuilder msg = new StringBuilder();
        msg.append( "(" );
        
        msg.append( expected[0].getName() );
        int to = expected.length - 1;
        for ( int j = 1; j < to; j++ )
        {
            msg.append( ", " ).append( expected[j].getName() );
        }
        msg.append( "or " ).append( expected[to].getName() );
        msg.append( " expected, got %s)" );
        
        throw new BadArgumentException( msg.toString(), value.getType() );
    }
    
    public void checkTable( MtsValue value, int i )
    {
        if ( value.getType() == MtsType.TABLE )
            return;
        
        throw new BadArgumentException( "(%s expected, got %s)", MtsType.TABLE.getName(), value.getType() );
    }
    
    public void checkString( MtsValue value, int i )
    {
        if ( ( value.getType() == MtsType.STRING ) || ( value.getType() == MtsType.NUMBER ) )
            return;
        
        throw new BadArgumentException( "(%s expected, got %s)", MtsType.STRING.getName(), value.getType() );
    }
    
    public void checkNumber( MtsValue value, int i )
    {
        if ( ( value.getType() == MtsType.NUMBER ) || ( value.getType() == MtsType.STRING ) )
            return;
        
        throw new ScriptRuntimeException( "(%s expected, got %s)", MtsType.NUMBER.getName(), value.getType() );
    }
}
