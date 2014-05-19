package mobtalkerscript.mts.v2.value.userdata;

import java.lang.reflect.*;

import mobtalkerscript.mts.v2.value.*;

/* package */class FunctionAdapter extends JavaMethodAdapter
{
    public FunctionAdapter( Method method, String name )
    {
        super( method, name );
    }
    
    // ========================================
    
    @Override
    protected Object getCallInstance( MtsVarArgs args )
    {
        return null;
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
