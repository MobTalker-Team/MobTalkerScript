package mobtalkerscript.mts.v2.lib;

import static mobtalkerscript.mts.v2.value.MtsValue.*;
import mobtalkerscript.mts.v2.value.*;
import mobtalkerscript.mts.v2.value.userdata.*;
import mobtalkerscript.util.*;

import org.apache.commons.lang3.*;

public class MtsStringLib
{
    
    @MtsNativeFunction
    public static MtsString reverse( MtsValue arg1 )
    {
        return valueOf( StringUtils.reverse( MtsCheck.checkString( arg1, 0 ) ) );
    }
}
