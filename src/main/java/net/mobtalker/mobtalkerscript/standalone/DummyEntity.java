package net.mobtalker.mobtalkerscript.standalone;

import net.mobtalker.mobtalkerscript.api.WorldPosition;

public class DummyEntity
{
    private String _name;
    private float _health;
    private float _maxHealth;
    private WorldPosition _position;
    
    // ========================================
    
    public DummyEntity( String name, float health, float maxHealth, WorldPosition position )
    {
        _name = name;
        _health = health;
        _maxHealth = maxHealth;
    }
    
    // ========================================
    
    public String getName()
    {
        return _name;
    }
    
    public void setName( String name )
    {
        _name = name;
    }
    
    public float getHealth()
    {
        return _health;
    }
    
    public void setHealth( float health )
    {
        _health = health;
    }
    
    public float getMaxHealth()
    {
        return _maxHealth;
    }
    
    public void setMaxHealth( float maxHealth )
    {
        _maxHealth = maxHealth;
    }
    
    public WorldPosition getPosition()
    {
        return _position;
    }
    
    public void setPosition( WorldPosition position )
    {
        _position = position;
    }
}