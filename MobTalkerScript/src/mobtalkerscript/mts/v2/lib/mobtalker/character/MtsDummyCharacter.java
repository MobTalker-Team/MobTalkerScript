package mobtalkerscript.mts.v2.lib.mobtalker.character;

import static mobtalkerscript.mts.v2.value.MtsValue.*;
import mobtalkerscript.mts.v2.value.*;
import mobtalkerscript.mts.v2.value.userdata.*;

@MtsNativeClass
public class MtsDummyCharacter
{
    @MtsNativeType
    public static final MtsType TYPE = MtsType.forName( "character" );
    
    @MtsNativeFunction
    public MtsString getName()
    {
        return valueOf( "FooBar" );
    }
}
