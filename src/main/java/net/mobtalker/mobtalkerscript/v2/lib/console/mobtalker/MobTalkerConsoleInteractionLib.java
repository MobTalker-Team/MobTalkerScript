package mobtalkerscript.v2.lib.console.mobtalker;

import static mobtalkerscript.util.MtsCheck.*;
import static mobtalkerscript.v2.value.MtsValue.*;
import mobtalkerscript.v2.*;
import mobtalkerscript.v2.value.*;
import mobtalkerscript.v2.value.userdata.*;

public class MobTalkerConsoleInteractionLib
{
    private final MtsGlobals _G;
    private final DummyTalkingPlayer _player;
    private final DummyTalkingEntity _entity;
    
    // ========================================
    
    public MobTalkerConsoleInteractionLib( MtsGlobals g, DummyTalkingPlayer player, DummyTalkingEntity entity )
    {
        _G = g;
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
    
    @MtsNativeFunction
    public MtsValue GetPlayerName()
    {
        return valueOf( getPlayer().getName() );
    }
    
    @MtsNativeFunction
    public static MtsValue GetPlayerGameMode()
    {
        return valueOf( "peaceful" );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsValue HurtPlayer( MtsValue arg )
    {
        checkNumber( arg, 1 );
        
        _G.out.println( "You were hurt for " + arg.asNumber().toString() + " damage!" );
        
        return EMPTY_VARARGS;
    }
    
    // ========================================
    
    @MtsNativeFunction
    public static MtsValue GetPlayerItemCount( MtsValue arg1, MtsValue arg2 )
    {
//            int id = checkInteger( arg1, 0 );
//            int meta = arg2.isNil() ? 0 : checkInteger( arg2, 1 );
        
        return valueOf( 42 );
    }
    
    @MtsNativeFunction
    public MtsValue TakePlayerItem( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
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
    
    @MtsNativeFunction
    public MtsValue GivePlayerItem( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
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
    
    // ========================================
    
    @MtsNativeFunction
    public MtsValue GetEntityName()
    {
        return valueOf( getEntity().getName() );
    }
    
    @MtsNativeFunction
    public MtsValue SetEntityName( MtsValue arg )
    {
        getEntity().setName( checkString( arg, 0 ) );
        return EMPTY_VARARGS;
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsValue GetEntityType( MtsValue arg )
    {
        return valueOf( getEntity().getType() );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsValue GetEntityHealth( MtsValue arg )
    {
        return valueOf( getEntity().getHealth() );
    }
    
    @MtsNativeFunction
    public MtsValue SetEntityHealth( MtsValue arg )
    {
        DummyTalkingEntity entity = getEntity();
        double value = checkNumber( arg, 0 );
        
        if ( value >= entity.getMaxHealth() )
            throw new ScriptEngineException( "new health must be lower then or equal to max health" );
        
        entity.setHealth( value );
        
        return EMPTY_VARARGS;
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsValue GetEntityMaxHealth( MtsValue arg )
    {
        return valueOf( getEntity().getMaxHealth() );
    }
    
    @MtsNativeFunction
    public MtsValue SetEntityMaxHealth( MtsValue arg )
    {
        getEntity().setMaxHealth( checkNumber( arg, 0 ) );
        return EMPTY_VARARGS;
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsValue GetLoveLevel( MtsValue arg )
    {
        return valueOf( getEntity().getLoveLevel( null ) );
    }
    
    @MtsNativeFunction
    public MtsValue SetLoveLevel( MtsValue arg )
    {
        getEntity().setLoveLevel( null, checkNumber( arg, 0 ) );
        return EMPTY_VARARGS;
    }
    
}
