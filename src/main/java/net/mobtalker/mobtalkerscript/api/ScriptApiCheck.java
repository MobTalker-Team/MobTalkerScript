package net.mobtalker.mobtalkerscript.api;

import static net.mobtalker.mobtalkerscript.v2.MtsCheck.*;
import net.mobtalker.mobtalkerscript.v2.BadArgumentException;
import net.mobtalker.mobtalkerscript.v2.value.MtsValue;

public class ScriptApiCheck
{
    public static EntityReaction checkReaction( MtsValue arg, int i )
    {
        String s = checkString( arg, i );
        
        EntityReaction result = EntityReaction.forName( s );
        if ( result == null )
            throw new BadArgumentException( i, "'%s' is not a valid reaction", s );
        
        return result;
    }
}
