package net.mobtalker.mobtalkerscript.standalone;

import net.mobtalker.mobtalkerscript.api.WorldPosition;

public class DummyPlayer extends DummyEntity
{
    private String _gameMode;
    
    // ========================================
    
    public DummyPlayer( String name, WorldPosition position )
    {
        super( name, 20.0F, 20.0F, position );
        _gameMode = "adventure";
    }
    
    // ========================================
    
    public String getGameMode()
    {
        return _gameMode;
    }
    
    public void setGameMode( String gameMode )
    {
        _gameMode = gameMode;
    }
}
