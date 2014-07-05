package mobtalkerscript.v2.lib.console.mobtalker;

import static mobtalkerscript.util.MtsCheck.*;
import static mobtalkerscript.v2.value.MtsValue.*;
import mobtalkerscript.v2.value.*;
import mobtalkerscript.v2.value.userdata.*;

public class InteractionWorldLib
{
    private String _biomeName = "Plains";
    private float _biomeTemperature = 0.2F;
    private float _biomeHumidity = 0.3F;
    private boolean _biomeIsHellish = false;
    private boolean _biomeCanSnow = true;
    
    private boolean _worldIsRaining = false;
    private boolean _worldIsThundering = false;
    
    private int _worldID = 0;
    private String _worldName = "Overworld";
    private String _worldType = "default";
    private int _worldTime = 6000;
    private boolean _worldIsDaytime = true;
    private int _worldMoonphase = 0;
    
    private int _difficulty = 2;
    private boolean _hardcore = false;
    
    // ========================================
    
    public InteractionWorldLib()
    {}
    
    // ========================================
    // Position and world dependent
    
    @MtsNativeFunction
    public MtsString getBiome()
    {
        return valueOf( _biomeName );
    }
    
    @MtsNativeFunction
    public MtsVarArgs getClimate()
    {
        float temperature = _biomeTemperature;
        
        String temperatureCategory;
        if ( _biomeIsHellish )
            temperatureCategory = "hellish";
        else if ( temperature >= 2.0F )
            temperatureCategory = "hot";
        else if ( temperature >= 1.2F )
            temperatureCategory = "warm";
        else if ( temperature >= 0.2F )
            temperatureCategory = "normal";
        else if ( temperature >= 0.05F )
            temperatureCategory = "cold";
        else
            temperatureCategory = "icy";
        
        float humidity = _biomeHumidity;
        String humidityCategory = "ardid";
        if ( humidity >= 0.9F )
            humidityCategory = "damp";
        else if ( humidity >= 0.3F )
            humidityCategory = "normal";
        
        return MtsVarArgs.of( valueOf( temperatureCategory ), valueOf( humidityCategory ) );
    }
    
    @MtsNativeFunction
    public MtsVarArgs getWeather()
    {
        boolean isRaining = _worldIsRaining;
        boolean canSnow = _biomeCanSnow;
        boolean isThundering = _worldIsThundering;
        
        String weather = "clear";
        if ( isRaining )
        {
            weather = canSnow ? "snow" : "raining";
        }
        
        // clear, raining, snowing,
        return MtsVarArgs.of( valueOf( weather ), valueOf( isThundering ) );
    }
    
    // ========================================
    // World dependent
    
    @MtsNativeFunction
    public MtsNumber getID()
    {
        return valueOf( _worldID );
    }
    
    @MtsNativeFunction
    public MtsNumber getMoonphase()
    {
        return valueOf( _worldMoonphase );
    }
    
    @MtsNativeFunction
    public MtsString getName()
    {
        return valueOf( _worldName );
    }
    
    @MtsNativeFunction
    public MtsNumber getTime()
    {
        return valueOf( _worldTime );
    }
    
    @MtsNativeFunction
    public MtsBoolean isDaytime()
    {
        return valueOf( _worldIsDaytime );
    }
    
    @MtsNativeFunction
    public MtsString getType()
    {
        return valueOf( _worldType );
    }
    
    @MtsNativeFunction
    public MtsVarArgs getDifficulty()
    {
        int difficulty = _difficulty;
        boolean isHardcore = _hardcore;
        
        String name;
        if ( difficulty == 0 )
            name = "peaceful";
        else if ( difficulty == 1 )
            name = "easy";
        else if ( difficulty == 2 )
            name = "normal";
        else if ( difficulty == 3 )
            name = "hard";
        else
            name = "unknown";
        
        return MtsVarArgs.of( valueOf( name ), valueOf( isHardcore ) );
    }
    
    @MtsNativeFunction
    public MtsBoolean getGameRule( MtsValue arg1 )
    {
        String ruleName = checkString( arg1, 0 );
        
        if ( "doFireTick".equals( ruleName ) )
            return TRUE;
        if ( "mobGriefing".equals( ruleName ) )
            return FALSE;
        if ( "keepInventory".equals( ruleName ) )
            return TRUE;
        if ( "doMobSpawning".equals( ruleName ) )
            return TRUE;
        if ( "doMobLoot".equals( ruleName ) )
            return TRUE;
        if ( "commandBlockOutput".equals( ruleName ) )
            return TRUE;
        if ( "naturalRegeneration".equals( ruleName ) )
            return TRUE;
        if ( "doDaylightCycle".equals( ruleName ) )
            return TRUE;
        
        return FALSE;
    }
}
