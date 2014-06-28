package mobtalkerscript.v2.lib;

import static mobtalkerscript.v2.value.MtsValue.*;
import mobtalkerscript.util.*;
import mobtalkerscript.v2.value.*;
import mobtalkerscript.v2.value.userdata.*;

import org.apache.commons.lang3.*;

public class MtsStringLib
{
    @MtsNativeFunction
    public static MtsString reverse( MtsValue arg1 )
    {
        return valueOf( StringUtils.reverse( MtsCheck.checkString( arg1, 0 ) ) );
    }
}
