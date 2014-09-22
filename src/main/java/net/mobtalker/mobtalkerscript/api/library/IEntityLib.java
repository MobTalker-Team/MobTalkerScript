package net.mobtalker.mobtalkerscript.api.library;

import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

public interface IEntityLib
{
    @MtsNativeFunction
    MtsString getName();
    
    @MtsNativeFunction
    MtsVarArgs getHealth();
    
    @MtsNativeFunction
    MtsVarArgs getPosition();
    
    @MtsNativeFunction
    MtsBoolean isRiding();
    
    // ========================================
    
    @MtsNativeFunction
    MtsTable getEffects();
    
    @MtsNativeFunction
    MtsBoolean applyEffect( MtsValue argEffect, MtsValue argDuration, MtsValue argAmplifier );
    
    @MtsNativeFunction
    MtsBoolean removeEffect( MtsValue argEffect );
    
    @MtsNativeFunction
    void removeAllEffects();
    
    // ========================================
    
    @MtsNativeFunction
    MtsValue getEquipment( MtsValue argSlot );
}