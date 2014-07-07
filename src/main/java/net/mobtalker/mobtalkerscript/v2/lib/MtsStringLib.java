package net.mobtalker.mobtalkerscript.v2.lib;

import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;
import net.mobtalker.mobtalkerscript.util.*;
import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.userdata.*;

import org.apache.commons.lang3.*;

public class MtsStringLib
{
    @MtsNativeFunction
    public static MtsString reverse( MtsValue arg1 )
    {
        return valueOf( StringUtils.reverse( MtsCheck.checkString( arg1, 0 ) ) );
    }
}
