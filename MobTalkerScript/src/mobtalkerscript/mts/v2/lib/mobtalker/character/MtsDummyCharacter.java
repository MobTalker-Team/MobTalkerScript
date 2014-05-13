package mobtalkerscript.mts.v2.lib.mobtalker.character;

import static mobtalkerscript.mts.v2.value.MtsValue.*;
import mobtalkerscript.mts.v2.value.*;
import mobtalkerscript.mts.v2.value.userdata.*;

@MtsAccessibleClass( name = "character" )
public class MtsDummyCharacter
{
    
    @MtsCallableMethod( name = "GetName" )
    public MtsString getName()
    {
        return valueOf( "FooBar" );
    }
}
