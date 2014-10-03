package net.mobtalker.mobtalkerscript.standalone.lib;

import net.mobtalker.mobtalkerscript.api.*;
import net.mobtalker.mobtalkerscript.api.library.ICreatureLibLogic;
import net.mobtalker.mobtalkerscript.standalone.DummyCreature;

public class ConsoleCreatureLibLogic implements ICreatureLibLogic
{
    private final DummyCreature _creature;
    
    // ========================================
    
    public ConsoleCreatureLibLogic( DummyCreature creature )
    {
        _creature = creature;
    }
    
    // ========================================
    
    @Override
    public void setName( String name )
    {
        _creature.setName( name );
    }
    
    @Override
    public boolean isValidItem( String name )
    {
        // TODO
        return true;
    }
    
    @Override
    public void setEquipment( EquipmentSlot slot, ItemInfo itemInfo )
    {
        // TODO
    }
    
    @Override
    public void setReaction( String key, EntityReaction reaction )
    {
        // TODO
    }
    
    @Override
    public EntityReaction getReaction( String key )
    {
        // TODO
        return EntityReaction.NEUTRAL;
    }
    
    @Override
    public void follow( String key )
    {
        // TODO Auto-generated method stub
    }
    
    @Override
    public void stopFollowing()
    {
        // TODO
    }
}
