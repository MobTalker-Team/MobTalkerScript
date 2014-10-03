package net.mobtalker.mobtalkerscript.standalone.lib;

import java.util.Map;

import net.mobtalker.mobtalkerscript.api.IWorldInfo;
import net.mobtalker.mobtalkerscript.api.library.IInteractionWorldLibLogic;
import net.mobtalker.mobtalkerscript.standalone.DummyWorldInfo;

import com.google.common.collect.ImmutableMap;

public class ConsoleInteractionWorldLibLogic implements IInteractionWorldLibLogic
{
    private String _biomeName = "Plains";
    private String _biomeTemperature = "normal";
    private String _biomeHumidity = "normal";
    
    private String _worldWeather = "clear";
    
    private String _difficulty = "peaceful";
    private boolean _isHardcore = false;
    
    private final IWorldInfo _worldInfo = new DummyWorldInfo();
    
    // ========================================
    
    @Override
    public Map<Integer, IWorldInfo> getWorlds()
    {
        return ImmutableMap.of( Integer.valueOf( 0 ), _worldInfo );
    }
    
    @Override
    public IWorldInfo getWorld()
    {
        return _worldInfo;
    }
    
    @Override
    public String getDifficulty()
    {
        return _difficulty;
    }
    
    @Override
    public boolean isHardcore()
    {
        return _isHardcore;
    }
    
    @Override
    public boolean getGameRule( String rule )
    {
        return false;
    }
    
    @Override
    public String getBiome()
    {
        return _biomeName;
    }
    
    @Override
    public String getTemperature()
    {
        return _biomeTemperature;
    }
    
    @Override
    public String getHumidity()
    {
        return _biomeHumidity;
    }
    
    @Override
    public String getWeather()
    {
        return _worldWeather;
    }
    
    @Override
    public boolean isThundering()
    {
        return false;
    }
    
    @Override
    public boolean isStorming()
    {
        return false;
    }
}
