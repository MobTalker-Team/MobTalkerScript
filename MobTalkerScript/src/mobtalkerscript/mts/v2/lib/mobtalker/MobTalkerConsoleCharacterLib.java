package mobtalkerscript.mts.v2.lib.mobtalker;

import static mobtalkerscript.mts.v2.value.MtsValue.*;
import static mobtalkerscript.mts.v2.value.userdata.MtsNatives.*;
import mobtalkerscript.mts.v2.lib.mobtalker.character.*;
import mobtalkerscript.mts.v2.value.*;
import mobtalkerscript.mts.v2.value.userdata.*;

public class MobTalkerConsoleCharacterLib
{
    @MtsNativeFunction
    public static MtsValue create( MtsValue arg1, MtsValue arg2 )
    {
        MtsTable mt = new MtsTable( 0, 1 );
        mt.set( __INDEX, createLibrary( new MtsDummyCharacter() ) );
        
        MtsTable table = new MtsTable();
        table.setMetaTable( mt );
        
        return table;
    }
}
