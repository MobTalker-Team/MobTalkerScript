package net.mobtalker.mobtalkerscript.api.library;

import java.util.List;

import net.mobtalker.mobtalkerscript.api.*;

public interface IEntityLibLogic
{
    String getName();
    
    // ========================================
    
    double getHealth();
    
    double getMaxHealth();
    
    // ========================================
    
    WorldPosition getPosition();
    
    // ========================================
    
    boolean isRiding();
    
    // ========================================
    
    List<EffectInfo> getEffects();
    
    boolean isValidEffect( String name );
    
    boolean applyEffect( String name, int duration, int amplifier );
    
    boolean removeEffect( String name );
    
    void removeAllEffects();
    
    // ========================================
    
    EquipmentInfo getEquipment();
    
    ItemInfo getEquipment( EquipmentSlot slot );
}
