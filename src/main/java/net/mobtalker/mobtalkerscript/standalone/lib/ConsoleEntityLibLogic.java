package net.mobtalker.mobtalkerscript.standalone.lib;

import java.util.*;

import net.mobtalker.mobtalkerscript.api.*;
import net.mobtalker.mobtalkerscript.api.library.IEntityLibLogic;
import net.mobtalker.mobtalkerscript.standalone.DummyEntity;

public class ConsoleEntityLibLogic implements IEntityLibLogic
{
    private final DummyEntity _entity;
    
    // ========================================
    
    public ConsoleEntityLibLogic( DummyEntity entity )
    {
        _entity = entity;
    }
    
    // ========================================

    @Override
    public String getName()
    {
        return _entity.getName();
    }

    @Override
    public double getHealth()
    {
        return _entity.getHealth();
    }
    
    @Override
    public double getMaxHealth()
    {
        return _entity.getMaxHealth();
    }
    
    @Override
    public WorldPosition getPosition()
    {
        return _entity.getPosition();
    }
    
    @Override
    public boolean isRiding()
    {
        return false;
    }
    
    @Override
    public List<EffectInfo> getEffects()
    {
        return Collections.emptyList();
    }
    
    @Override
    public boolean isValidEffect( String name )
    {
        // TODO
        return true;
    }
    
    @Override
    public boolean applyEffect( String name, int duration, int amplifier )
    {
        // TODO
        return true;
    }
    
    @Override
    public boolean removeEffect( String name )
    {
        // TODO
        return true;
    }
    
    @Override
    public void removeAllEffects()
    {
        // TODO
    }
    
    @Override
    public EquipmentInfo getEquipment()
    {
        // TODO
        return new EquipmentInfo();
    }
    
    @Override
    public ItemInfo getEquipment( EquipmentSlot slot )
    {
        // TODO
        return new ItemInfo( "mts:dummy", 0 );
    }
    
}
