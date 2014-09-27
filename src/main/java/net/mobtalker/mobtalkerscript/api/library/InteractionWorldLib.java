package net.mobtalker.mobtalkerscript.api.library;

import static net.mobtalker.mobtalkerscript.v2.MtsCheck.*;
import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;

import java.util.Map;

import net.mobtalker.mobtalkerscript.api.IWorldInfo;
import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

public class InteractionWorldLib
{
    private IInteractionWorldLibLogic _logic;
    
    // ========================================
    
    public InteractionWorldLib( IInteractionWorldLibLogic logic )
    {
        _logic = logic;
    }
    
    // ========================================
    
    public IInteractionWorldLibLogic getLogic()
    {
        return _logic;
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsTable getWorlds()
    {
        Map<Integer, IWorldInfo> worlds = _logic.getWorlds();
        
        MtsTable t = new MtsTable( 0, worlds.size() );
        for ( IWorldInfo world : worlds.values() )
        {
            MtsTable info = new MtsTable( 0, 2 );
            info.set( "name", valueOf( world.getName() ) );
            info.set( "type", valueOf( world.getType() ) );
            t.set( valueOf( world.getID() ), info );
        }
        
        return t;
    }
    
    @MtsNativeFunction
    public MtsVarArgs getDifficulty()
    {
        return MtsVarArgs.of( valueOf( _logic.getDifficulty() ),
                              valueOf( _logic.isHardcore() ) );
    }
    
    @MtsNativeFunction
    public MtsBoolean getGameRule( MtsValue argRuleName )
    {
        return valueOf( _logic.getGameRule( checkString( argRuleName, 0 ) ) );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsNumber getID()
    {
        return valueOf( _logic.getWorld().getID() );
    }
    
    @MtsNativeFunction
    public MtsString getName()
    {
        return valueOf( _logic.getWorld().getName() );
    }
    
    @MtsNativeFunction
    public MtsString getType()
    {
        return valueOf( _logic.getWorld().getType() );
    }
    
    @MtsNativeFunction
    public MtsNumber getTime()
    {
        return valueOf( _logic.getWorld().getTime() );
    }
    
    @MtsNativeFunction
    public MtsNumber getMoonphase()
    {
        return valueOf( _logic.getWorld().getMoonphase() );
    }
    
    @MtsNativeFunction
    public MtsBoolean isDaytime()
    {
        return valueOf( _logic.getWorld().isDaytime() );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsString getBiome()
    {
        return valueOf( _logic.getBiome() );
    }
    
    @MtsNativeFunction
    public MtsVarArgs getClimate()
    {
        return MtsVarArgs.of( valueOf( _logic.getTemperature() ),
                              valueOf( _logic.getHumidity() ) );
    }
    
    @MtsNativeFunction
    public MtsVarArgs getWeather()
    {
        return MtsVarArgs.of( valueOf( _logic.getWeather() ),
                              valueOf( _logic.isThundering() ) );
    }
}
