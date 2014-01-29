package mobtalkerscript.mts.v2.value;

import mobtalkerscript.mts.v2.*;

/**
 * Base class for functions implemented in Java. Takes care of handling runtime exceptions.
 */
public abstract class MtsJavaFunction extends MtsFunction
{
    @Override
    public final MtsValue call( MtsValue... args )
    {
        try
        {
            return invoke( args );
        }
        catch ( ScriptRuntimeException ex )
        {
            String name = getClass().getSimpleName();
            ex.addStackTraceElement( new MtsStackTraceElement( name ) );
            
            throw ex;
        }
    }
    
    protected abstract MtsValue invoke( MtsValue... args );
    
    // ========================================
    
    public void checkType( MtsValue value, int i, MtsType expected )
    {
        if ( value.getType() != expected )
            throw new ScriptRuntimeException( "bad argument #%s to '%s' (%s expected, got %s)",
                                              i,
                                              getName(),
                                              expected,
                                              value.getType() );
    }
    
    public void checkType( MtsValue value, int i, MtsType... expected )
    {
        for ( MtsType t : expected )
        {
            if ( t == value.getType() )
                return;
        }
        
        StringBuilder msg = new StringBuilder();
        msg.append( "bad argument #%s to '%s' (" );
        
        msg.append( expected[0].getName() );
        int to = expected.length - 1;
        for ( int j = 1; j < to; j++ )
        {
            msg.append( ", " ).append( expected[j].getName() );
        }
        msg.append( "or " ).append( expected[to].getName() );
        msg.append( " expected, got %s" );
        
        throw new ScriptRuntimeException( msg.toString(), i, getName(), value.getType() );
    }
    
    public void checkTable( MtsValue value, int i )
    {
        if ( value.getType() == MtsType.TABLE )
            return;
        
        throw new ScriptRuntimeException( "bad argument #%s to '%s' (%s expected, got %s)",
                                          i,
                                          getName(),
                                          MtsType.TABLE.getName(),
                                          value.getType() );
    }
    
    public void checkString( MtsValue value, int i )
    {
        if ( ( value.getType() == MtsType.STRING ) || ( value.getType() == MtsType.NUMBER ) )
            return;
        
        throw new ScriptRuntimeException( "bad argument #%s to '%s' (%s expected, got %s)",
                                          i,
                                          getName(),
                                          MtsType.STRING.getName(),
                                          value.getType() );
    }
    
    public void checkNumber( MtsValue value, int i )
    {
        if ( ( value.getType() == MtsType.NUMBER ) || ( value.getType() == MtsType.STRING ) )
            return;
        
        throw new ScriptRuntimeException( "bad argument #%s to '%s' (%s expected, got %s)",
                                          i,
                                          getName(),
                                          MtsType.NUMBER.getName(),
                                          value.getType() );
    }
}
