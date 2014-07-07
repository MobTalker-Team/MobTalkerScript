package net.mobtalker.mobtalkerscript.v2.lib.console.mobtalker;

import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;
import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

public class MinecraftCommandLib
{
    @MtsNativeFunction
    public MtsBoolean execute( MtsVarArgs args )
    {
        // NOOP
        return TRUE;
    }
}
