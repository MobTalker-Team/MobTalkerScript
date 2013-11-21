package mobtalkerscript.misl.v1.lib;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;

public class MislTableLib implements IMislLibrary
{
    
    @Override
    public void loadInto( IBindings env )
    {
        MislTable t = new MislTable( 3 );
        t.set( "Random", new Random() );
        
        env.set( "table", t );
    }
    
    // ========================================
    
    private static final class Random extends MislVarArgFunction
    {
        private static final java.util.Random rnd = new java.util.Random();
        
        @Override
        public MislValue call( IBindings env, MislValue... args )
        {
            if ( ( args == null ) || ( args.length == 0 ) )
            {
                return null;
            }
            
            if ( args.length == 1 )
            {
                if ( args[0].isTable() )
                {
                    return args[0].asTable().getRandomElement( rnd );
                }
                else
                {
                    return args[0];
                }
            }
            else
            {
                return args[rnd.nextInt( args.length )];
            }
        }
    }
    
}
