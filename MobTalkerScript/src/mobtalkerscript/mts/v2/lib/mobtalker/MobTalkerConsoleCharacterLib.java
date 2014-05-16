package mobtalkerscript.mts.v2.lib.mobtalker;

import mobtalkerscript.mts.v2.lib.*;
import mobtalkerscript.mts.v2.lib.mobtalker.character.*;
import mobtalkerscript.mts.v2.value.*;
import mobtalkerscript.mts.v2.value.userdata.*;

public class MobTalkerConsoleCharacterLib extends MtsGlobalLibrary
{
    
    @Override
    public void bind()
    {
        MtsTable lib = new MtsTable( 0, 1 );
        bindFunction( lib, "Create", new Create() );
        
        _G.set( "Character", lib );
    }
    
    // ========================================
    
    private static final class Create extends MtsTwoArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
        {
//            MtsTable c = new MtsTable( 0, 3 );
//            
//            c.set( "Name", arg1 );
//            
//            if ( !arg2.isNil() )
//            {
//                c.set( "SpritePath", arg2.asString() );
//                c.set( "SoundPath", arg2.asString() );
//            }
//            else
//            {
//                c.set( "SpritePath", EMPTY_STRING );
//                c.set( "SoundPath", EMPTY_STRING );
//            }
            
            return MtsNatives.wrapInstance( new MtsDummyCharacter() );
        }
        
    }
    
}
