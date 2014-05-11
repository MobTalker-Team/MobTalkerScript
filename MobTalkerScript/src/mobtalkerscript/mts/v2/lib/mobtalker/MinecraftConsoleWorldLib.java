package mobtalkerscript.mts.v2.lib.mobtalker;

import mobtalkerscript.mts.v2.lib.*;
import mobtalkerscript.mts.v2.value.*;

public class MinecraftConsoleWorldLib extends MtsGlobalLibrary
{
    
    @Override
    public void bind()
    {
        MtsTable lib = new MtsTable( 0, 13 );
        
        bindFunction( lib, "GetBiome", new GetBiome() );
        bindFunction( lib, "GetWeather", new GetWeather() );
        
        bindFunction( lib, "GetID", new GetID() );
        bindFunction( lib, "GetMoonphase", new GetMoonphase() );
        bindFunction( lib, "GetName", new GetName() );
        bindFunction( lib, "GetTime", new GetTime() );
        bindFunction( lib, "GetSpawnpoint", new GetSpawnpoint() );
        bindFunction( lib, "GetTime", new GetTime() );
        bindFunction( lib, "IsDaytime", new IsDaytime() );
        
        bindFunction( lib, "GetDifficulty", new GetDifficulty() );
        bindFunction( lib, "GetGameRule", new GetGameRule() );
        bindFunction( lib, "GetType", new GetType() );
        bindFunction( lib, "IsSinglePlayer", new IsSinglePlayer() );
        
        _G.set( "World", lib, false );
    }
    
    // ========================================
    // Position and world dependent
    
    private final class GetBiome extends MtsZeroArgFunction
    {
        @Override
        protected MtsValue invoke()
        {
            return valueOf( "Plains" );
        }
    }
    
    private final class GetWeather extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg )
        {
            // clear, raining, snowing,
            return new MtsVarArgs( valueOf( "clear" ), FALSE );
        }
    }
    
    // ========================================
    // World dependent
    
    private final class GetID extends MtsZeroArgFunction
    {
        @Override
        protected MtsValue invoke()
        {
            return ZERO;
        }
    }
    
    private final class GetMoonphase extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg )
        {
            return ZERO;
        }
    }
    
    private final class GetName extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg )
        {
            return valueOf( "Overworld" );
        }
    }
    
    private final class GetSpawnpoint extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg )
        {
            return new MtsVarArgs( ZERO, ZERO, ZERO );
        }
    }
    
    private final class GetTime extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg )
        {
            return ZERO;
        }
    }
    
    private final class IsDaytime extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg )
        {
            return TRUE;
        }
    }
    
    // ========================================
    // World independent
    
    private final class GetDifficulty extends MtsZeroArgFunction
    {
        @Override
        protected MtsValue invoke()
        {
            // peaceful, easy, normal, hard
            return new MtsVarArgs( valueOf( "peaceful" ), FALSE );
        }
    }
    
    private final class GetGameRule extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg )
        {
//             ("doFireTick", "true");
//             ("mobGriefing", "true");
//             ("keepInventory", "false");
//             ("doMobSpawning", "true");
//             ("doMobLoot", "true");
//             ("doTileDrops", "true");
//             ("commandBlockOutput", "true");
//             ("naturalRegeneration", "true");
//             ("doDaylightCycle", "true");
            return FALSE;
        }
    }
    
    private final class GetType extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg )
        {
            // Default, Flat, LargeBiomes
            return valueOf( "Default" );
        }
    }
    
    private final class IsSinglePlayer extends MtsZeroArgFunction
    {
        @Override
        protected MtsValue invoke()
        {
            return TRUE;
        }
    }
}
