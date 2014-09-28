package net.mobtalker.mobtalkerscript.api.library;

import net.mobtalker.mobtalkerscript.api.*;

public interface ICreatureLibLogic
{
    void setName( String name );
    
    // ========================================
    
    boolean isValidItem( String name );
    
    void setEquipment( EquipmentSlot slot, ItemInfo itemInfo );
    
    // ========================================
    
    void setReaction( String key, EntityReaction reaction );
    
    EntityReaction getReaction( String key );
    
    // ========================================
    
    void follow( String key );
    
    void stopFollowing();
}
