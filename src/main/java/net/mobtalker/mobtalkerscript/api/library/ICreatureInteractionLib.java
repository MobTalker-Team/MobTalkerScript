package net.mobtalker.mobtalkerscript.api.library;

import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

public interface ICreatureInteractionLib
{
    @MtsNativeFunction
    void followPlayer();
    
    @MtsNativeFunction
    void stopFollowing();
}
