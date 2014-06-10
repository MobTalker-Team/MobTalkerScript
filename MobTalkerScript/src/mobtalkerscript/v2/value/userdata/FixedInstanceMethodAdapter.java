package mobtalkerscript.v2.value.userdata;

import static com.google.common.base.Preconditions.*;

import java.lang.reflect.*;

import mobtalkerscript.v2.value.*;

/* package */class FixedInstanceMethodAdapter extends JavaMethodAdapter
{
    private final Object _instance;
    
    // ========================================
    
    public FixedInstanceMethodAdapter( Object instance, Method method, String name )
    {
        super( method, name );
        checkNotNull( instance );
        
        if ( !method.getDeclaringClass().isAssignableFrom( instance.getClass() ) )
            throw new IllegalArgumentException( String.format( "instance is of type %s that is neither the same or a subclass of %s!",
                                                               instance.getClass().getName(),
                                                               method.getDeclaringClass().getName() ) );
        
        _instance = instance;
    }
    
    // ========================================
    
    @Override
    protected Object getCallInstance( MtsVarArgs args )
    {
        return _instance;
    }
    
    @Override
    protected Object[] getCallArguments( MtsVarArgs args, int nParams )
    {
        if ( nParams == -1 )
            return new Object[] { args };
        if ( nParams == 0 )
            return EMPTY_CALLARGS;
        
        Object[] result = new Object[nParams];
        for ( int i = 0; i < nParams; i++ )
        {
            result[i] = args.get( i );
        }
        
        return result;
    }
    
}
