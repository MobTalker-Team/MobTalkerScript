package mobtalkerscript.mts.v2.lib;

import java.io.*;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;

public class MtsCommandConsoleLib extends MtsCommandLib
{
    @Override
    public MtsValue bind( MtsString name, MtsValue env )
    {
        checkIfGlobals( env );
        
        env.set( FNAME_SAY, new ShowText() );
        env.set( FNAME_SHOW, new ShowSprite() );
        env.set( FNAME_MENU, new ShowMenu() );
        
        return null;
    }
    
    // ========================================
    
    private static final class ShowText extends MtsThreeArgFunction
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
            
            System.out.println( s.toString() );
            
            return EMPTY_VARARGS;
        }
    }
    
    // ========================================
    
    private static final class ShowSprite extends MtsThreeArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
        {
            return EMPTY_VARARGS;
        }
    }
    
    // ========================================
    
    private static final class ShowMenu extends MtsJavaFunction
    {
        
        @Override
        protected MtsValue invoke( MtsVarArgs args )
        {
            if ( !args.get( 0 ).isNil() )
            {
                String caption = args.get( 0 ).asString().toJava();
                System.out.println( caption );
            }
            
            int nOptions = args.count() - 1;
            if ( nOptions < 1 )
                throw new ScriptRuntimeException( "Must have at least 1 option!" );
            
            for ( int i = 0; i < nOptions; i++ )
            {
                System.out.println( "  " + ( i + 1 ) + ": " + args.get( i + 1 ).asString().toJava() );
            }
            
            for ( ;; )
            {
                System.out.println( "Enter your choice" );
                try
                {
                    String input = new BufferedReader( new InputStreamReader( System.in ) ).readLine();
                    int choice = Integer.parseInt( input );
                    if ( ( 0 < choice ) && ( choice <= nOptions ) )
                        return valueOf( choice );
                }
                catch ( Exception ex )
                {}
            }
        }
    }
}
