package net.mobtalker.mobtalkerscript.api.library;

import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

public interface IWorldLib
{
    @MtsNativeFunction
    MtsTable getWorlds();
    
    @MtsNativeFunction
    MtsNumber getID();
    
    @MtsNativeFunction
    MtsString getName();
    
    @MtsNativeFunction
    MtsString getBiome();
    
    @MtsNativeFunction
    MtsVarArgs getClimate();
    
    @MtsNativeFunction
    MtsVarArgs getWeather();
    
    @MtsNativeFunction
    MtsNumber getMoonphase();
    
    @MtsNativeFunction
    MtsNumber getTime();
    
    @MtsNativeFunction
    MtsBoolean isDaytime();
    
    @MtsNativeFunction
    MtsString getType();
    
    @MtsNativeFunction
    MtsVarArgs getDifficulty();
    
    @MtsNativeFunction
    MtsBoolean getGameRule( MtsValue argRuleName );
}