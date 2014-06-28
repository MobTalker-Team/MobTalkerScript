package mobtalkerscript.v2.lib.console.mobtalker;

public class DummyTalkingPlayer extends DummyInteractableEntity implements ITalkingPlayer
{
    protected double _exp;
    
    // ========================================
    
    public DummyTalkingPlayer( String name, double maxHealth )
    {
        super( name, "Player", maxHealth );
    }
    
    // ========================================
    
    @Override
    public String getGameMode()
    {
        // survival, creative, adventure
        return "survival";
    }
    
    // ========================================
    
    @Override
    public double getExp()
    {
        return _exp;
    }
    
    @Override
    public void setExp( double exp )
    {
        _exp = exp;
    }
}
