package net.mobtalker.mobtalkerscript.v2.lib.console.mobtalker;

import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;
import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.userdata.*;

public class MinecraftConsoleWorldLib
{
    // ========================================
    // Position and world dependent
    
    @MtsNativeFunction
    public MtsString GetBiome()
    {
        return valueOf( "Plains" );
    }
    
    @MtsNativeFunction
    public MtsVarArgs GetWeather( MtsValue arg )
    {
        // clear, raining, snowing,
        return MtsVarArgs.of( valueOf( "clear" ), FALSE );
    }
    
    // ========================================
    // World dependent
    
    @MtsNativeFunction
    public MtsNumber GetID()
    {
        return ZERO;
    }
    
    @MtsNativeFunction
    public MtsNumber GetMoonphase( MtsValue arg )
    {
        return ZERO;
    }
    
    @MtsNativeFunction
    public MtsString GetName( MtsValue arg )
    {
        return valueOf( "Overworld" );
    }
    
    @MtsNativeFunction
    public MtsVarArgs GetSpawnpoint( MtsValue arg )
    {
        return MtsVarArgs.of( ZERO, ZERO, ZERO );
    }
    
    @MtsNativeFunction
    public MtsNumber GetTime( MtsValue arg )
    {
        return ZERO;
    }
    
    @MtsNativeFunction
    public MtsBoolean IsDaytime( MtsValue arg )
    {
        return TRUE;
    }
    
    // ========================================
    // World independent
    
    @MtsNativeFunction
    public MtsVarArgs GetDifficulty()
    {
        // peaceful, easy, normal, hard
        return MtsVarArgs.of( valueOf( "peaceful" ), FALSE );
    }
    
    @MtsNativeFunction
    public MtsBoolean GetGameRule( MtsValue arg )
    {
//         ("doFireTick", "true");
//         ("mobGriefing", "true");
//         ("keepInventory", "false");
//         ("doMobSpawning", "true");
//         ("doMobLoot", "true");
//         ("doTileDrops", "true");
//         ("commandBlockOutput", "true");
//         ("naturalRegeneration", "true");
//         ("doDaylightCycle", "true");
        return FALSE;
    }
    
    @MtsNativeFunction
    public MtsString GetType( MtsValue arg )
    {
        // default, flat, largebiomes
        return valueOf( "default" );
    }
    
    @MtsNativeFunction
    public MtsBoolean IsSinglePlayer()
    {
        return TRUE;
    }
}
