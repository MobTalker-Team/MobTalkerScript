package mobtalkerscript.mts.v2.lib;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;

public class ConsoleCommandLib extends MtsGlobalLibrary
{
    @Override
    protected void bind()
    {
        bindFunction( Constants.FunctionNames.COMMAND_SAY, new ShowText() );
        bindFunction( Constants.FunctionNames.COMMAND_SHOW, new ShowSprite() );
        bindFunction( Constants.FunctionNames.COMMAND_MENU, new ShowMenu() );
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
                        ? arg1.get( "Name" ).asString().asJavaString()
                        : arg1.asString().asJavaString();
                
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
                String basePath = characterArg.get( "SpritePath" ).toMtsString().asJavaString();
                pathBuilder.append( basePath );
            }
            else
            {
                String basePath = characterArg.toMtsString().asJavaString();
                pathBuilder.append( basePath );
            }
            
            // Path
            if ( pathArg.isTable() )
            {
                for ( MtsValue pathPart : pathArg.asTable().listView() )
                {
                    pathBuilder.append( "/" ).append( pathPart.toMtsString().asJavaString() );
                }
            }
            else
            {
                pathBuilder.append( "/" ).append( pathArg.toMtsString() );
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
                position = positionArg.toMtsString().asJavaString();
            }
            
            // Offset
            int offsetX = offsetXArg.asNumber().asJavaInt();
            int offsetY = offsetYArg.asNumber().asJavaInt();
            
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
                String caption = args.get( 0 ).asString().asJavaString();
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
                _G.out.println( "  " + ( i + 1 ) + ": " + args.get( i + 1 ).asString().asJavaString() );
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
