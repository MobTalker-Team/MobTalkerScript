package mobtalkerscript.mts.v2.lib;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;

public class ConsoleCommandLib extends MtsCommandLib
{
    /* package */MtsGlobals _G;
    
    // ========================================
    
    @Override
    public MtsValue bind( MtsString name, MtsValue env )
    {
        checkIfGlobals( env );
        
        _G = (MtsGlobals) env;
        
        env.set( FNAME_SAY, new ShowText() );
        env.set( FNAME_SHOW, new ShowSprite() );
        env.set( FNAME_MENU, new ShowMenu() );
        
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
                String name = arg1.isTable() //
                        ? arg1.get( "Name" ).asString().toJava()
                        : arg1.asString().toJava();
                
                s.append( '[' ).append( name ).append( "] " );
            }
            
            s.append( arg2.asString() );
            
            if ( !arg3.asBoolean().toJava() )
                s.append( " \u25BA" );
            else
                s.append( " \u25A0" );
            
            _G.out.println( s.toString() );
            
            return EMPTY_VARARGS;
        }
    }
    
    // ========================================
    
    private final class ShowSprite extends MtsJavaFunction
    {
        @Override
        protected MtsValue invoke( MtsVarArgs args )
        {
            MtsValue characterArg = args.get( 0 );
            MtsValue pathArg = args.get( 1 );
            MtsValue positionArg = args.get( 2 );
            MtsValue offsetXArg = args.get( 3 );
            MtsValue offsetYArg = args.get( 4 );
//            MtsValue effectArg = args.get( 5 );
            
            // Character
            StringBuilder pathBuilder = new StringBuilder();
            if ( characterArg.isTable() )
            {
                String basePath = characterArg.get( "SpritePath" ).toStringMts().toJava();
                pathBuilder.append( basePath );
            }
            else
            {
                String basePath = characterArg.toStringMts().toJava();
                pathBuilder.append( basePath );
            }
            
            // Path
            if ( pathArg.isTable() )
            {
                for ( MtsValue pathPart : pathArg.asTable().listView() )
                {
                    pathBuilder.append( "/" ).append( pathPart.toStringMts().toJava() );
                }
            }
            else
            {
                pathBuilder.append( "/" ).append( pathArg.toStringMts() );
            }
            
            String path = pathBuilder.toString();
            
            if ( !path.endsWith( ".png" ) )
            {
                path += ".png";
            }
            
            // Position
            String position;
            if ( positionArg.isNil() )
            {
                position = "center";
            }
            else
            {
                position = positionArg.toStringMts().toJava();
            }
            
            // Offset
            int offsetX = (int) offsetXArg.asNumber().asInteger().toJava();
            int offsetY = (int) offsetYArg.asNumber().asInteger().toJava();
            
            // Effect
            String effect = "none";
            
            _G.out.println( "Displaying sprite '"
                            + path
                            + "' at "
                            + position
                            + "["
                            + offsetX
                            + ","
                            + offsetY
                            + "] with effect '"
                            + effect + "'." );
            
            return EMPTY_VARARGS;
        }
    }
    
    // ========================================
    
    private final class ShowMenu extends MtsJavaFunction
    {
        @Override
        protected MtsValue invoke( MtsVarArgs args )
        {
            if ( !args.get( 0 ).isNil() )
            {
                String caption = args.get( 0 ).asString().toJava();
                _G.out.println( caption );
            }
            else
            {
                _G.out.println( "Make your choice" );
            }
            
            int nOptions = args.count() - 1;
            if ( nOptions < 1 )
                throw new ScriptRuntimeException( "Must have at least 1 option!" );
            
            for ( int i = 0; i < nOptions; i++ )
            {
                _G.out.println( "  " + ( i + 1 ) + ": " + args.get( i + 1 ).asString().toJava() );
            }
            
            for ( ;; )
            {
                _G.out.print( "> " );
                try
                {
                    String input = _G.in.readLine();
                    int choice = Integer.parseInt( input );
                    if ( ( 0 < choice ) && ( choice <= nOptions ) )
                        return valueOf( choice );
                }
                catch ( Exception ex )
                {
                    _G.out.println( ex.getMessage() );
                }
            }
        }
    }
}
