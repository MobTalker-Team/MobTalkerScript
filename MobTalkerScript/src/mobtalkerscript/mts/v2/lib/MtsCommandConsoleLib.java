package mobtalkerscript.mts.v2.lib;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;

public class MtsCommandConsoleLib extends MtsCommandLib
{
    MtsGlobals _G;
    
    @Override
    public MtsValue bind( MtsString name, MtsValue env )
    {
        checkIfGlobals( env );
        
        _G = (MtsGlobals) env;
        
        env.set( FNAME_SAY, new ShowText() );
        env.set( FNAME_SHOW, new ShowSprite() );
        
        return null;
    }
    
    // ========================================
    
    private final class ShowText extends MtsThreeArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
        {
            StringBuilder s = new StringBuilder();
            
            if ( !arg1.isNil() )
            {
                s.append( '[' ).append( arg1.asString().toJava() ).append( "] " );
            }
            
            s.append( arg2.asString() );
            
            if ( !arg3.asBoolean().toJava() )
            {
                s.append( " \u25B6" );
            }
            
            _G.out.println( s.toString() );
            
            return EMPTY_VARARGS;
        }
    }
    
    // ========================================
    
    private final class ShowSprite extends MtsThreeArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
        {
            return EMPTY_VARARGS;
        }
    }
    
}
