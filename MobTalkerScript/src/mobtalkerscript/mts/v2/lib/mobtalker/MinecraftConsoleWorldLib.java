package mobtalkerscript.mts.v2.lib.mobtalker;

import static mobtalkerscript.mts.v2.value.MtsValue.*;
import mobtalkerscript.mts.v2.value.*;
import mobtalkerscript.mts.v2.value.userdata.*;

public class MinecraftConsoleWorldLib
{
    
    // ========================================
    // Position and world dependent
    
    @MtsNativeLibraryFunction( name = "GetBiome" )
    public MtsString GetBiome()
    {
        return valueOf( "Plains" );
    }
    
    @MtsNativeLibraryFunction( name = "GetWeather" )
    public MtsVarArgs GetWeather( MtsValue arg )
    {
        // clear, raining, snowing,
        return new MtsVarArgs( valueOf( "clear" ), FALSE );
    }
    
    // ========================================
    // World dependent
    
    @MtsNativeLibraryFunction( name = "GetID" )
    public MtsNumber GetID()
    {
        return ZERO;
    }
    
    @MtsNativeLibraryFunction( name = "GetMoonphase" )
    public MtsNumber GetMoonphase( MtsValue arg )
    {
        return ZERO;
    }
    
    @MtsNativeLibraryFunction( name = "GetName" )
    public MtsString GetName( MtsValue arg )
    {
        return valueOf( "Overworld" );
    }
    
    @MtsNativeLibraryFunction( name = "GetSpawnpoint" )
    public MtsVarArgs GetSpawnpoint( MtsValue arg )
    {
        return new MtsVarArgs( ZERO, ZERO, ZERO );
    }
    
    @MtsNativeLibraryFunction( name = "GetTime" )
    public MtsNumber GetTime( MtsValue arg )
    {
        return ZERO;
    }
    
    @MtsNativeLibraryFunction( name = "IsDaytime" )
    public MtsBoolean IsDaytime( MtsValue arg )
    {
        return TRUE;
    }
    
    // ========================================
    // World independent
    
    @MtsNativeLibraryFunction( name = "GetDifficulty" )
    public MtsVarArgs GetDifficulty()
    {
        // peaceful, easy, normal, hard
        return new MtsVarArgs( valueOf( "peaceful" ), FALSE );
    }
    
    @MtsNativeLibraryFunction( name = "GetGameRule" )
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
    
    @MtsNativeLibraryFunction( name = "GetType" )
    public MtsString GetType( MtsValue arg )
    {
        // default, flat, largebiomes
        return valueOf( "default" );
    }
    
    @MtsNativeLibraryFunction( name = "GetType" )
    public MtsBoolean IsSinglePlayer()
    {
        return TRUE;
    }
}
