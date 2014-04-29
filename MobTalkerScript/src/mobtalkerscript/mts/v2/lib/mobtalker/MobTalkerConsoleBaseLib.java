package mobtalkerscript.mts.v2.lib.mobtalker;

import mobtalkerscript.mts.v2.value.*;

public class MobTalkerConsoleBaseLib extends MtsLibrary
{
    /* package */final MtsString _playerName;
    /* package */MtsNumber _loveLevel;
    
    // ========================================
    
    public MobTalkerConsoleBaseLib( String playerName, double loveLevel )
    {
        _playerName = valueOf( playerName );
        _loveLevel = valueOf( loveLevel );
    }
    
    // ========================================
    
    @Override
    public MtsValue bind( MtsString name, MtsValue env )
    {
        checkIfGlobals( env );
        
        bindFunction( env, "GetPlayerName", new GetPlayerName() );
        bindFunction( env, "GetPlayerGameMode", new GetPlayerGameMode() );
        bindFunction( env, "GetLoveLevel", new GetLoveLevel() );
        bindFunction( env, "SetLoveLevel", new SetLoveLevel() );
        
        bindFunction( env, "HurtPlayer", new HurtPlayer() );
        
        return null;
    }
    
    // ========================================
    
    private final class GetPlayerName extends MtsZeroArgFunction
    {
        @Override
        protected MtsValue invoke()
        {
            return _playerName;
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
    
    private final class GetLoveLevel extends MtsZeroArgFunction
    {
        @Override
        protected MtsValue invoke()
        {
            return _loveLevel;
        }
    }
    
    private final class SetLoveLevel extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg )
        {
            checkNumber( arg, 1 );
            
            _loveLevel = arg.asNumber();
            return EMPTY_VARARGS;
        }
    }
    
    // ========================================
    
    private final class HurtPlayer extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg )
        {
            checkNumber( arg, 1 );
            
            System.out.println( "You were hurt for " + arg.asNumber().toString() + " damage!" );
            
            return EMPTY_VARARGS;
        }
    }
}
