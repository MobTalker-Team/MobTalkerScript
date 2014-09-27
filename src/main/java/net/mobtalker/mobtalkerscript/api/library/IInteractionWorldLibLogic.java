package net.mobtalker.mobtalkerscript.api.library;

import java.util.Map;

import net.mobtalker.mobtalkerscript.api.IWorldInfo;

public interface IInteractionWorldLibLogic
{
    Map<Integer, IWorldInfo> getWorlds();
    
    /**
     * The current world where the interaction takes place.
     */
    IWorldInfo getWorld();
    
    // ========================================
    
    String getDifficulty();
    
    boolean isHardcore();
    
    boolean getGameRule( String rule );
    
    // ========================================
    
    String getBiome();
    
    String getTemperature();
    
    String getHumidity();
    
    String getWeather();
    
    boolean isThundering();
    
    boolean isStorming();
}
