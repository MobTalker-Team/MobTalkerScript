package mobtalkerscript.mts.v2.lib.mobtalker;

import static mobtalkerscript.util.MtsCheck.*;
import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.lib.*;
import mobtalkerscript.mts.v2.value.*;

public class MobTalkerConsoleInteractionLib extends MtsGlobalLibrary
{
    private final DummyTalkingPlayer _player;
    private final DummyTalkingEntity _entity;
    
    // ========================================
    
    public MobTalkerConsoleInteractionLib( DummyTalkingPlayer player, DummyTalkingEntity entity )
    {
        _player = player;
        _entity = entity;
    }
    
    // ========================================
    
    public DummyTalkingPlayer getPlayer()
    {
        return _player;
    }
    
    public DummyTalkingEntity getEntity()
    {
        return _entity;
    }
    
    // ========================================
    
    @Override
    public void bind()
    {
        // Player
        bindFunction( "GetPlayerName", new GetPlayerName() );
        bindFunction( "GetPlayerGameMode", new GetPlayerGameMode() );
        
        bindFunction( "HurtPlayer", new HurtPlayer() );
        
        bindFunction( "GetPlayerItemCount", new GetPlayerItemCount() );
        bindFunction( "TakePlayerItem", new TakePlayerItem() );
        bindFunction( "GivePlayerItem", new GivePlayerItem() );
        
        // Entity
        bindFunction( "GetEntityName", new GetEntityName() );
        bindFunction( "SetEntityName", new SetEntityName() );
        
        bindFunction( "GetEntityType", new GetEntityType() );
        
        bindFunction( "GetEntityHealth", new GetEntityHealth() );
        bindFunction( "SetEntityHealth", new SetEntityHealth() );
        
        bindFunction( "GetEntityMaxHealth", new GetEntityMaxHealth() );
        bindFunction( "SetEntityMaxHealth", new SetEntityMaxHealth() );
        
        bindFunction( "GetLoveLevel", new GetLoveLevel() );
        bindFunction( "SetLoveLevel", new SetLoveLevel() );
    }
    
    // ========================================
    
    private final class GetPlayerName extends MtsZeroArgFunction
    {
        @Override
        protected MtsValue invoke()
        {
            return valueOf( getPlayer().getName() );
        }
    }
    
    private static final class GetPlayerGameMode extends MtsZeroArgFunction
    {
        @Override
        protected MtsValue invoke()
        {
            return valueOf( "peaceful" );
        }
    }
    
    // ========================================
    
    private class HurtPlayer extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg )
        {
            checkNumber( arg, 1 );
            
            _G.out.println( "You were hurt for " + arg.asNumber().toString() + " damage!" );
            
            return EMPTY_VARARGS;
        }
    }
    
    // ========================================
    
    private class GetPlayerItemCount extends MtsTwoArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
        {
//            int id = checkInteger( arg1, 0 );
//            int meta = arg2.isNil() ? 0 : checkInteger( arg2, 1 );
            
            return valueOf( 42 );
        }
    }
    
    private class TakePlayerItem extends MtsThreeArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
        {
            int id = checkInteger( arg1, 0 );
            int count = arg2.isNil() ? 0 : checkInteger( arg2, 1 );
            int meta = arg3.isNil() ? 0 : checkInteger( arg3, 2 );
            
            StringBuilder s = new StringBuilder( 32 );
            s.append( "Taking " ).append( count ).append( "x " );
            
            s.append( id );
            if ( meta != 0 )
                s.append( ":" ).append( meta );
            
            s.append( " from " ).append( getPlayer().getName() );
            
            return TRUE;
        }
    }
    
    private class GivePlayerItem extends MtsThreeArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
        {
            int id = checkInteger( arg1, 0 );
            int count = arg2.isNil() ? 0 : checkInteger( arg2, 1 );
            int meta = arg3.isNil() ? 0 : checkInteger( arg3, 2 );
            
            StringBuilder s = new StringBuilder( 32 );
            s.append( "Giving " ).append( getPlayer().getName() ).append( " " );
            s.append( count ).append( "x " );
            
            s.append( id );
            if ( meta != 0 )
                s.append( ":" ).append( meta );
            
            return TRUE;
        }
    }
    
// ========================================
    
    private final class GetEntityName extends MtsZeroArgFunction
    {
        @Override
        protected MtsValue invoke()
        {
            return valueOf( getEntity().getName() );
        }
    }
    
    private final class SetEntityName extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg )
        {
            getEntity().setName( checkString( arg, 0 ) );
            return EMPTY_VARARGS;
        }
    }
    
    // ========================================
    
    private final class GetEntityType extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg )
        {
            return valueOf( getEntity().getType() );
        }
    }
    
    // ========================================
    
    private final class GetEntityHealth extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg )
        {
            return valueOf( getEntity().getHealth() );
        }
    }
    
    private final class SetEntityHealth extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg )
        {
            DummyTalkingEntity entity = getEntity();
            double value = checkNumber( arg, 0 );
            
            if ( value >= entity.getMaxHealth() )
                throw new ScriptEngineException( "new health must be lower then or equal to max health" );
            
            entity.setHealth( value );
            
            return EMPTY_VARARGS;
        }
    }
    
    // ========================================
    
    private final class GetEntityMaxHealth extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg )
        {
            return valueOf( getEntity().getMaxHealth() );
        }
    }
    
    private final class SetEntityMaxHealth extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg )
        {
            getEntity().setMaxHealth( checkNumber( arg, 0 ) );
            return EMPTY_VARARGS;
        }
    }
    
    // ========================================
    
    private final class GetLoveLevel extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg )
        {
            return valueOf( getEntity().getLoveLevel( null ) );
        }
    }
    
    private final class SetLoveLevel extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg )
        {
            getEntity().setLoveLevel( null, checkNumber( arg, 0 ) );
            return EMPTY_VARARGS;
        }
    }
    
}
