package net.mobtalker.mobtalkerscript.v2.lib.console.mobtalker;

import com.google.common.base.*;

public class DummyInteractableEntity implements IInteractableEntity
{
    protected String _name;
    protected String _type;
    protected double _health;
    protected double _maxHealth;
    
    // ========================================
    
    public DummyInteractableEntity( String name, String type, double maxHealth )
    {
        _name = name;
        _type = type;
        _health = maxHealth;
        _maxHealth = maxHealth;
    }
    
    // ========================================
    
    @Override
    public String getName()
    {
        return Strings.isNullOrEmpty( _name ) ? _type : _name;
    }
    
    // ========================================
    
    @Override
    public String getType()
    {
        return _type;
    }
    
    // ========================================
    
    @Override
    public double getHealth()
    {
        return _health;
    }
    
    @Override
    public void setHealth( double health )
    {
        _health = health;
    }
    
    // ========================================
    
    @Override
    public double getMaxHealth()
    {
        return _maxHealth;
    }
    
    @Override
    public void setMaxHealth( double maxHealth )
    {
        _maxHealth = maxHealth;
    }
    
}