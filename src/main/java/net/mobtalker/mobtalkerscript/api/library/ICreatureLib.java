package net.mobtalker.mobtalkerscript.api.library;

import net.mobtalker.mobtalkerscript.v2.value.MtsValue;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

public interface ICreatureLib
{
    @MtsNativeFunction
    void setName( MtsValue argName );
    
    // ========================================
    
    @MtsNativeFunction
    MtsValue getReaction( MtsValue argKey );
    
    @MtsNativeFunction
    void setReaction( MtsValue arg1, MtsValue arg2 );
    
    // ========================================
    
    @MtsNativeFunction
    void setEquipment( MtsValue argSlot, MtsValue argItemName, MtsValue argMeta );
}