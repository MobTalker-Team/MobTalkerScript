package mobtalkerscript.mts.v2.lib.mobtalker;

import mobtalkerscript.mts.v2.value.*;

public class MobTalkerConsoleCharacterLib extends MtsLibrary
{
    
    @Override
    public MtsValue bind( MtsString name, MtsValue env )
    {
        checkIfGlobals( env );
        
        MtsTable lib = new MtsTable( 0, 1 );
        bindFunction( lib, "Create", new Create() );
        
        env.set( "Character", lib );
        
        return null;
    }
    
    // ========================================
    
    private static final class Create extends MtsTwoArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
        {
            MtsTable c = new MtsTable( 0, 3 );
            
            c.set( "Name", arg1 );
            
            if ( !arg2.isNil() )
            {
                c.set( "SpritePath", arg2.asString() );
                c.set( "SoundPath", arg2.asString() );
            }
            else
            {
                c.set( "SpritePath", EMPTY_STRING );
                c.set( "SoundPath", EMPTY_STRING );
            }
            
            return c;
        }
        
    }
    
}
