package net.mobtalker.mobtalkerscript.v2.lib.console.mobtalker;

public class DummyTalkingEntity extends DummyInteractableEntity implements ITalkingEntity
{
    protected double _loveLevel;
    
    // ========================================
    
    public DummyTalkingEntity( String name, String type, double maxHealth, double loveLevel )
    {
        super( name, type, maxHealth );
        _loveLevel = loveLevel;
    }
    
    // ========================================
    
    @Override
    public void setName( String name )
    {
        _name = name;
    }
    
    // ========================================
    
    @Override
    public double getLoveLevel( IInteractableEntity entity )
    {
        return _loveLevel;
    }
    
    @Override
    public void setLoveLevel( IInteractableEntity entity, double level )
    {
        _loveLevel = level;
    }
}
