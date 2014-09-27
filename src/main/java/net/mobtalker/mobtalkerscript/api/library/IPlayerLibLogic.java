package net.mobtalker.mobtalkerscript.api.library;

import net.mobtalker.mobtalkerscript.api.InventoryInfo;

public interface IPlayerLibLogic
{
    /**
     * @return <code>survival</code>, <code>adventure</code> or <code>creative</code>
     */
    String getGameMode();
    
    // ========================================
    
    /**
     * Total armor damage reduction
     */
    double getArmor();
    
    // ========================================
    
    /**
     * Current experience level
     */
    int getExperienceLevel();
    
    /**
     * Current experience progress on the current level
     */
    int getExperience();
    
    /**
     * Experience required to reach the next level
     */
    int getExperienceRequired();
    
    /**
     * Give the specific amount of experience
     */
    void giveExperience( int amount );
    
    /**
     * Tries to take that much experience
     */
    boolean takeExperience( int amount );
    
    // ========================================
    
    boolean isValidItem( String name );
    
    int getItemCount( String name, int meta );
    
    boolean takeItems( String name, int meta, int amount );
    
    boolean giveItems( String name, int meta, int amount );
    
    InventoryInfo getInventory();
}