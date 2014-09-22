package net.mobtalker.mobtalkerscript.api.library;

import net.mobtalker.mobtalkerscript.v2.value.MtsVarArgs;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

public interface IMinecraftCommandLib
{
    @MtsNativeFunction
    MtsVarArgs execute( MtsVarArgs args );
}