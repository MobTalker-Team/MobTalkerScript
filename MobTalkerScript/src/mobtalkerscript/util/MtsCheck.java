package mobtalkerscript.util;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;

public class MtsCheck
{
    public static void checkNotNil( MtsValue value )
    {
        if ( value.isNil() )
            throw new BadArgumentException( "value cannot be nil" );
    }
    
    public static void checkNotNil( MtsValue value, String msg )
    {
        if ( value.isNil() )
            throw new BadArgumentException( msg );
    }
}
