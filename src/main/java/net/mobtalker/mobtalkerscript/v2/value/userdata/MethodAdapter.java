package net.mobtalker.mobtalkerscript.v2.value.userdata;

import java.lang.reflect.*;

import net.mobtalker.mobtalkerscript.v2.value.*;

/* package */class MethodAdapter extends JavaMethodAdapter
{
    public MethodAdapter( Method method, String name )
    {
        super( method, name );
    }
    
    // ========================================
    
    @Override
    protected Object getCallInstance( MtsVarArgs args )
    {
        return args.get( 0 ).asUserdata().asJavaObject();
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
