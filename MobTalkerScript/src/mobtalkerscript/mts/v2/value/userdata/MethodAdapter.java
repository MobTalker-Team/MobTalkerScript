package mobtalkerscript.mts.v2.value.userdata;

import java.lang.reflect.*;

import mobtalkerscript.mts.v2.value.*;

public class MethodAdapter extends JavaMethodAdapter
{
    public MethodAdapter( Method method )
    {
        super( method );
    }
    
    // ========================================
    
    @Override
    protected Object getCallInstance( MtsVarArgs args )
    {
        return args.get( 0 );
    }
    
    @Override
    protected Object[] getCallArguments( MtsVarArgs args, int nParams )
    {
        if ( nParams == -1 )
            return new Object[] { args.subArgs( 1 ) };
        if ( nParams == 0 )
            return EMPTY_CALLARGS;
        
        Object[] result = new Object[nParams];
        for ( int i = 1; i <= nParams; i++ )
        {
            result[i] = args.get( i );
        }
        
        return result;
    }
}
