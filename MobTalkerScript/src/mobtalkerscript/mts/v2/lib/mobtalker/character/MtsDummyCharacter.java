package mobtalkerscript.mts.v2.lib.mobtalker.character;

import static mobtalkerscript.mts.v2.value.MtsValue.*;
import mobtalkerscript.mts.v2.value.*;
import mobtalkerscript.mts.v2.value.userdata.*;

@MtsNativeClass( name = "character" )
public class MtsDummyCharacter
{
    
    @MtsNativeMethod( name = "GetName" )
    public MtsString getName()
    {
        return valueOf( "FooBar" );
    }
}
