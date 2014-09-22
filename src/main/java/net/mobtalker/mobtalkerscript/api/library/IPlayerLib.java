package net.mobtalker.mobtalkerscript.api.library;

import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

public interface IPlayerLib
{
    @MtsNativeFunction
    MtsNumber getArmor();
    
    @MtsNativeFunction
    MtsVarArgs getExperience();
    
    @MtsNativeFunction
    void giveExperience( MtsValue argAmount );
    
    @MtsNativeFunction
    MtsString getGameMode();
    
    // ========================================
    
    @MtsNativeFunction
    MtsTable getInventory();
    
    @MtsNativeFunction
    MtsNumber getItemCount( MtsValue argItemName, MtsValue argItemMeta );
    
    @MtsNativeFunction
    MtsBoolean takeItems( MtsValue argItemName, MtsValue argItemCount, MtsValue argItemMeta );
    
    @MtsNativeFunction
    MtsBoolean giveItems( MtsValue argItemName, MtsValue argItemCount, MtsValue argItemMeta );
}